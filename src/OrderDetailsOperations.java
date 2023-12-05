import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderDetailsOperations {
    private Connection con;

    public OrderDetailsOperations(Connection connection) {
        this.con = connection;
    }

    public void performOrderDetailsOperations() {
        insertRandomOrderDetails(5);
        displayAllOrderDetails();

        updateOrderDetails(new OrderDetails(3, 1, 2, 4, new BigDecimal("45.00")));

        displayAllOrderDetails();

        deleteOrderDetails(2);

        displayAllOrderDetails();
    }

    private void displayAllOrderDetails() {
        List<OrderDetails> orderDetailsLists = getAllOrderDetails();
        orderDetailsLists.forEach(System.out::println);
    }

    public void insertOrderDetails(OrderDetails orderDetails) {
        if (con == null) {
            System.out.println("Connection is null. Unable to perform database operation.");
            return;
        }

        String sql = "INSERT INTO OrderDetails (orderDetailID, orderID, bookID, quantity, total) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, orderDetails.getOrderDetailID());
            statement.setInt(2, orderDetails.getOrderID());
            statement.setInt(3, orderDetails.getBookID());
            statement.setInt(4, orderDetails.getQuantity());
            statement.setBigDecimal(5, orderDetails.getTotal());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("A new OrderDetails record has been inserted successfully!");
            } else {
                System.out.println("Failed to insert a new OrderDetails record.");
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }
    }

    public void insertRandomOrderDetails(int numberOfRecords) {
        Random random = new Random();

        for (int i = 0; i < numberOfRecords; i++) {
            int orderDetailID = i + 1;
            int orderID = random.nextInt(5) + 1;
            int bookID = random.nextInt(5) + 1;
            int quantity = random.nextInt(10) + 1;
            BigDecimal total = new BigDecimal(random.nextInt(100) + 1);

            OrderDetails orderDetails = new OrderDetails(orderDetailID, orderID, bookID, quantity, total);
            insertOrderDetails(orderDetails);
        }
    }

    public List<OrderDetails> getAllOrderDetails() {
        List<OrderDetails> orderDetailsList = new ArrayList<>();

        if (con == null) {
            System.out.println("Connection is null. Unable to retrieve order details.");
            return orderDetailsList;
        }

        String sql = "SELECT * FROM OrderDetails";

        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int orderDetailID = resultSet.getInt("orderDetailID");
                int orderID = resultSet.getInt("orderID");
                int bookID = resultSet.getInt("bookID");
                int quantity = resultSet.getInt("quantity");
                BigDecimal total = resultSet.getBigDecimal("total");

                OrderDetails orderDetails = new OrderDetails(orderDetailID, orderID, bookID, quantity, total);
                orderDetailsList.add(orderDetails);
            }

        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }

        return orderDetailsList;
    }

    public void updateOrderDetails(OrderDetails orderDetails) {
        if (con == null) {
            System.out.println("Connection is null. Unable to perform database operation.");
            return;
        }

        String sql = "UPDATE OrderDetails SET orderID = ?, bookID = ?, quantity = ?, total = ? WHERE orderDetailID = ?";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, orderDetails.getOrderID());
            statement.setInt(2, orderDetails.getBookID());
            statement.setInt(3, orderDetails.getQuantity());
            statement.setBigDecimal(4, orderDetails.getTotal());
            statement.setInt(5, orderDetails.getOrderDetailID());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("OrderDetails updated successfully!");
            } else {
                System.out.println("Failed to update the OrderDetails. No matching orderDetailID found.");
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }
    }

    public void deleteOrderDetails(int orderDetailID) {
        if (con == null) {
            System.out.println("Connection is null. Unable to perform database operation.");
            return;
        }

        String sql = "DELETE FROM OrderDetails WHERE orderDetailID = ?";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, orderDetailID);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("OrderDetails deleted successfully!");
            } else {
                System.out.println("Failed to delete the OrderDetails. No matching orderDetailID found.");
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }
    }
}
