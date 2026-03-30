package ui;

import javax.swing.*;
import util.AppContext;
import model.Complaint;

public class AdminUI {

    public AdminUI() {

        JFrame frame = new JFrame("Admin Panel");
        frame.setSize(400, 350); // increased height
        frame.setLayout(null);

        // 🔹 Process Button (Greedy + BFS)
        JButton processBtn = new JButton("Process Complaint");
        processBtn.setBounds(100, 60, 200, 40);
        frame.add(processBtn);

        // 🔹 View Priority Order (Greedy Visualization)
        JButton viewSorted = new JButton("View Priority Order");
        viewSorted.setBounds(100, 120, 200, 40);
        frame.add(viewSorted);

        // 🔥 PROCESS BUTTON LOGIC
        processBtn.addActionListener(e -> {

            Complaint c = AppContext.service.processNext();

            if (c == null) {
                JOptionPane.showMessageDialog(frame, "No complaints");
            } else {

                String team = AppContext.service.assignTeam(c);

                // 🔥 BFS RELATED COMPLAINTS
                java.util.List<Integer> related =
                        AppContext.service.getRelatedComplaints(c.getId());

                JOptionPane.showMessageDialog(frame,
                        "Complaint ID: " + c.getId() +
                        "\nAssigned to: " + team +
                        "\nRelated Complaints: " + related
                );
            }
        });

        // 🔥 GREEDY SORT DISPLAY
        viewSorted.addActionListener(e -> {

            java.util.List<Complaint> list =
                    AppContext.service.getSortedComplaints();

            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No complaints available");
                return;
            }

            StringBuilder sb = new StringBuilder();

            for (Complaint c : list) {
                sb.append("ID: ").append(c.getId())
                  .append(" | Priority: ").append(c.getPriority())
                  .append("\n");
            }

            JOptionPane.showMessageDialog(frame, sb.toString());
        });

        frame.setVisible(true);
    }
}
