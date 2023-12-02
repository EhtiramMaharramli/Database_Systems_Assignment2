import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class OrderOperations {

    private Connection connection;

    public OrderOperations(Connection connection) {
        this.connection = connection;
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

        for (int i = 1; i <= 5; i++) {
            int orderID = i;
            int customerID = random.nextInt(5) + 1; // Random customerID between 1 and 5
            Date orderDate = Date.valueOf("2023-01-01"); // You can modify this with a random date
            BigDecimal total = new BigDecimal(random.nextInt(100) + 50); // Random total between 50 and 150
            String[] statuses = {"Processing", "Shipped", "Completed"};
            String status = statuses[random.nextInt(statuses.length)]; // Random status

            Order order = new Order(orderID, customerID, orderDate, total, status);
            insertOrder(order);
        }
    }


}

