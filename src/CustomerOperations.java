import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerOperations {

    private Connection connection;

    public CustomerOperations(Connection connection) {
        this.connection = connection;
    }

    public void insertCustomer(Customer customer) {
        String sql = "INSERT INTO Customers (customerID, firstName, lastName, email, customerAddress, phoneNumber) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, customer.getCustomerID());
            preparedStatement.setString(2, customer.getFirstName());
            preparedStatement.setString(3, customer.getLastName());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setString(5, customer.getCustomerAddress());
            preparedStatement.setString(6, customer.getPhoneNumber());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Customer inserted: " + customer);
            } else {
                System.out.println("Failed to insert customer: " + customer);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
