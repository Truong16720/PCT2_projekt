import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://localhost:3306/knihy";
            String user = "root";
            String password = "Truong1234";

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Successfully connected to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
