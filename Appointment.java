package appointment;

import java.time.LocalDateTime;

public class Appointment {


    public enum Status { SCHEDULED, COMPLETED, CANCELLED, NO_SHOW }

    private String patientID;
    private String staffID;
    private LocalDateTime time;
    private Status status;

    public Appointment(String patientID, String staffID, LocalDateTime time, Status status) {
        this.patientID = patientID;
        this.staffID = staffID;
        this.time = time;
        this.status = (status == null) ? Status.SCHEDULED : status;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String toFileString() {
        return patientID + "|" + staffID + "|" + time + "|" + status;
    }

    public static Appointment fromFileString(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length != 4) {
            return null;
        }

        return new Appointment(
            parts[0],
            parts[1],
            LocalDateTime.parse(parts[2]),
            Status.valueOf(parts[3])
        );
    }

    public void display() {
        System.out.println("Patient ID: " + patientID);
        System.out.println("Staff ID: " + staffID);
        System.out.println("Time: " + time);
        System.out.println("Status: " + status);
    }
}
