import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BookOperations {

    private Connection con;

    public BookOperations(Connection connection) {
        this.con = connection;
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
}
