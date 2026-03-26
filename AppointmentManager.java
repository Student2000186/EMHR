package appointmentManager;

import appointment.Appointment;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentManager {

    private ArrayList<Appointment> appointments;

    public AppointmentManager() {
        this.appointments = new ArrayList<>();
    }

    public AppointmentManager(List<Appointment> loadedAppointments) {
        this.appointments = new ArrayList<>(loadedAppointments);
    }

    public boolean checkClash(String staffID, LocalDateTime time) {
        for (Appointment a : appointments) {
            if (a.getStaffID().equalsIgnoreCase(staffID) && a.getTime().equals(time)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasConflict(Appointment newAppt) {
        for (Appointment existing : appointments) {
            if (existing.getStaffID().equalsIgnoreCase(newAppt.getStaffID())
                    && existing.getStatus() != Appointment.Status.CANCELLED) {

                long minutes = Math.abs(
                    Duration.between(existing.getTime(), newAppt.getTime()).toMinutes()
                );

                if (minutes < 15) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addAppointment(Appointment appt) {
        if (hasConflict(appt)) {
            return false;
        }

        appointments.add(appt);
        return true;
    }

    public void viewAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }

        for (Appointment a : appointments) {
            a.display();
            System.out.println("----------------------");
        }
    }

    public boolean updateStatus(LocalDateTime time, Appointment.Status status) {
        for (Appointment a : appointments) {
            if (a.getTime().equals(time)) {
                a.setStatus(status);
                return true;
            }
        }
        return false;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }
}
