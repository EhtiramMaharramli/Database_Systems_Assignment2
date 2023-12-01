import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/NewDB";
        String username = "postgres";
        String password = "admin14014";

        try {
            try (Connection con = DriverManager.getConnection(url, username, password)) {
                if (con != null) {
                    System.out.println("Connected to database successfully!");
                } else {
                    System.out.println("Failed to create connection.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
