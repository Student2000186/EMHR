package triage;

import java.util.List;

public class Triage {

    private static final int MEDIUM_THRESHOLD = 90;
    private static final int HIGH_THRESHOLD = 120;
    private static final int LARGE_DEVIATION_THRESHOLD = 20;

    public static String riskScore(int heartRate) {
        if (heartRate > HIGH_THRESHOLD) {
            return "HIGH";
        } else if (heartRate > MEDIUM_THRESHOLD) {
            return "MEDIUM";
        }
        return "LOW";
    }

    public static double movingAverage(List<Integer> readings) {
        if (readings == null || readings.isEmpty()) {
            return 0;
        }

        int sum = 0;
        for (int reading : readings) {
            sum += reading;
        }

        return (double) sum / readings.size();
    }

    public static double movingAverage(List<Integer> readings, int lastN) {
        if (readings == null || readings.isEmpty()) {
            return 0;
        }

        if (lastN <= 0 || lastN >= readings.size()) {
            return movingAverage(readings);
        }

        int startIndex = readings.size() - lastN;
        int sum = 0;
        for (int i = startIndex; i < readings.size(); i++) {
            sum += readings.get(i);
        }

        return (double) sum / lastN;
    }

    public static boolean hasLargeDeviation(int currentReading, double average) {
        return Math.abs(currentReading - average) > LARGE_DEVIATION_THRESHOLD;
    }
}
