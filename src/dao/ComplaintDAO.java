package dao;

import java.sql.*;
import model.Complaint;
import util.AppContext;

public class ComplaintDAO {

    // INSERT
    public void insertComplaint(Complaint c) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO complaints(title, description, category, severity, urgency, impact, priority, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            if (conn == null) {
                System.err.println("insertComplaint: DB connection is null");
                return;
            }

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, c.getTitle());
            ps.setString(2, c.getDescription());
            ps.setString(3, c.getCategory());
            ps.setInt(4, c.getSeverity());
            ps.setInt(5, c.getUrgency());
            ps.setInt(6, c.getImpact());
            ps.setDouble(7, c.getPriority());
            ps.setString(8, "NEW");

            int affected = ps.executeUpdate();

            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys != null && keys.next()) {
                        c.setId(keys.getInt(1));
                    }
                }
            }

            // 🔥 IMPORTANT: ADD TO SERVICE (DAA INTEGRATION)
            AppContext.service.addComplaint(c);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // USER VIEW
    public ResultSet getComplaintsByUser() {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT id, title, category, priority, status, created_at, updated_at FROM complaints ORDER BY priority DESC";

            if (conn == null) {
                System.err.println("getComplaintsByUser: DB connection is null");
                return null;
            }

            PreparedStatement ps = conn.prepareStatement(sql);

            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // TEAM VIEW
    public ResultSet getComplaintsByTeam(String category) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT id, title, category, priority, status, created_at, updated_at FROM complaints WHERE category=? ORDER BY priority DESC";

            if (conn == null) {
                System.err.println("getComplaintsByTeam: DB connection is null");
                return null;
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, category);

            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE STATUS
    public void updateStatus(int id, String status) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "UPDATE complaints SET status=? WHERE id=?";

            if (conn == null) {
                System.err.println("updateStatus: DB connection is null");
                return;
            }

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, status);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // SEARCH
    public ResultSet searchByTitle(String title, String category) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT id, title, category, priority, status, created_at, updated_at FROM complaints WHERE title LIKE ? AND category=? ORDER BY priority DESC";

            if (conn == null) {
                System.err.println("searchByTitle: DB connection is null");
                return null;
            }

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, "%" + title + "%");
            ps.setString(2, category);

            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔥 FETCH ALL COLUMNS (NEW)
    public ResultSet getAllComplaintsFull() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM complaints ORDER BY priority DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

