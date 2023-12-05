import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

public class TransactionManager {

    private Connection connection;

    public TransactionManager(Connection connection) {
        this.connection = connection;
    }

    public void placeOrderWithTransaction(Order order, List<OrderDetails> orderDetailsList) {
        try {
            connection.setAutoCommit(false); // Start transaction

            // Savepoint to rollback to in case of failure
            Savepoint savepoint = connection.setSavepoint();

            try {
                // Check if there are enough books in stock
                if (areBooksInStock(orderDetailsList)) {
                    // Insert order
                    insertOrder(order);

                    // Insert order details
                    for (OrderDetails orderDetails : orderDetailsList) {
                        insertOrderDetails(orderDetails);
                        updateBookStock(orderDetails.getBookID(), orderDetails.getQuantity());
                    }

                    // Commit the transaction
                    connection.commit();
                } else {
                    // Rollback if there are not enough books in stock
                    connection.rollback(savepoint);
                    System.out.println("Transaction failed: Not enough books in stock.");
                }
            } catch (SQLException e) {
                // Rollback in case of any exception
                connection.rollback(savepoint);
                System.out.println("Transaction failed: " + e.getMessage());
            } finally {
                // Set auto-commit back to true after transaction completion
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error setting auto-commit: " + e.getMessage());
        }
    }

    private void insertOrder(Order order) throws SQLException {
        String sql = "INSERT INTO Orders (orderID, customerID, orderDate, total, status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, order.getOrderID());
            preparedStatement.setInt(2, order.getCustomerID());
            preparedStatement.setDate(3, order.getOrderDate());
            preparedStatement.setBigDecimal(4, order.getTotal());
            preparedStatement.setString(5, order.getStatus());

            preparedStatement.executeUpdate();
            System.out.println("Order inserted successfully: " + order);
        }
    }

    private void insertOrderDetails(OrderDetails orderDetails) throws SQLException {
        String sql = "INSERT INTO OrderDetails (orderDetailID, orderID, bookID, quantity, total) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderDetails.getOrderDetailID());
            statement.setInt(2, orderDetails.getOrderID());
            statement.setInt(3, orderDetails.getBookID());
            statement.setInt(4, orderDetails.getQuantity());
            statement.setBigDecimal(5, orderDetails.getTotal());

            statement.executeUpdate();
            System.out.println("OrderDetails inserted successfully: " + orderDetails);
        }
    }

    private boolean areBooksInStock(List<OrderDetails> orderDetailsList) throws SQLException {
        for (OrderDetails orderDetails : orderDetailsList) {
            if (!isBookInStock(orderDetails.getBookID(), orderDetails.getQuantity())) {
                return false;
            }
        }
        return true;
    }

    private boolean isBookInStock(int bookID, int requiredQuantity) throws SQLException {
        int availableStock = getAvailableStock(bookID);
        return availableStock >= requiredQuantity;
    }

    private int getAvailableStock(int bookID) throws SQLException {
        String sql = "SELECT inStock FROM Books WHERE bookID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookID);

            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("inStock");
                }
            }
        }
        return 0;
    }

    private void updateBookStock(int bookID, int quantity) throws SQLException {
        String sql = "UPDATE Books SET inStock = inStock - ? WHERE bookID = ?";

        // Updated the order of parameters in the setInt method to match the order in the SQL query
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(2, bookID);
            statement.setInt(1, quantity);

            statement.executeUpdate();
            System.out.println("Book stock updated successfully for bookID: " + bookID);
        }
    }
}
