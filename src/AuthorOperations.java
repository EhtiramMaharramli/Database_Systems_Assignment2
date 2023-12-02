import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorOperations {

    private Connection con;

    public AuthorOperations(Connection connection) {
        this.con = connection;
    }

    public void insertAuthor(Author author) {
        if (con == null) {
            System.out.println("Connection is null. It is not possible to do database operation.");
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
            System.out.println("Connection is null. It is not possible to do database operation.");
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
}
