package ui;

import javax.swing.*;
import model.Complaint;
import util.AppContext;

public class AdminUI {

    public AdminUI() {

        JFrame frame = new JFrame("Admin Panel");
        frame.setSize(400, 450); // increased height for new features
        frame.setLayout(null);

        // 🔹 Process Button (Greedy + BFS + Explainable AI)
        JButton processBtn = new JButton("Process Complaint");
        processBtn.setBounds(100, 50, 200, 40);
        frame.add(processBtn);

        // 🔹 View Priority Order (Greedy Visualization)
        JButton viewSorted = new JButton("View Priority Order");
        viewSorted.setBounds(100, 110, 200, 40);
        frame.add(viewSorted);

        // 🔹 Top-K Complaints (Heap)
        JButton topKBtn = new JButton("Top Complaints");
        topKBtn.setBounds(100, 170, 200, 40);
        frame.add(topKBtn);

        // 🔹 View Trends (Sliding Window)
        JButton trendsBtn = new JButton("View Trends");
        trendsBtn.setBounds(100, 230, 200, 40);
        frame.add(trendsBtn);

        // 🔥 Top-K LOGIC
        topKBtn.addActionListener(e -> {
            java.util.List<model.Complaint> list = AppContext.service.loadComplaintsFromDB();
            System.out.println("Loaded complaints (TopK): " + (list != null ? list.size() : 0));
            
            if (list == null || list.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No complaints found in Database.");
                return;
            }

            java.util.List<model.Complaint> topK = AppContext.topKService.getTopK(list, 3);
            
            StringBuilder sb = new StringBuilder("Top 3 High-Priority (from DB):\n");
            for (model.Complaint c : topK) {
                sb.append("ID: ").append(c.getId()).append(" | Priority: ").append(c.getPriority()).append("\n");
            }
            JOptionPane.showMessageDialog(frame, sb.toString());
        });

        // 🔥 TRENDS LOGIC
        trendsBtn.addActionListener(e -> {
            java.util.List<model.Complaint> list = AppContext.service.loadComplaintsFromDB();
            System.out.println("Loaded complaints (Trends): " + (list != null ? list.size() : 0));
            
            if (list == null || list.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No complaints found in Database.");
                return;
            }

            java.util.List<model.Complaint> trend = AppContext.trendService.getComplaintsLastNDays(list, 7);
            JOptionPane.showMessageDialog(frame, "Trends: " + trend.size() + " complaints in last 7 days.");
        });




        // 🔥 PROCESS BUTTON LOGIC (UPDATED WITH EXPLANATION)
        processBtn.addActionListener(e -> {
            // 🔥 SYNC FROM DB
            AppContext.service.syncWithDB();

            Complaint c = AppContext.service.processNext();

            if (c == null) {
                JOptionPane.showMessageDialog(frame, "No complaints");
            } else {

                String team = AppContext.service.assignTeam(c);
                
                // 🔥 PERSIST STATUS CHANGE TO DB
                AppContext.dao.updateStatus(c.getId(), "ASSIGNED");

                // 🔥 BFS RELATED COMPLAINTS
                java.util.List<Integer> related =
                        AppContext.service.getRelatedComplaints(c.getId());

                // 🔥 NEW: EXPLAIN PRIORITY
                String explanation =
                        "Priority Breakdown:\n" +
                        "Severity (" + c.getSeverity() + ") × 0.5\n" +
                        "Urgency (" + c.getUrgency() + ") × 0.3\n" +
                        "Impact (" + c.getImpact() + ") × 0.2\n\n" +
                        "Final Priority = " + c.getPriority();

                JOptionPane.showMessageDialog(frame,
                        "Complaint ID: " + c.getId() +
                        "\nAssigned to: " + team +
                        "\nRelated Complaints: " + related +
                        "\n\n" + explanation
                );
            }
        });

        // 🔥 GREEDY SORT DISPLAY
        viewSorted.addActionListener(e -> {
            // 🔥 LOAD DATA FIRST
            AppContext.service.syncWithDB();

            java.util.List<Complaint> list =
                    AppContext.service.getSortedComplaints();

            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No complaints available");
                return;
            }

            StringBuilder sb = new StringBuilder();

            for (Complaint c : list) {
                sb.append("ID: ").append(c.getId());
                sb.append(" | Priority: ").append(c.getPriority());
                sb.append("\n");
            }

            JOptionPane.showMessageDialog(frame, sb.toString());
        });

        frame.setVisible(true);
    }
}
