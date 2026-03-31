package ui;

import dao.ComplaintDAO;
import java.awt.*;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.User;

public class TeamUI {

    JTable table;
    DefaultTableModel model;
    JTextField searchField;
    JComboBox<String> statusBox;

    public TeamUI(User user) {

        JFrame frame = new JFrame("Team Panel - " + user.getRole());
        frame.setSize(950, 500);
        frame.setLayout(new BorderLayout());

        // TABLE
        String[] cols = {"ID","Title","Category","Priority","Status","Created At","Updated At"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        table.getColumnModel().getColumn(5).setPreferredWidth(180);
        table.getColumnModel().getColumn(6).setPreferredWidth(180);

        JScrollPane scroll = new JScrollPane(table);
        frame.add(scroll, BorderLayout.CENTER);

        // PANEL
        JPanel panel = new JPanel();

        // STATUS DROPDOWN
        String[] statuses = {"IN_PROGRESS", "RESOLVED", "ESCALATED"};
        statusBox = new JComboBox<>(statuses);
        panel.add(statusBox);

        JButton updateBtn = new JButton("Update Status");
        panel.add(updateBtn);

        // SEARCH
        searchField = new JTextField(15);
        panel.add(searchField);

        JButton searchBtn = new JButton("Search by Title");
        panel.add(searchBtn);

        // 🔹 Duplicate Check (Rabin-Karp)
        JButton dupBtn = new JButton("Check Duplicate");
        panel.add(dupBtn);

        // 🔹 Cluster (Union-Find)
        JButton clusterBtn = new JButton("Cluster Complaints");
        panel.add(clusterBtn);

        frame.add(panel, BorderLayout.SOUTH);

        // 🔥 DUPLICATE ACTION
        dupBtn.addActionListener(e -> {
            java.util.List<model.Complaint> list = util.AppContext.service.loadComplaintsFromDB();
            String userInput = JOptionPane.showInputDialog(frame, "Enter description to check for duplicates:");
            if (userInput != null && !userInput.isEmpty()) {
                boolean isDup = util.AppContext.rabinKarpService.isDuplicate(userInput, list);
                JOptionPane.showMessageDialog(frame, isDup ? "Potential duplicate found!" : "No duplicate found.");
            }
        });

        // 🔥 CLUSTER ACTION
        clusterBtn.addActionListener(e -> {
            java.util.List<model.Complaint> list = util.AppContext.service.loadComplaintsFromDB();
            util.AppContext.clusterService = new service.ComplaintClusterService(); // Reset for fresh cluster
            for (model.Complaint c : list) {
                util.AppContext.clusterService.add(c.getId());
            }
            // For simplicity, cluster by category (Union complaints in same category)
            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    if (list.get(i).getCategory().equals(list.get(j).getCategory())) {
                        util.AppContext.clusterService.union(list.get(i).getId(), list.get(j).getId());
                    }
                }
            }
            int groups = util.AppContext.clusterService.getClusterCount();
            JOptionPane.showMessageDialog(frame, "Grouped complaints into " + groups + " clusters (by category).");
        });



        ComplaintDAO dao = new ComplaintDAO();

        // LOAD FUNCTION
        Runnable loadData = () -> {
            try {
                model.setRowCount(0);

                ResultSet rs = dao.getComplaintsByTeam(user.getRole());

                if (rs == null) {
                    JOptionPane.showMessageDialog(frame, "Database unavailable");
                    return;
                }

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("category"),
                            rs.getDouble("priority"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at")
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        loadData.run();

        // UPDATE STATUS
        updateBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(frame, "Select a complaint first!");
                return;
            }

            int id = (int) model.getValueAt(row, 0);
            String status = statusBox.getSelectedItem().toString();

            dao.updateStatus(id, status);

            JOptionPane.showMessageDialog(frame, "Status Updated!");

            loadData.run();
        });

        // SEARCH
        searchBtn.addActionListener(e -> {
            try {
                model.setRowCount(0);

                
                String title = searchField.getText();

                ResultSet rs = dao.searchByTitle(title, user.getRole());

                if (rs == null) {
                    JOptionPane.showMessageDialog(frame, "Database unavailable");
                    return;
                }

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("category"),
                            rs.getDouble("priority"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at")
                    });
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        frame.setVisible(true);
    }
}