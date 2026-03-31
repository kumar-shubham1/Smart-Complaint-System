package service;

import java.util.*;

public class CategoryBitmaskService {

    private final static Map<String, Integer> CATEGORY_MAP = new HashMap<>();

    static {
        CATEGORY_MAP.put("IT", 1);          // 2^0
        CATEGORY_MAP.put("Maintenance", 2);  // 2^1
        CATEGORY_MAP.put("Service", 4);      // 2^2
        CATEGORY_MAP.put("HR", 8);           // 2^3
        CATEGORY_MAP.put("FINANCE", 16);     // 2^4
    }

    /**
     * Encodes a list of categories into a single integer using bitmasking.
     * @param categories Selected categories.
     * @return The combined bitmask of categories.
     */
    public int encodeCategories(List<String> categories) {
        int mask = 0;
        if (categories == null) return 0;
        for (String cat : categories) {
            mask |= CATEGORY_MAP.getOrDefault(cat, 0);
        }
        return mask;
    }

    /**
     * Checks if a specific category bit is set in the mask.
     */
    public boolean hasCategory(int mask, String category) {
        int bit = CATEGORY_MAP.getOrDefault(category, 0);
        return (mask & bit) != 0;
    }
}
