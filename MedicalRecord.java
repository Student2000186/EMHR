package medicalRecord;

public abstract class MedicalRecord {

    protected String patientID;
    protected String date;
    protected String purposeForProcessing;

    public MedicalRecord(String patientID, String date, String purposeForProcessing) {
        this.patientID = patientID;
        this.date = date;
        this.purposeForProcessing = purposeForProcessing;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getDate() {
        return date;
    }

    public String getPurposeForProcessing() {
        return purposeForProcessing;
    }

    public abstract void display();
}
