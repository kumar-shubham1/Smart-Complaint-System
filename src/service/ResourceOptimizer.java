package service;

import model.Complaint;
import java.util.*;

public class ResourceOptimizer {

    /**
     * Uses 0/1 Knapsack logic to assign the highest priority complaints to teams within a limited capacity.
     * @param complaints Full list of pending complaints.
     * @param capacity The maximum number of complaints a team can handle.
     * @return Optimized list of complaints to be assigned.
     */
    public List<Complaint> optimizeAssignments(List<Complaint> complaints, int capacity) {
        if (complaints == null || complaints.isEmpty() || capacity <= 0) return new ArrayList<>();

        int n = complaints.size();
        double[][] dp = new double[n + 1][capacity + 1];

        // Each complaint has cost = 1, and value = priority
        for (int i = 1; i <= n; i++) {
            Complaint c = complaints.get(i - 1);
            for (int w = 1; w <= capacity; w++) {
                if (1 <= w) {
                    dp[i][w] = Math.max(c.getPriority() + dp[i - 1][w - 1], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Backtrack to find chosen complaints
        List<Complaint> result = new ArrayList<>();
        int w = capacity;
        for (int i = n; i > 0 && w > 0; i--) {
            if (dp[i][w] != dp[i-1][w]) {
                result.add(complaints.get(i-1));
                w -= 1;
            }
        }
        return result;
    }
}
