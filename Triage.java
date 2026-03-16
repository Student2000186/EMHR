package triage;


import java.util.ArrayList;

public class Triage {

	  public static String riskScore(int heartRate) {

	        if (heartRate > 120)
	            return "HIGH";
	        else if (heartRate > 90)
	            return "MEDIUM";
	        else
	            return "LOW";
	    }
    public static double movingAverage(ArrayList<Integer> readings) {
        if (readings == null || readings.isEmpty()) {
            return 0;
        }

        int sum = 0;
        for (int value : readings) {
            sum += value;
        }

        return (double) sum / readings.size();
    }

    public static boolean hasLargeDeviation(int currentReading, double average) {
        return Math.abs(currentReading - average) > 20;
    }
}