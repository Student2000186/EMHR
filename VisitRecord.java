package visitRecord;

import medicalRecord.MedicalRecord;
import vitals.Vitals;

public class VisitRecord extends MedicalRecord {

    private Vitals vitals;
    private String diagnosis;
    private String prescription;
    private String allergies;
    private String labResults;
    private String immunizationDate;

    public VisitRecord(String patientID, String date, String purposeForProcessing,
                       Vitals vitals, String diagnosis, String prescription,
                       String allergies, String labResults, String immunizationDate) {
        super(patientID, date, purposeForProcessing);
        this.vitals = vitals;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.allergies = allergies;
        this.labResults = labResults;
        this.immunizationDate = immunizationDate;
    }

    public Vitals getVitals() {
        return vitals;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public String getAllergies() {
        return allergies;
    }

    public String getLabResults() {
        return labResults;
    }

    public String getImmunizationDate() {
        return immunizationDate;
    }

    public String toFileString() {
        return escape(patientID) + "|" +
               escape(date) + "|" +
               escape(purposeForProcessing) + "|" +
               vitals.getTemperature() + "|" +
               vitals.getHeartRate() + "|" +
               escape(vitals.getBloodPressure()) + "|" +
               vitals.getOxygenLevel() + "|" +
               escape(diagnosis) + "|" +
               escape(prescription) + "|" +
               escape(allergies) + "|" +
               escape(labResults) + "|" +
               escape(immunizationDate);
    }

    public static VisitRecord fromFileString(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length != 12) {
            return null;
        }

        Vitals vitals = new Vitals(
            Double.parseDouble(parts[3]),
            Integer.parseInt(parts[4]),
            parts[5],
            Integer.parseInt(parts[6])
        );

        return new VisitRecord(
            parts[0],
            parts[1],
            parts[2],
            vitals,
            parts[7],
            parts[8],
            parts[9],
            parts[10],
            parts[11]
        );
    }

    private static String escape(String value) {
        return value == null ? "" : value.replace("|", "/");
    }

    @Override
    public void display() {
        System.out.println("Patient ID: " + patientID);
        System.out.println("Visit Date: " + date);
        System.out.println("Purpose for Processing: " + purposeForProcessing);
        System.out.println("Vitals: " + vitals);
        System.out.println("Diagnosis: " + diagnosis);
        System.out.println("Prescription: " + prescription);
        System.out.println("Allergies: " + allergies);
        System.out.println("Lab Results: " + labResults);
        System.out.println("Immunization Date: " + immunizationDate);
    }
}
