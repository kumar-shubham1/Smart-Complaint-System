package service;

import model.Complaint;
import java.util.*;
import java.util.stream.Collectors;

public class TrendAnalysisService {

    /**
     * Filters complaints created within the last N days (Sliding Window logic).
     * @param complaints The collection of all complaints.
     * @param days The sliding window size in days.
     * @return List of complaints within the date range.
     */
    public List<Complaint> getComplaintsLastNDays(Collection<Complaint> complaints, int days) {
        if (complaints == null || days <= 0) return new ArrayList<>();

        long nowMilliseconds = System.currentTimeMillis();
        long windowMilliseconds = (long) days * 24 * 60 * 60 * 1000;
        long startTime = nowMilliseconds - windowMilliseconds;

        return complaints.stream()
            .filter(c -> c.getCreatedAt() != null && c.getCreatedAt().getTime() >= startTime)
            .collect(Collectors.toList());
    }
}
