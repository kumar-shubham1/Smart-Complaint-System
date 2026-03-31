package service;

public class PeakAnalysisService {

    /**
     * Finds the maximum sum of a contiguous sub-array (peak burst of complaints).
     * @param frequencyArray Array representing complaint counts over a time range.
     * @return The maximum contiguous sum found via Kadane's algorithm.
     */
    public int maxComplaintBurst(int[] frequencyArray) {
        if (frequencyArray == null || frequencyArray.length == 0) return 0;
        
        int maxSoFar = 0;
        int currentMax = 0;

        for (int count : frequencyArray) {
            currentMax = currentMax + count;
            if (currentMax < 0) {
                currentMax = 0;
            }
            if (maxSoFar < currentMax) {
                maxSoFar = currentMax;
            }
        }
        return maxSoFar;
    }
}
