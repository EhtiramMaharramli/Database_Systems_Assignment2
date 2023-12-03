import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderOperations {

    private Connection connection;

    public OrderOperations(Connection connection) {
        this.connection = connection;
    }

    public void performOrderOperations() {
        insertRandomOrders();
        displayAllOrders();

        updateOrder(new Order(3, 3, Date.valueOf("2023-03-21"), new BigDecimal("52.00"), "Shipped"));

        displayAllOrders();

        deleteOrder(2);

        displayAllOrders();
    }

    private void displayAllOrders() {
        List<Order> orders = getAllOrders();
        orders.forEach(System.out::println);
    }

    public void insertOrder(Order order) {
        String sql = "INSERT INTO Orders (orderID, customerID, orderDate, total, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, order.getOrderID());
            preparedStatement.setInt(2, order.getCustomerID());
            preparedStatement.setDate(3, order.getOrderDate());
            preparedStatement.setBigDecimal(4, order.getTotal());
            preparedStatement.setString(5, order.getStatus());

            preparedStatement.executeUpdate();
            System.out.println("Order inserted successfully: " + order);
        } catch (SQLException e) {
            System.out.println("Error inserting order: " + e.getMessage());
        }
    }

    public void insertRandomOrders() {
        Random random = new Random();

        Date[] orderDates = {
                Date.valueOf("2023-01-05"),
                Date.valueOf("2023-10-29"),
                Date.valueOf("2023-04-01"),
                Date.valueOf("2023-06-15"),
                Date.valueOf("2023-08-28")
        };

        for (int i = 1; i <= 5; i++) {
            int orderID = i;
            int customerID = random.nextInt(5) + 1;
            Date orderDate = orderDates[i - 1];
            BigDecimal total = new BigDecimal(random.nextInt(100) + 50);
            String[] statuses = {"Processing", "Shipped", "Completed"};
            String status = statuses[random.nextInt(statuses.length)];

            Order order = new Order(orderID, customerID, orderDate, total, status);
            insertOrder(order);
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        if (connection == null) {
            System.out.println("Connection is null. Unable to retrieve orders.");
            return orders;
        }

        String sql = "SELECT * FROM Orders";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int orderID = resultSet.getInt("orderID");
                int customerID = resultSet.getInt("customerID");
                Date orderDate = resultSet.getDate("orderDate");
                BigDecimal total = resultSet.getBigDecimal("total");
                String status = resultSet.getString("status");

                Order order = new Order(orderID, customerID, orderDate, total, status);
                orders.add(order);
            }

        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }

        return orders;
    }

    public void updateOrder(Order order) {
        if (connection == null) {
            System.out.println("Connection is null. Unable to perform database operation.");
            return;
        }

        String sql = "UPDATE Orders SET customerID = ?, orderDate = ?, total = ?, status = ? WHERE orderID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order.getCustomerID());
            statement.setDate(2, order.getOrderDate());
            statement.setBigDecimal(3, order.getTotal());
            statement.setString(4, order.getStatus());
            statement.setInt(5, order.getOrderID());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Order updated successfully!");
            } else {
                System.out.println("Failed to update the order. No matching orderID found.");
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }
    }

    public void deleteOrder(int orderID) {
        if (connection == null) {
            System.out.println("Connection is null. Unable to perform database operation.");
            return;
        }

        String sql = "DELETE FROM Orders WHERE orderID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderID);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Order deleted successfully!");
            } else {
                System.out.println("Failed to delete the order. No matching orderID found.");
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }
    }
}
