import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/asg02";
        String username = "postgres";
        String password = "admin14014";
        int authorID = 1;
        String authorName = "Agatha Christie";
        String birthDateStr = "1890-09-15";

        try {
            try (Connection con = DriverManager.getConnection(url, username, password)) {
                if (con != null) {
                    System.out.println("Connected to the database successfully!");
                } else {
                    System.out.println("Failed to create a connection.");
                }

                Date birthDate = Date.valueOf(birthDateStr);

                String sql = "INSERT INTO Authors (authorID, authorName, birthDate) VALUES (?, ?, ?)";

                try (PreparedStatement statement = con.prepareStatement(sql)) {
                    statement.setInt(1, authorID);
                    statement.setString(2, authorName);
                    statement.setDate(3, birthDate);

                    int rows = statement.executeUpdate();

                    if (rows > 0) {
                        System.out.println("A new record has been inserted successfully!");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


