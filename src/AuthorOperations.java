import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorOperations {

    private Connection con;

    public AuthorOperations(Connection connection) {
        this.con = connection;
    }

    public void performAuthorOperations() {
        insertSampleAuthors();
        displayAllAuthors();

        updateAuthor(new Author(1, "Agatha Clarissa Christie", Date.valueOf("1890-09-15")));

        deleteAuthor(3);

        displayAllAuthors();
    }

    private void insertSampleAuthors() {
        insertAuthor(new Author(1, "Agatha Christie", Date.valueOf("1890-09-15")));
        insertAuthor(new Author(2, "J.K. Rowling", Date.valueOf("1965-07-31")));
        insertAuthor(new Author(3, "Ernest Hemingway", Date.valueOf("1899-07-21")));
        insertAuthor(new Author(4, "Leo Tolstoy", Date.valueOf("1828-09-09")));
        insertAuthor(new Author(5, "Fyodor Dostoyevsky", Date.valueOf("1821-11-11")));
    }

    private void displayAllAuthors() {
        List<Author> authors = getAllAuthors();
        authors.forEach(System.out::println);
    }

    public void insertAuthor(Author author) {
        if (con == null) {
            System.out.println("Connection is null. It is not possible to do a database operation.");
            return;
        }

        String sql = "INSERT INTO Authors (authorID, authorName, birthDate) VALUES (?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, author.getAuthorID());
            statement.setString(2, author.getAuthorName());
            statement.setDate(3, author.getBirthDate());

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

    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();

        if (con == null) {
            System.out.println("Connection is null. It is not possible to do a database operation.");
            return authors;
        }

        String sql = "SELECT * FROM Authors";

        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int authorID = resultSet.getInt("authorID");
                String authorName = resultSet.getString("authorName");
                Date birthDate = resultSet.getDate("birthDate");

                Author author = new Author(authorID, authorName, birthDate);
                authors.add(author);
            }

        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }

        return authors;
    }

    public void updateAuthor(Author author) {
        if (con == null) {
            System.out.println("Connection is null. It is not possible to do a database operation.");
            return;
        }

        String sql = "UPDATE Authors SET authorName = ?, birthDate = ? WHERE authorID = ?";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, author.getAuthorName());
            statement.setDate(2, author.getBirthDate());
            statement.setInt(3, author.getAuthorID());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Author updated successfully!");
            } else {
                System.out.println("Failed to update the author. No matching authorID found.");
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }
    }

    public void deleteAuthor(int authorID) {
        if (con == null) {
            System.out.println("Connection is null. It is not possible to do a database operation.");
            return;
        }

        String sql = "DELETE FROM Authors WHERE authorID = ?";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, authorID);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Author deleted successfully!");
            } else {
                System.out.println("Failed to delete the author. No matching authorID found.");
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }
    }}
