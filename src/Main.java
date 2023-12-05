import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/asg02";
        String username = "postgres";
        String password = "admin14014";

        try (Connection con = DriverManager.getConnection(url, username, password)) {
            if (con != null) {
                System.out.println("Connected to the database successfully!");

                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter action (CRUD, Transaction, Metadata): ");
                String action = scanner.nextLine();

                switch (action.toLowerCase()) {
                    case "crud":
                        performCRUDOperations(con);
                        break;
                    case "transaction":
                        performTransaction(con);
                        break;
                    case "metadata":
                        performMetadata(con);
                        break;
                    default:
                        System.out.println("Invalid action.");
                }

                scanner.close();

            } else {
                System.out.println("Failed to create a connection.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void performCRUDOperations(Connection con) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter table name for CRUD operations: ");
        String tableName = scanner.nextLine();

        switch (tableName.toLowerCase()) {
            case "authors":
                AuthorOperations authorOperations = new AuthorOperations(con);
                authorOperations.performAuthorOperations();
                break;
            case "customers":
                CustomerOperations customerOperations = new CustomerOperations(con);
                customerOperations.performCustomerOperations();
                break;
            case "books":
                BookOperations bookOperations = new BookOperations(con);
                bookOperations.performBookOperations();
                break;
            case "orders":
                OrderOperations orderOperations = new OrderOperations(con);
                orderOperations.performOrderOperations();
                break;
            case "orderdetails":
                OrderDetailsOperations orderDetailsOperations = new OrderDetailsOperations(con);
                orderDetailsOperations.performOrderDetailsOperations();
                break;
            default:
                System.out.println("Invalid table name for CRUD operations.");
        }
    }

    private static void performTransaction(Connection con) {
        // Create an instance of TransactionManager
        TransactionManager transactionManager = new TransactionManager(con);

        // Sample data for order and order details
        Order order = new Order(6, 1, Date.valueOf("2023-05-15"), new BigDecimal("75.00"), "Processing");

        List<OrderDetails> orderDetailsList = new ArrayList<>();
        orderDetailsList.add(new OrderDetails(6, 6, 2, 3, new BigDecimal("30.00")));
        orderDetailsList.add(new OrderDetails(7, 6, 3, 2, new BigDecimal("45.00")));

        transactionManager.placeOrderWithTransaction(order, orderDetailsList);
    }

    private static void performMetadata(Connection con) {
        MetaData metaData = new MetaData(con);
        metaData.displayTableInfo();
    }
}

