import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BookOperations {

    private Connection con;

    public BookOperations(Connection connection) {
        this.con = connection;
    }

    public void performBookOperations() {
        insertBooks();
        displayAllBooks();

        updateBook(new Book(2, "Harry Potter and the Philosopher’s Stone", "Fantasy", 2, 12, new BigDecimal("27.56"), 2001));


        displayAllBooks();

        deleteBook(2);

        displayAllBooks();
    }

    private void insertBooks() {
        Book book1 = new Book(1, "The Big Four", "Detective", 1, 10, new BigDecimal("10.00"), 1927);
        Book book2 = new Book(2, "Harry Potter and the Philosopher’s Stone", "Fantasy", 2, 17, new BigDecimal("20.40"), 2001);
        Book book3 = new Book(3, "The Old Man and the Sea", "Fiction", 3, 5, new BigDecimal("8.99"), 1952);
        Book book4 = new Book(4, "War and Peace", "Realistic Fiction", 4, 7, new BigDecimal("12.75"), 1867);
        Book book5 = new Book(5, "Crime and Punishment", "Psychological fiction", 5, 3, new BigDecimal("15.00"), 1866);

        insertBook(book1);
        insertBook(book2);
        insertBook(book3);
        insertBook(book4);
        insertBook(book5);
    }

    private void displayAllBooks() {
        List<Book> books = getAllBooks();
        books.forEach(System.out::println);
    }

    public void insertBook(Book book) {
        if (con == null) {
            System.out.println("Connection is null. Unable to perform database operation.");
            return;
        }

        String sql = "INSERT INTO Books (bookID, bookTitle, genre, authorID, inStock, price, publicationYear) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, book.getBookID());
            statement.setString(2, book.getBookTitle());
            statement.setString(3, book.getGenre());
            statement.setInt(4, book.getAuthorID());
            statement.setInt(5, book.getInStock());
            statement.setBigDecimal(6, book.getPrice());
            statement.setInt(7, book.getPublicationYear());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("A new record has been inserted successfully!");
            } else {
                System.out.println("Failed to insert a new record.");
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        if (con == null) {
            System.out.println("Connection is null. Unable to perform database operation.");
            return books;
        }

        String sql = "SELECT * FROM Books";

        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int bookID = resultSet.getInt("bookID");
                String bookTitle = resultSet.getString("bookTitle");
                String genre = resultSet.getString("genre");
                int authorID = resultSet.getInt("authorID");
                int inStock = resultSet.getInt("inStock");
                BigDecimal price = resultSet.getBigDecimal("price");
                int publicationYear = resultSet.getInt("publicationYear");

                Book book = new Book(bookID, bookTitle, genre, authorID, inStock, price, publicationYear);
                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }

        return books;
    }

    public void updateBook(Book book) {
        if (con == null) {
            System.out.println("Connection is null. Unable to perform database operation.");
            return;
        }

        String sql = "UPDATE Books SET bookTitle = ?, genre = ?, authorID = ?, inStock = ?, price = ?, publicationYear = ? WHERE bookID = ?";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, book.getBookTitle());
            statement.setString(2, book.getGenre());
            statement.setInt(3, book.getAuthorID());
            statement.setInt(4, book.getInStock());
            statement.setBigDecimal(5, book.getPrice());
            statement.setInt(6, book.getPublicationYear());
            statement.setInt(7, book.getBookID());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Book updated successfully!");
            } else {
                System.out.println("Failed to update the book. No matching bookID found.");
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }
    }

    public void deleteBook(int bookID) {
        if (con == null) {
            System.out.println("Connection is null. Unable to perform database operation.");
            return;
        }

        String sql = "DELETE FROM Books WHERE bookID = ?";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, bookID);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Book deleted successfully!");
            } else {
                System.out.println("Failed to delete the book. No matching bookID found.");
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }
    }
}
