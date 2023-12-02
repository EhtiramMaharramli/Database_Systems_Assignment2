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

                Author author1 = new Author(1, "Agatha Christie", Date.valueOf("1890-09-15"));
                Author author2 = new Author(2, "J.K. Rowling", Date.valueOf("1965-07-31"));
                Author author3 = new Author(3, "Ernest Hemingway", Date.valueOf("1899-07-21"));
                Author author4 = new Author(4, "Leo Tolstoy", Date.valueOf("1828-09-09"));
                Author author5 = new Author(5, "Fyodor Dostoyevsky", Date.valueOf("1821-11-11"));

                authorOperations.insertAuthor(author1);
                authorOperations.insertAuthor(author2);
                authorOperations.insertAuthor(author3);
                authorOperations.insertAuthor(author4);
                authorOperations.insertAuthor(author5);

                authorOperations.getAllAuthors().forEach(System.out::println);

                BookOperations bookOperations = new BookOperations(con);

                Book book1 = new Book(1, "The Big Four", "Detective", 1, 10, new BigDecimal("10.00"), 1927);
                Book book2 = new Book(2, "Harry Potter and the Philosopher’s Stone", "Fantasy", 2, 17, new BigDecimal("20.40"), 2001);
                Book book3 = new Book(3, "The Old Man and the Sea", "Fiction", 3, 5, new BigDecimal("8.99"), 1952);
                Book book4 = new Book(4, "War and Peace", "Realistic Fiction", 4, 7, new BigDecimal("12.75"), 1867);
                Book book5 = new Book(5, "Crime and Punishment ", "Psychological fiction", 5, 3, new BigDecimal("15.00"), 1866);

                bookOperations.insertBook(book1);
                bookOperations.insertBook(book2);
                bookOperations.insertBook(book3);
                bookOperations.insertBook(book4);
                bookOperations.insertBook(book5);

                bookOperations.getAllBooks().forEach(System.out::println);


                CustomerOperations customerOperations = new CustomerOperations(con);

                Customer customer1 = new Customer(1, "Sama", "Gurbanova", "sama.gurban@mail.com", "Mubariz Ibrahimov Shusha", "010-456-78-90");
                Customer customer2 = new Customer(2, "Ali", "Aliyev", "ali.aliyev@yahoo.com", "Nasimi Baku", "055-654-32-10");
                Customer customer3 = new Customer(3, "Farid", "Naghiyev", "farid.naghiyev@inbox.com", "Nizami Baku", "070-789-01-23");
                Customer customer4 = new Customer(4, "Nihat", "Khidirov", "nihat.khidir@gmail.com", "Qara Qarayev Baku", "051-012-34-56");
                Customer customer5 = new Customer(5, "Gismat", "Imamverdiyev", "gismat.imam@inbox.com", "Polad Hashimov Gabala", "077-345-67-89");

                customerOperations.insertCustomer(customer1);
                customerOperations.insertCustomer(customer2);
                customerOperations.insertCustomer(customer3);
                customerOperations.insertCustomer(customer4);
                customerOperations.insertCustomer(customer5);


                OrderOperations orderOperations = new OrderOperations(con);

                orderOperations.insertRandomOrders();

                OrderDetailsOperations orderDetailsOperations = new OrderDetailsOperations(con);

                orderDetailsOperations.insertRandomOrderDetails(5);


            } else {
                System.out.println("Failed to create a connection.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
