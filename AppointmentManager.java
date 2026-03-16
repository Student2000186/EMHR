package appointmentManager;

import appointment.Appointment;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.Duration;

public class AppointmentManager {

    // Stores all appointments in memory while the program is running
    private ArrayList<Appointment> appointments = new ArrayList<>();

    // Checks if a staff member already has an appointment at the exact same time
    public boolean checkClash(String staffID, LocalDateTime time) {

        for (Appointment a : appointments) {

            // If same staff member and same time are found, return true
            if (a.getStaffID().equals(staffID) && a.getTime().equals(time)) {
                return true;
            }
        }

        // No clash found
        return false;
    }

    // Adds an appointment after checking for conflicts
    public void addAppointment(Appointment appt) {

        // Prevent appointment if staff member has another within 15 minutes
        if (hasConflict(appt)) {
            System.out.println("Scheduling conflict! Staff must have a 15-minute gap.");
            return;
        }

        appointments.add(appt);
        System.out.println("Appointment Scheduled Successfully!");
    }

    // Checks if an appointment is within 15 minutes of another appointment for the same staff member
    public boolean hasConflict(Appointment newAppt) {

        for (Appointment existing : appointments) {

            // Only compare appointments for the same staff member
            if (existing.getStaffID().equals(newAppt.getStaffID())) {

                // Calculate the difference in minutes between appointments
                long minutes = Math.abs(
                    Duration.between(existing.getTime(), newAppt.getTime()).toMinutes()
                );

                // If less than 15 minutes apart, it is a conflict
                if (minutes < 15)
                    return true;
            }
        }

        // No conflict found
        return false;
    }

    // Displays all appointments stored in the list
    public void viewAppointments() {

        for (Appointment a : appointments) {
            a.display();
        }
    }

    // Updates the status of an appointment using its time
    public void updateStatus(LocalDateTime time, Appointment.Status status) {

        for (Appointment a : appointments) {

            // Find the matching appointment by date and time
            if (a.getTime().equals(time)) {

                // Update the status
                a.setStatus(status);

                System.out.println("Status Updated!");
                return;
            }
        }

        // If no appointment was found with that time
        System.out.println("Appointment Not Found");
    }
}