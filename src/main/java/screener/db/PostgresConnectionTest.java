package screener.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnectionTest {

    // 1. UPDATE THESE CREDENTIALS
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    public static void main(String[] args) {
        System.out.println("Testing PostgreSQL connection...");

        // Use try-with-resources to ensure the Connection object is closed
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            
            // If getConnection succeeds, the code inside the try block executes
            if (connection != null) {
                System.out.println("✅ SUCCESS! Connected to the PostgreSQL server.");
            }

        } catch (SQLException e) {
            // If connection fails, an SQLException is caught
            System.err.println("❌ FAILED! Could not establish connection to PostgreSQL.");
            System.err.println("Error: " + e.getMessage());
            // Optional: e.printStackTrace(); for full stack trace
        }
    }
}