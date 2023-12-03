import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/asg02";
        String username = "postgres";
        String password = "admin14014";

        try (Connection con = DriverManager.getConnection(url, username, password)) {
            if (con != null) {
                System.out.println("Connected to the database successfully!");

                AuthorOperations authorOperations = new AuthorOperations(con);
                authorOperations.performAuthorOperations();


                BookOperations bookOperations = new BookOperations(con);
                bookOperations.performBookOperations();


                CustomerOperations customerOperations = new CustomerOperations(con);
                customerOperations.performCustomerOperations();


                OrderOperations orderOperations = new OrderOperations(con);
                orderOperations.performOrderOperations();

                OrderDetailsOperations orderDetailsOperations = new OrderDetailsOperations(con);
                orderDetailsOperations.performOrderDetailsOperations();

                MetaData metaData = new MetaData(con);
                metaData.displayTableInfo();



            } else {
                System.out.println("Failed to create a connection.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
