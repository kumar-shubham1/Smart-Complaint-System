package ui;

import javax.swing.*;
import model.Complaint;
import util.AppContext;

public class AdminUI {

    public AdminUI() {
        // 🔥 STARTUP DAA SYNC
        AppContext.service.initializeSystem();

        JFrame frame = new JFrame("Admin Panel");
        frame.setSize(400, 500); 
        frame.setLayout(null);

        // 🔹 Process Button (Greedy + BFS + Explainable AI)
        JButton processBtn = new JButton("Process Complaint");
        processBtn.setBounds(100, 40, 200, 40);
        frame.add(processBtn);

        // 🔹 View Priority Order (Greedy Visualization)
        JButton viewSorted = new JButton("View Priority Order");
        viewSorted.setBounds(100, 100, 200, 40);
        frame.add(viewSorted);

        // 🔹 Top-K Complaints (Heap)
        JButton topKBtn = new JButton("Top Complaints");
        topKBtn.setBounds(100, 160, 200, 40);
        frame.add(topKBtn);

        // 🔹 View Trends (Sliding Window)
        JButton trendsBtn = new JButton("View Trends");
        trendsBtn.setBounds(100, 220, 200, 40);
        frame.add(trendsBtn);

        // 🔹 Binary Search (Priority Finding)
        JButton searchPriorityBtn = new JButton("Search by Priority");
        searchPriorityBtn.setBounds(100, 280, 200, 40);
        frame.add(searchPriorityBtn);

        // 🔥 SEARCH PRIORITY (BINARY SEARCH)
        searchPriorityBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter Priority to Search (e.g. 8.5):");
            if (input != null && !input.isEmpty()) {
                try {
                    double target = Double.parseDouble(input);
                    Complaint found = AppContext.service.findByPriority(target);
                    if (found != null) {
                        JOptionPane.showMessageDialog(frame, "Found: " + found.getTitle() + "\nID: " + found.getId() + "\nCategory: " + found.getCategory());
                    } else {
                        JOptionPane.showMessageDialog(frame, "No complaint matches that exact priority.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid priority format.");
                }
            }
        });

        // 🔥 Top-K LOGIC
        topKBtn.addActionListener(e -> {
            java.util.List<model.Complaint> list = AppContext.service.loadComplaintsFromDB();
            if (list == null || list.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No complaints found in Database.");
                return;
            }

            java.util.List<model.Complaint> topK = AppContext.topKService.getTopK(list, 3);
            StringBuilder sb = new StringBuilder("Top 3 High-Priority:\n");
            for (model.Complaint c : topK) {
                sb.append("ID: ").append(c.getId()).append(" | Priority: ").append(c.getPriority()).append(" | Title: ").append(c.getTitle()).append("\n");
            }
            JOptionPane.showMessageDialog(frame, sb.toString());
        });

        // 🔥 TRENDS LOGIC
        trendsBtn.addActionListener(e -> {
            java.util.List<model.Complaint> list = AppContext.service.loadComplaintsFromDB();
            if (list == null || list.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No complaints in DB.");
                return;
            }

            String daysInput = JOptionPane.showInputDialog(frame, "Analyze last N days:", "7");
            int days = (daysInput != null) ? Integer.parseInt(daysInput) : 7;
            
            java.util.List<model.Complaint> trend = AppContext.trendService.getComplaintsLastNDays(list, days);
            JOptionPane.showMessageDialog(frame, "Trends: Found " + trend.size() + " complaints in the last " + days + " days.");
        });

        // 🔥 PROCESS BUTTON LOGIC
        processBtn.addActionListener(e -> {
            Complaint c = AppContext.service.processNext();
            if (c == null) {
                JOptionPane.showMessageDialog(frame, "No complaints to process.");
            } else {
                String team = AppContext.service.assignTeam(c);
                AppContext.dao.updateStatus(c.getId(), "ASSIGNED");
                java.util.List<Integer> related = AppContext.service.getRelatedComplaints(c.getId());
                
                String explanation = "Priority Breakdown:\n" +
                                     "Severity (" + c.getSeverity() + ") × 0.5\n" +
                                     "Urgency (" + c.getUrgency() + ") × 0.3\n" +
                                     "Impact (" + c.getImpact() + ") × 0.2\n\n" +
                                     "Final Priority = " + c.getPriority();

                JOptionPane.showMessageDialog(frame, 
                    "Processed ID: " + c.getId() + "\nTitle: " + c.getTitle() + "\nTeam: " + team +
                    "\nRelated IDs: " + related + "\n\n" + explanation);
            }
        });

        // 🔥 GREEDY SORT DISPLAY
        viewSorted.addActionListener(e -> {
            java.util.List<Complaint> list = AppContext.service.getSortedComplaints();
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No complaints available.");
                return;
            }
            StringBuilder sb = new StringBuilder("Priority List (Greedy Sorting):\n");
            for (Complaint c : list) {
                sb.append("ID: ").append(c.getId()).append(" | Priority: ").append(c.getPriority()).append(" | Title: ").append(c.getTitle()).append("\n");
            }
            JOptionPane.showMessageDialog(frame, sb.toString());
        });

        frame.setVisible(true);
    }
}
