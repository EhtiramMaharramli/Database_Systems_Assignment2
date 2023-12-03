import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/asg02";
        String username = "postgres";
        String password = "admin14014";



        try (Connection con = DriverManager.getConnection(url, username, password)) {
            if (con != null) {
                System.out.println("Connected to the database successfully!");


                System.out.print("Enter table name that you want: ");

                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();

                if (input.equals("authors")) {
                    AuthorOperations authorOperations = new AuthorOperations(con);
                    authorOperations.performAuthorOperations();
                }

                if (input.equals("books")) {
                    BookOperations bookOperations = new BookOperations(con);
                    bookOperations.performBookOperations();
                }

                if (input.equals("customers")) {
                    CustomerOperations customerOperations = new CustomerOperations(con);
                    customerOperations.performCustomerOperations();
                }

                if (input.equals("orders")) {
                    OrderOperations orderOperations = new OrderOperations(con);
                    orderOperations.performOrderOperations();
                }

                if (input.equals("orderdetails")) {
                    OrderDetailsOperations orderDetailsOperations = new OrderDetailsOperations(con);
                    orderDetailsOperations.performOrderDetailsOperations();
                }

                scanner.close();

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
