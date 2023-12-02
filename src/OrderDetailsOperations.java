import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class OrderDetailsOperations {
    private Connection con;

    public OrderDetailsOperations(Connection connection) {
        this.con = connection;
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
            int orderID = random.nextInt(5) + 1; // Assuming you have 5 orders
            int bookID = random.nextInt(5) + 1;  // Assuming you have 5 books
            int quantity = random.nextInt(10) + 1; // Random quantity between 1 and 10
            BigDecimal total = new BigDecimal(random.nextInt(100) + 1); // Random total between 1 and 100

            OrderDetails orderDetails = new OrderDetails(orderDetailID, orderID, bookID, quantity, total);
            insertOrderDetails(orderDetails);
        }
    }
}

