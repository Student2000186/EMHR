package vitals;

public class Vitals {
	
	 private double temperature;
	    private int heartRate;
	    private String bloodPressure;
	    private int oxygenLevel;
	    
	    public Vitals(double temperature, int heartRate, int oxygenLevel) {
	        this.temperature = temperature;
	        this.heartRate = heartRate;
	        this.oxygenLevel = oxygenLevel;
	    }

		public double getTemperature() {
			return temperature;
		}

		public void setTemperature(double temperature) {
			this.temperature = temperature;
		}

		public int getHeartRate() {
			return heartRate;
		}

		public void setHeartRate(int heartRate) {
			this.heartRate = heartRate;
		}

		public String getBloodPressure() {
			return bloodPressure;
		}

		public void setBloodPressure(String bloodPressure) {
			this.bloodPressure = bloodPressure;
		}

		public int getOxygenLevel() {
			return oxygenLevel;
		}

		public void setOxygenLevel(int oxygenLevel) {
			this.oxygenLevel = oxygenLevel;
		}



	    
}
