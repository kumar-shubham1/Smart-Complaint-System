package service;

import java.util.*;

public class ComplaintClusterService {

    private Map<Integer, Integer> parent;
    private int clustersFound;

    public ComplaintClusterService() {
        this.parent = new HashMap<>();
        this.clustersFound = 0;
    }

    /**
     * Initializes a complaint ID in the Disjoint Set.
     */
    public void add(int x) {
        if (!parent.containsKey(x)) {
            parent.put(x, x);
            clustersFound++;
        }
    }

    /**
     * Finds the representative root of a complaint group.
     */
    public int find(int x) {
        if (parent.get(x) == x)
            return x;
        int root = find(parent.get(x));
        parent.put(x, root); // Path compression
        return root;
    }

    /**
     * Unites two complaints into the same cluster.
     */
    public void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA != rootB) {
            parent.put(rootA, rootB);
            clustersFound--;
        }
    }

    public int getClusterCount() {
        return clustersFound;
    }

    /**
     * Groups complaints together using Union-Find.
     */
    public Map<Integer, List<Integer>> getClusters() {
        Map<Integer, List<Integer>> groupings = new HashMap<>();
        for (int id : parent.keySet()) {
            int root = find(id);
            groupings.computeIfAbsent(root, k -> new ArrayList<>()).add(id);
        }
        return groupings;
    }
}
