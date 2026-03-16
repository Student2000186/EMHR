package appointment;


//LocalDateTime is used to store both date and time of the appointment
import java.time.LocalDateTime;

public class Appointment {
	
	// Enum is used so status can only be one of these fixed values
    public enum Status { SCHEDULED, COMPLETED, CANCELLED, NO_SHOW }


    // Stores the patient linked to the appointment
    private String patientID;

    // Stores the staff member linked to the appointment
    private String staffID;

    // Stores the appointment date and time
    private LocalDateTime time;

    // Stores the current status of the appointment
    private Status status;
    
 // Constructor used when creating a new appointment
    public Appointment(String patientID, String staffID, LocalDateTime time, Status status){
        this.patientID = patientID;
        this.staffID = staffID;
        this.time = time;
        this.status = Status.SCHEDULED;
    }

    
    
 // Returns patient ID
    public String getPatientID() {
		return patientID;
	}



 // Updates PatientID 
	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}



	// Returns staff ID
	public String getStaffID() {
		return staffID;
	}



	// Updates  updated Staff ID
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}



	// Returns appointment time
	public LocalDateTime getTime() {
		return time;
	}



	// Updates  Appointment Time
	public void setTime(LocalDateTime time) {
		this.time = time;
	}



	// Returns staff ID
	public Status getStatus() {
		return status;
	}



    // Updates appointment status
	public void setStatus(Status status) {
		this.status = status;
	}



    // Updates appointment status
    public void display(){
        System.out.println("Patient ID: " + patientID);
        System.out.println("Staff ID: " + staffID);
        System.out.println("Time: " + time);
        System.out.println("Status: " + status);
    }
 
}
