package ui;

import java.awt.*;
import dao.ComplaintDAO;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Complaint;

public class UserUI extends JFrame {

    JTextField titleField;
    JTextArea descArea;
    JComboBox<String> categoryBox;
    JComboBox<String> severityBox, urgencyBox, impactBox;

    JTable table;
    DefaultTableModel tableModel;

    public UserUI() {

        setTitle("User Panel");
        setSize(1200, 600); // 🔹 Step 1: Increased size
        setLayout(new BorderLayout(10, 10)); // 🔹 Step 2: Proper Layout
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 🔹 Step 3: Form Panel (Left Side)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(10, 2, 10, 10)); // Grid for inputs
        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Complaint"));
        formPanel.setPreferredSize(new Dimension(350, 600));

        // Form Fields
        formPanel.add(new JLabel("Title"));
        titleField = new JTextField();
        formPanel.add(titleField);

        formPanel.add(new JLabel("Description"));
        descArea = new JTextArea();
        formPanel.add(new JScrollPane(descArea)); // Scroll for desc

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

        // Buttons
        JButton submitBtn = new JButton("Submit");
        formPanel.add(submitBtn);

        JButton viewBtn = new JButton("Refresh Table");
        formPanel.add(viewBtn);

        add(formPanel, BorderLayout.WEST);

        // 🔹 Step 4 & 5: Table Display
        String[] cols = { "ID", "Title", "Category", "Priority", "Status", "Created At", "Updated At" };
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Step 5
        table.setRowHeight(25); // Step 6

        // Step 4: Fixed Column Widths
        table.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // Title
        table.getColumnModel().getColumn(2).setPreferredWidth(120); // Category
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // Priority
        table.getColumnModel().getColumn(4).setPreferredWidth(120); // Status
        table.getColumnModel().getColumn(5).setPreferredWidth(180); // Created At
        table.getColumnModel().getColumn(6).setPreferredWidth(180); // Updated At

        // Step 7: Text Alignment/Padding Renderer
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5)); // Padding
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

            // 🔥 AUTO CATEGORY DETECTION (UTIL - BALANCED)
            String detectedCategory = util.CategoryDetector.detectCategory(descArea.getText());
            c.setCategory(detectedCategory);
            System.out.println("Detected Category: " + detectedCategory);

            int severity = Integer.parseInt(severityBox.getSelectedItem().toString());
            int urgency = Integer.parseInt(urgencyBox.getSelectedItem().toString());
            int impact = Integer.parseInt(impactBox.getSelectedItem().toString());

            c.setSeverity(severity);
            c.setUrgency(urgency);
            c.setImpact(impact);

            double priority = (severity * 0.5) + (urgency * 0.3) + (impact * 0.2);
            c.setPriority(priority);

            new ComplaintDAO().insertComplaint(c);

            JOptionPane.showMessageDialog(null,
                    "Complaint Submitted!\nDetected Category: " + detectedCategory);
        });

        // VIEW
        viewBtn.addActionListener(e -> {
            try {
                ResultSet rs = new ComplaintDAO().getComplaintsByUser();

                if (rs == null) {
                    JOptionPane.showMessageDialog(null, "Database unavailable");
                    return;
                }

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

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        setVisible(true);
    }
}
