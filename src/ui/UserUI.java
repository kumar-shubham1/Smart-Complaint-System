package ui;

import java.awt.*;
import dao.ComplaintDAO;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Complaint;
import model.User;

public class UserUI extends JFrame {

    private User currentUser;
    private JTextField titleField;
    private JTextArea descArea;
    private JComboBox<String> categoryBox;
    private JComboBox<String> severityBox, urgencyBox, impactBox;

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton submitBtn, viewBtn;

    public UserUI(User user) {
        this.currentUser = user;

        setTitle("User Panel - Logged in as: " + currentUser.getUsername());
        setSize(1200, 600);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window

        // Form Panel (Left Side)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(10, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Complaint"));
        formPanel.setPreferredSize(new Dimension(350, 600));

        formPanel.add(new JLabel("Title"));
        titleField = new JTextField();
        formPanel.add(titleField);

        formPanel.add(new JLabel("Description"));
        descArea = new JTextArea();
        formPanel.add(new JScrollPane(descArea));

        formPanel.add(new JLabel("Category"));
        categoryBox = new JComboBox<>(new String[] { "IT", "Maintenance", "Service" });
        formPanel.add(categoryBox);

        String[] values = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
        formPanel.add(new JLabel("Severity"));
        severityBox = new JComboBox<>(values);
        formPanel.add(severityBox);

        formPanel.add(new JLabel("Urgency"));
        urgencyBox = new JComboBox<>(values);
        formPanel.add(urgencyBox);

        formPanel.add(new JLabel("Impact"));
        impactBox = new JComboBox<>(values);
        formPanel.add(impactBox);

        submitBtn = new JButton("Submit");
        formPanel.add(submitBtn);

        viewBtn = new JButton("Refresh Table");
        formPanel.add(viewBtn);

        add(formPanel, BorderLayout.WEST);

        // Table Display
        String[] cols = { "ID", "Title", "Category", "Priority", "Status", "Created At", "Updated At" };
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowHeight(25);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        table.getColumnModel().getColumn(5).setPreferredWidth(180);
        table.getColumnModel().getColumn(6).setPreferredWidth(180);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // SUBMIT LOGIC
        submitBtn.addActionListener(e -> {
            Complaint c = new Complaint();
            c.setTitle(titleField.getText());
            c.setDescription(descArea.getText());

            String detectedCategory = util.CategoryDetector.detectCategory(descArea.getText());
            c.setCategory(detectedCategory);

            int severity = Integer.parseInt(severityBox.getSelectedItem().toString());
            int urgency = Integer.parseInt(urgencyBox.getSelectedItem().toString());
            int impact = Integer.parseInt(impactBox.getSelectedItem().toString());

            c.setSeverity(severity);
            c.setUrgency(urgency);
            c.setImpact(impact);

            double priority = (severity * 0.5) + (urgency * 0.3) + (impact * 0.2);
            c.setPriority(priority);

            new ComplaintDAO().insertComplaint(c, currentUser.getId());

            JOptionPane.showMessageDialog(null, "Complaint Submitted!\nDetected Category: " + detectedCategory);
            refreshTable();
        });

        // REFRESH BUTTON
        viewBtn.addActionListener(e -> refreshTable());

        // INITIAL LOAD
        refreshTable();

        setVisible(true);
    }

    private void refreshTable() {
        viewBtn.setText("Updating...");
        viewBtn.setEnabled(false);

        // Run data fetching in background to avoid lag
        new Thread(() -> {
            System.out.println("UserUI: Background fetching table for user ID: " + currentUser.getId());
            try {
                ResultSet rs = new ComplaintDAO().getComplaintsByUser(currentUser.getId());

                // Update UI on the Event Dispatch Thread
                SwingUtilities.invokeLater(() -> {
                    try {
                        if (rs == null) {
                            JOptionPane.showMessageDialog(null, "Database unavailable");
                        } else {
                            tableModel.setRowCount(0);
                            while (rs.next()) {
                                tableModel.addRow(new Object[] {
                                        rs.getInt("id"),
                                        rs.getString("title"),
                                        rs.getString("category"),
                                        rs.getDouble("priority"),
                                        rs.getString("status"),
                                        rs.getTimestamp("created_at"),
                                        rs.getTimestamp("updated_at")
                                });
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        viewBtn.setText("Refresh Table");
                        viewBtn.setEnabled(true);
                    }
                });

            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    viewBtn.setText("Refresh Table");
                    viewBtn.setEnabled(true);
                });
            }
        }).start();
    }
}
