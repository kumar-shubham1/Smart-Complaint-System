package service;

import model.Complaint;
import java.util.*;

public class TopKService {

    /**
     * Finds the Top-K complaints with the highest priority using a Heap (Min-Heap approach).
     * @param complaints The collection of all complaints.
     * @param k The number of top complaints to retrieve.
     * @return List of top K complaints sorted by priority descending.
     */
    public List<Complaint> getTopK(Collection<Complaint> complaints, int k) {
        if (complaints == null || k <= 0) return new ArrayList<>();

        // Min-Heap (Lowest priority at the head)
        PriorityQueue<Complaint> minHeap = new PriorityQueue<>(
            Comparator.comparingDouble(Complaint::getPriority)
        );

        for (Complaint c : complaints) {
            minHeap.add(c);
            if (minHeap.size() > k) {
                minHeap.poll(); // remove the smallest priority element
            }
        }

        List<Complaint> result = new ArrayList<>(minHeap);
        // Sort descending because heap gives smallest first
        result.sort((c1, c2) -> Double.compare(c2.getPriority(), c1.getPriority()));
        return result;
    }
}
