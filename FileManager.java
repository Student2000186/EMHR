package fileManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import visitRecord.VisitRecord;
import java.time.LocalDateTime;

import patient.Patient;
import appointment.Appointment;


public class FileManager {

    private static final String PATIENT_FILE = "patients.txt";
    private static final String USER_FILE = "users.txt";
    private static final String APPOINTMENT_FILE = "appointments.txt";
    private static final String AUDIT_FILE = "audit.txt";
    private static final String VISIT_FILE = "visits.txt";

    // =========================
    // PATIENT METHODS
    // =========================

    public static void savePatient(Patient patient) {
        try (FileWriter fw = new FileWriter(PATIENT_FILE, true)) {
            fw.write(patient.toFileString() + "\n");
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }

    public static List<Patient> loadPatients() {
        List<Patient> patients = new ArrayList<>();

        try {
            File file = new File(PATIENT_FILE);
            if (!file.exists()) {
                return patients;
            }

            List<String> lines = Files.readAllLines(Paths.get(PATIENT_FILE));
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    Patient patient = Patient.fromFileString(line);
                    if (patient != null) {
                        patients.add(patient);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }

        return patients;
    }

    public static void readPatients() {
        List<Patient> patients = loadPatients();

        if (patients.isEmpty()) {
            System.out.println("No patient records found.");
            return;
        }

        for (Patient patient : patients) {
            patient.display();
            System.out.println("----------------------");
        }
    }

    public static boolean updatePatient(String id, String newContact, String newKin) {
        List<Patient> patients = loadPatients();
        boolean found = false;

        for (Patient patient : patients) {
            if (patient.getId().equalsIgnoreCase(id)) {
                patient.setContact(newContact);
                patient.setNextOfKin(newKin);
                found = true;
                break;
            }
        }

        if (found) {
            overwritePatients(patients);
            System.out.println("Patient Updated Successfully!");
        } else {
            System.out.println("Patient ID not found.");
        }

        return found;
    }

    public static boolean deletePatient(String id) {
        List<Patient> patients = loadPatients();
        boolean removed = patients.removeIf(patient -> patient.getId().equalsIgnoreCase(id));

        if (removed) {
            overwritePatients(patients);
            System.out.println("Patient Deleted!");
        } else {
            System.out.println("Patient ID not found.");
        }

        return removed;
    }

    private static void overwritePatients(List<Patient> patients) {
        try (FileWriter fw = new FileWriter(PATIENT_FILE, false)) {
            for (Patient patient : patients) {
                fw.write(patient.toFileString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }

    // =========================
    // USER METHODS
    // =========================

    public static void saveUser(String data) {
        try (FileWriter fw = new FileWriter(USER_FILE, true)) {
            fw.write(data + "\n");
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }

    // =========================
    // APPOINTMENT METHODS
    // =========================

    public static void saveAppointment(Appointment appointment) {
        try (FileWriter fw = new FileWriter(APPOINTMENT_FILE, true)) {
            fw.write(appointment.toFileString() + "\n");
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }

    public static List<Appointment> loadAppointments() {
        List<Appointment> appointments = new ArrayList<>();

        try {
            File file = new File(APPOINTMENT_FILE);
            if (!file.exists()) {
                return appointments;
            }

            List<String> lines = Files.readAllLines(Paths.get(APPOINTMENT_FILE));
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    Appointment appointment = Appointment.fromFileString(line);
                    if (appointment != null) {
                        appointments.add(appointment);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }

        return appointments;
    }

    public static void overwriteAppointments(List<Appointment> appointments) {
        try (FileWriter fw = new FileWriter(APPOINTMENT_FILE, false)) {
            for (Appointment appointment : appointments) {
                fw.write(appointment.toFileString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }

    // =========================
    // VISIT / EHR METHODS
    // =========================

    public static void saveVisitRecord(VisitRecord visit) {
        try (FileWriter fw = new FileWriter(VISIT_FILE, true)) {
            fw.write(visit.toFileString() + "\n");
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }

    public static List<VisitRecord> loadVisitRecords() {
        List<VisitRecord> visits = new ArrayList<>();

        try {
            File file = new File(VISIT_FILE);
            if (!file.exists()) {
                return visits;
            }

            List<String> lines = Files.readAllLines(Paths.get(VISIT_FILE));
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    VisitRecord visit = VisitRecord.fromFileString(line);
                    if (visit != null) {
                        visits.add(visit);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }

        return visits;
    }

    public static List<VisitRecord> getVisitRecordsByPatientId(String patientId) {
        List<VisitRecord> result = new ArrayList<>();

        for (VisitRecord visit : loadVisitRecords()) {
            if (visit.getPatientID().equalsIgnoreCase(patientId)) {
                result.add(visit);
            }
        }

        return result;
    }

    // =========================
    // AUDIT
    // =========================

    public static void logAudit(String action) {
        try (FileWriter fw = new FileWriter(AUDIT_FILE, true)) {
            fw.write(LocalDateTime.now() + " | " + action + "\n");
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }
    public static List<String> loadRawUsers() {
        List<String> lines = new ArrayList<>();

        try {
            File file = new File(USER_FILE);
            if (!file.exists()) {
                return lines;
            }

            lines = Files.readAllLines(Paths.get(USER_FILE));
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }

        return lines;
    }
    public static void readAllVisitRecords() {
        List<VisitRecord> visits = loadVisitRecords();

        if (visits.isEmpty()) {
            System.out.println("No visit records found.");
            return;
        }

        for (VisitRecord visit : visits) {
            visit.display();
            System.out.println("----------------------");
        }
    }
    public static List<VisitRecord> searchVisitRecordsByDiagnosis(String diagnosis) {
        List<VisitRecord> results = new ArrayList<>();

        for (VisitRecord visit : loadVisitRecords()) {
            if (visit.getDiagnosis().equalsIgnoreCase(diagnosis)) {
                results.add(visit);
            }
        }

        return results;
    }
    public static List<VisitRecord> searchVisitRecordsByDate(String date) {
        List<VisitRecord> results = new ArrayList<>();

        for (VisitRecord visit : loadVisitRecords()) {
            if (visit.getDate().equalsIgnoreCase(date)) {
                results.add(visit);
            }
        }

        return results;
    }
    public static List<VisitRecord> searchVisitRecordsByDateRange(String startDate, String endDate) {
        List<VisitRecord> results = new ArrayList<>();

        for (VisitRecord visit : loadVisitRecords()) {
            if (visit.getDate().compareTo(startDate) >= 0 && visit.getDate().compareTo(endDate) <= 0) {
                results.add(visit);
            }
        }

        return results;
    }
    public static void displayVisitRecordList(List<VisitRecord> visits) {
        if (visits.isEmpty()) {
            System.out.println("No matching visit records found.");
            return;
        }

        for (VisitRecord visit : visits) {
            visit.display();
            System.out.println("----------------------");
        }
    }
}
