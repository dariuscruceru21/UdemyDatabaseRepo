import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
// Database URL and credentials
        String jdbcURL = "jdbc:postgresql://localhost:5432/Udemy"; // Replace with your DB URL
        String username = "postgres"; // Replace with your DB username
        String password = "Germania!10"; // Replace with your DB password

        try {
            // Connect to the database
            System.out.println("Connecting to database...");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful!");

            // Close the connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection failed!");
        }
    }
}