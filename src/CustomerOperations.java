import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerOperations {

    private Connection connection;

    public CustomerOperations(Connection connection) {
        this.connection = connection;
    }

    public void performCustomerOperations() {
        insertCustomers();
        displayAllCustomers();

        updateCustomer(new Customer(4, "Jamal", "Hasanli", "jamal.hasanli@gmail.com", "Qara Qarayev Baku", "051-012-34-56"));

        displayAllCustomers();

        deleteCustomer(5);

        displayAllCustomers();
    }

    private void insertCustomers() {
        Customer customer1 = new Customer(1, "Sama", "Gurbanova", "sama.gurban@mail.com", "Mubariz Ibrahimov Shusha", "010-456-78-90");
        Customer customer2 = new Customer(2, "Ali", "Aliyev", "ali.aliyev@yahoo.com", "Nasimi Baku", "055-654-32-10");
        Customer customer3 = new Customer(3, "Farid", "Naghiyev", "farid.naghiyev@inbox.com", "Nizami Baku", "070-789-01-23");
        Customer customer4 = new Customer(4, "Nihat", "Khidirov", "nihat.khidir@gmail.com", "Qara Qarayev Baku", "051-012-34-56");
        Customer customer5 = new Customer(5, "Gismat", "Imamverdiyev", "gismat.imam@inbox.com", "Polad Hashimov Gabala", "077-345-67-89");

        insertCustomer(customer1);
        insertCustomer(customer2);
        insertCustomer(customer3);
        insertCustomer(customer4);
        insertCustomer(customer5);
    }

    private void displayAllCustomers() {
        List<Customer> customers = getAllCustomers();
        customers.forEach(System.out::println);
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

    private List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        String sql = "SELECT * FROM Customers";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int customerID = resultSet.getInt("customerID");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String customerAddress = resultSet.getString("customerAddress");
                String phoneNumber = resultSet.getString("phoneNumber");

                Customer customer = new Customer(customerID, firstName, lastName, email, customerAddress, phoneNumber);
                customers.add(customer);
            }

        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }

        return customers;
    }

    public void updateCustomer(Customer customer) {
        if (connection == null) {
            System.out.println("Connection is null. Unable to perform database operation.");
            return;
        }

        String sql = "UPDATE Customers SET firstName = ?, lastName = ?, email = ?, customerAddress = ?, phoneNumber = ? WHERE customerID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getCustomerAddress());
            statement.setString(5, customer.getPhoneNumber());
            statement.setInt(6, customer.getCustomerID());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Customer updated successfully!");
            } else {
                System.out.println("Failed to update the customer. No matching customerID found.");
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }
    }

    public void deleteCustomer(int customerID) {
        if (connection == null) {
            System.out.println("Connection is null. Unable to perform database operation.");
            return;
        }

        String sql = "DELETE FROM Customers WHERE customerID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerID);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Customer deleted successfully!");
            } else {
                System.out.println("Failed to delete the customer. No matching customerID found.");
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }
    }
}
