package medicalRecord;

public abstract class MedicalRecord {
	
	  protected String patientID;
	    protected String date;

	    public MedicalRecord(String patientID,String date){
	        this.patientID = patientID;
	        this.date = date;
	    }

	    public abstract void display();

}
