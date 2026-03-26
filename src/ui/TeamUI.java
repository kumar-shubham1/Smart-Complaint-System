package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

import dao.ComplaintDAO;
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

        frame.add(panel, BorderLayout.SOUTH);

        ComplaintDAO dao = new ComplaintDAO();

        // LOAD FUNCTION
        Runnable loadData = () -> {
            try {
                model.setRowCount(0);

                ResultSet rs = dao.getComplaintsByTeam(user.getRole());

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