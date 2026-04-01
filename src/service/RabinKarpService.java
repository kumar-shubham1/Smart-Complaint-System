package service;

import model.Complaint;
import java.util.*;

public class RabinKarpService {

    private final static int PRIME = 101; 

    /**
     * Checks if a text matches any existing complaint description using Rabin-Karp logic.
     * Normalized with trim() and lower case to avoid false negatives.
     */
    public boolean isDuplicate(String description, Collection<Complaint> complaints) {
        if (description == null || complaints == null) return false;

        // Normalization (trim + lower case)
        String pattern = description.trim().toLowerCase();
        long patternHash = computeHash(pattern);

        for (Complaint c : complaints) {
            String text = c.getDescription().trim().toLowerCase();
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
