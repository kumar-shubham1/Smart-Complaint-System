package dao;

import java.sql.*;
import model.User;

public class UserDAO {

    public User login(String username, String password) {
        System.out.println("UserDAO: Validating credentials for user '" + username + "'...");

        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.err.println("UserDAO: No database connection available.");
                return null;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, password);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int idFromDB = rs.getInt("id");
                        String userFromDB = rs.getString("username");
                        String role = rs.getString("role");
                        System.out.println("✅ UserDAO: Login valid for user: " + userFromDB + " (ID: " + idFromDB + ") with role: " + role);
                        
                        return new User(
                            idFromDB,
                            userFromDB,
                            rs.getString("password"),
                            role
                        );
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("❌ UserDAO: Error during login validation");
            e.printStackTrace();
        }

        System.err.println("❌ UserDAO: Invalid username/password combination.");
        return null;
    }
}