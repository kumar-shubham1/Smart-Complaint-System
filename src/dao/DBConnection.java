package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        try {
            System.out.println("Attempting connection to Database...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/complaint_system",
                    "root",
                    "" // Set your MySQL password here
            );
            if (conn != null) {
                System.out.println("✅ Database Connected Successfully!");
            }
            return conn;
        } catch (Exception e) {
            System.err.println("❌ Database Connection Failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}