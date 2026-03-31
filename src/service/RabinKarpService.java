package service;

import model.Complaint;
import java.util.*;

public class RabinKarpService {

    private final static int PRIME = 101; // A prime number for hashing

    /**
     * Checks if a text matches any existing complaint description using Rabin-Karp logic.
     * @param description The new complaint description to check.
     * @param complaints The collection of all current complaints.
     * @return true if a duplicate match is found.
     */
    public boolean isDuplicate(String description, Collection<Complaint> complaints) {
        if (description == null || complaints == null) return false;

        String pattern = description.trim();
        long patternHash = computeHash(pattern);

        for (Complaint c : complaints) {
            String text = c.getDescription().trim();
            // In a small-scale system, it's safer to compare full texts, but Rabin-Karp logic is applied:
            if (computeHash(text) == patternHash && text.equals(pattern)) {
                return true; 
            }
        }
        return false;
    }

    /**
     * Standard polynomial hash for Rabin-Karp simplicity.
     */
    private long computeHash(String s) {
        long h = 0;
        for (int i = 0; i < s.length(); i++) {
            h = (h * PRIME) + s.charAt(i);
        }
        return h;
    }
}
