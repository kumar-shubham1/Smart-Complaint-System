package service;

import model.Complaint;
import util.AppContext;
import java.util.*;

public class ComplaintService {

    private PriorityQueue<Complaint> queue;
    private HashMap<Integer, Complaint> map;
    private HashMap<String, String> teamMap;

    // GRAPH for related complaints
    private Map<Integer, List<Integer>> graph;

    public ComplaintService() {
        // Greedy: Max-Heap based on priority
        queue = new PriorityQueue<>((c1, c2) -> Double.compare(c2.getPriority(), c1.getPriority()));
        map = new HashMap<>(); // Hashing: O(1) Lookup
        graph = new HashMap<>(); // Graph: Adjacency List for BFS
        teamMap = new HashMap<>();
        
        teamMap.put("IT", "IT Team");
        teamMap.put("Maintenance", "Maintenance Team");
        teamMap.put("Service", "Service Team");
    }

    /**
     * 🔥 DAA CENTRAL COMMAND: Rebuilds initial state from DB.
     */
    public void initializeSystem() {
        queue.clear();
        map.clear();
        graph.clear();
        
        List<Complaint> list = loadComplaintsFromDB();
        for (Complaint c : list) {
            addComplaintToMemory(c);
        }
    }

    private void addComplaintToMemory(Complaint c) {
        if (c == null) return;
        queue.add(c);
        map.put(c.getId(), c);

        // Build Graph relationship: Connect complaints in same category (O(N) per add)
        graph.putIfAbsent(c.getId(), new ArrayList<>());
        for (Complaint other : map.values()) {
            if (other.getId() != c.getId() && 
                other.getCategory() != null && 
                other.getCategory().equalsIgnoreCase(c.getCategory())) {
                
                graph.get(c.getId()).add(other.getId());
                graph.get(other.getId()).add(c.getId());
            }
        }
    }

    // Called by DAO after successful SQL Insert
    public void addComplaint(Complaint c) {
        addComplaintToMemory(c);
    }

    /**
     * 🔥 GREEDY Processing: Pops highest priority from Queue
     */
    public Complaint processNext() {
        return queue.poll();
    }

    public List<Complaint> getSortedComplaints() {
        List<Complaint> list = new ArrayList<>(map.values());
        list.sort((a, b) -> Double.compare(b.getPriority(), a.getPriority()));
        return list;
    }

    /**
     * 🔥 BFS: Traverses the graph of related complaints
     */
    public List<Integer> getRelatedComplaints(int id) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> q = new LinkedList<>();

        if (!graph.containsKey(id)) return result;

        q.add(id);
        visited.add(id);

        while (!q.isEmpty()) {
            int curr = q.poll();
            result.add(curr);
            for (int nei : graph.getOrDefault(curr, new ArrayList<>())) {
                if (!visited.contains(nei)) {
                    visited.add(nei);
                    q.add(nei);
                }
            }
        }
        return result;
    }

    /**
     * 🔥 BINARY SEARCH: Specifically finds a priority target in a sorted list
     */
    public Complaint findByPriority(double target) {
        List<Complaint> list = getSortedComplaints(); // Binary Search NEEDS a sorted list
        int low = 0, high = list.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            double midVal = list.get(mid).getPriority();
            if (Math.abs(midVal - target) < 0.01) return list.get(mid);
            if (midVal < target) high = mid - 1;
            else low = mid + 1;
        }
        return null;
    }

    public String assignTeam(Complaint c) {
        return teamMap.getOrDefault(c.getCategory(), "General Team");
    }

    public List<Complaint> loadComplaintsFromDB() {
        // Safe access to optimized DAO loader
        return util.AppContext.dao.getAllComplaints();
    }
}
