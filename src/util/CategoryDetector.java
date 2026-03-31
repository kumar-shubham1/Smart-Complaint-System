package util;

public class CategoryDetector {

    /**
     * Detects the category of a complaint based on keywords found in the
     * description.
     * Uses a score-based heuristic for more balanced classification.
     * 
     * @param desc The complaint description text.
     * @return The most likely category ("IT", "Maintenance", or "Service").
     */
    public static String detectCategory(String desc) {
        if (desc == null)
            return "IT"; // default fallback

        desc = desc.toLowerCase();

        int itScore = 0;
        int maintenanceScore = 0;
        int serviceScore = 0;

        // IT keywords
        if (desc.contains("network") || desc.contains("internet") || desc.contains("server") ||
                desc.contains("system") || desc.contains("login") || desc.contains("software") ||
                desc.contains("computer") || desc.contains("wi-fi")) {
            itScore++;
        }

        // Maintenance keywords
        if (desc.contains("ac") || desc.contains("power") || desc.contains("electric") ||
                desc.contains("water") || desc.contains("leak") || desc.contains("fan") ||
                desc.contains("repair") || desc.contains("cleaning")) {
            maintenanceScore++;
        }

        // Service keywords
        if (desc.contains("food") || desc.contains("cafeteria") || desc.contains("clean") ||
                desc.contains("service") || desc.contains("delay") || desc.contains("quality")) {
            serviceScore++;
        }

        // Decision logic (Order: IT < Maintenance < Service)
        if (itScore >= maintenanceScore && itScore >= serviceScore && itScore > 0) {
            return "IT";
        } else if (maintenanceScore >= itScore && maintenanceScore >= serviceScore && maintenanceScore > 0) {
            return "Maintenance";
        } else if (serviceScore > 0) {
            return "Service";
        }

        return "IT"; // final default if no keywords found
    }
}
