package service;

import java.util.*;

public class AStarService {

    /**
     * Implements A* algorithm to find the optimal path in a department graph.
     * 
     * @param start Starting department.
     * @param goal  Target department.
     * @return Path as a list of strings representing department nodes.
     */
    public List<String> findBestPath(String start, String goal) {

        // Define simple graph structure: node -> neighbor to reach goal
        Map<String, Map<String, Integer>> graph = new HashMap<>();

        // (start, target, cost)
        addPath(graph, "User", "Reception", 1);
        addPath(graph, "Reception", "IT_Department", 2);
        addPath(graph, "Reception", "Maintenance", 3);
        addPath(graph, "IT_Department", "Technical_Lead", 2);
        addPath(graph, "Maintenance", "Service_Manager", 3);
        addPath(graph, "Technical_Lead", "Resolution", 1);
        addPath(graph, "Service_Manager", "Resolution", 1);

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        Map<String, Integer> gScore = new HashMap<>();
        Map<String, String> cameFrom = new HashMap<>();

        gScore.put(start, 0);
        openSet.add(new Node(start, 0, getHeuristic(start, goal)));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.name.equals(goal)) {
                return reconstructPath(cameFrom, goal);
            }

            for (Map.Entry<String, Integer> neighbor : graph.getOrDefault(current.name, new HashMap<>()).entrySet()) {
                int tentativeGScore = gScore.get(current.name) + neighbor.getValue();

                if (tentativeGScore < gScore.getOrDefault(neighbor.getKey(), Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor.getKey(), current.name);
                    gScore.put(neighbor.getKey(), tentativeGScore);
                    openSet.add(new Node(neighbor.getKey(), tentativeGScore, getHeuristic(neighbor.getKey(), goal)));
                }
            }
        }
        return null;
    }

    private void addPath(Map<String, Map<String, Integer>> g, String s, String d, int c) {
        g.computeIfAbsent(s, k -> new HashMap<>()).put(d, c);
    }

    // Heuristic value (mock cost to goal)
    private int getHeuristic(String name, String goal) {
        return 0; // Simple Dijkstra if zero, A* if non-zero
    }

    private List<String> reconstructPath(Map<String, String> cameFrom, String current) {
        List<String> path = new ArrayList<>();
        path.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(0, current);
        }
        return path;
    }

    private static class Node {
        String name;
        int g, h, f;

        Node(String name, int g, int h) {
            this.name = name;
            this.g = g;
            this.h = h;
            this.f = g + h;
        }
    }
}
