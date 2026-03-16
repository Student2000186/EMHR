package visitRecord;

public class VisitRecord {

    // Stores patient ID for the visit
    private String patientID;

    // Stores visit date
    private String date;

    // Stores patient heart rate
    private int heartRate;

    // Stores patient blood pressure
    private int bloodPressure;

    // Stores diagnosis given during visit
    private String diagnosis;

    // Constructor used when creating a visit record
    public VisitRecord(String patientID, String date, int heartRate, int bloodPressure, String diagnosis) {
        this.patientID = patientID;
        this.date = date;
        this.heartRate = heartRate;
        this.bloodPressure = bloodPressure;
        this.diagnosis = diagnosis;
    }

    // Displays visit details
    public void display() {
        System.out.println("Patient ID: " + patientID);
        System.out.println("Visit Date: " + date);
        System.out.println("Heart Rate: " + heartRate);
        System.out.println("Blood Pressure: " + bloodPressure);
        System.out.println("Diagnosis: " + diagnosis);
    }
}