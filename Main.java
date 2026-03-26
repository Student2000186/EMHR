//EMHR Group PROJECT//

package main;

import java.util.ArrayList;
// Scanner allows the program to read input from the user
import java.util.List;
import java.util.Scanner;

// Importing classes from other packages used in the system
import appointment.Appointment;
import appointment.Appointment.Status;
import appointmentManager.AppointmentManager;
import cardPayment.CardPayment;
import cashPayment.CashPayment;
import fileManager.FileManager;
import insuranceBilling.InsuranceBilling;
import metrics.Metrics;
import patient.Patient;
import payment.Payment;
import role.Role;
import triage.Triage;
import user.User;
import userManager.UserManager;
import visitRecord.VisitRecord;
import notificationService.NotificationService;
import emailObserver.EmailObserver;
import smsObserver.SmsObserver;


// Import classes used to handle date and time
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
	

    // Console color codes used to improve the visual appearance of the system
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";
    public static final String PURPLE = "\u001B[35m";

    public static void main(String[] args) throws InterruptedException {

        // Scanner object reads user input from the keyboard
        Scanner input = new Scanner(System.in);

        // Stores the user menu choice
        int choice;

        // Manager classes control specific parts of the system
        UserManager userManager = new UserManager();
        AppointmentManager appointmentManager = new AppointmentManager(FileManager.loadAppointments());
        // Notification classes sends alerts to user and patients
        NotificationService notificationService = new NotificationService();
        notificationService.addObserver(new EmailObserver());
        notificationService.addObserver(new SmsObserver());
        User currentUser = null;
        
        while (currentUser == null) {
            System.out.println(CYAN + "\n===== EMHR LOGIN =====" + RESET);
            System.out.print("Enter Username: ");
            String loginUsername = input.next();
            System.out.print("Enter Password: ");
            String loginPassword = input.next();

            currentUser = userManager.authenticate(loginUsername, loginPassword);

            if (currentUser == null) {
            	
                System.out.println(RED + "Invalid login. Try again." + RESET);
            } else {
                System.out.println(GREEN + "Welcome, " + currentUser.getFullName() + "!" + RESET);
                FileManager.logAudit("User logged in: " + currentUser.getUsername());
            }
        }
        
        // Defines the format used for appointment date and time
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Program runs continuously until the user chooses Exit
        do {

            // Display a small loading animation before the menu appears
            loading();

            // Clear the screen to keep the interface tidy
            clearScreen();

            
            
            // Show the menu
            displayMenu();

            try {
                System.out.print("Enter Choice: ");
                choice = input.nextInt();
                input.nextLine(); // clear leftover newline
            } catch (Exception e) {
                System.out.println("Invalid menu input.");
                input.nextLine();
                choice = 0;
            }

            switch (choice) {

                // =========================================================
                // PATIENT MANAGEMENT
                // =========================================================

            case 1: {
                if (!requirePermission(currentUser, "addPatient")) {
                    pause(input);
                    break;
                }

                try {
                    System.out.print("Enter Patient Name: ");
                    String name = input.nextLine();

                    System.out.print("Enter Patient ID: ");
                    String id = input.nextLine();

                    System.out.print("Enter Contact Number: ");
                    String contact = input.nextLine();

                    System.out.print("Enter Next of Kin: ");
                    String kin = input.nextLine();

                    Patient patient = new Patient(name, id, contact, kin);
                    FileManager.savePatient(patient);
                    FileManager.logAudit("Added patient: " + id + " by " + currentUser.getUsername());

                    System.out.println(GREEN + "Patient added successfully." + RESET);
                } catch (Exception e) {
                    System.out.println(RED + "Error adding patient." + RESET);
                }

                pause(input);
                break;
            }
            
                case 2: {// VIEW PATIENTS
                	if (!requirePermission(currentUser, "viewPatients")) {
                        pause(input);
                        break;
                    }

                    
                    
                	System.out.println(BLUE + "\n--- Patient Records ---" + RESET);

                    // Display all stored patients
                    FileManager.readPatients();

                    input.nextLine();
                    pause(input);
                    break;
            }
                case 3: {
                	if (!requirePermission(currentUser, "updatePatient")) {
                	    pause(input);
                	    break;
                	}
                    try {
                        System.out.print("Enter Patient ID to update: ");
                        String updateId = input.next();

                        System.out.print("Enter New Contact Number: ");
                        String newContact = input.next();

                        System.out.print("Enter New Next of Kin: ");
                        String newKin = input.next();

                        boolean patientUpdated = FileManager.updatePatient(updateId, newContact, newKin);
                        if (patientUpdated) {
                            FileManager.logAudit("Updated patient: " + updateId);
                        }
                    } catch (Exception e) {
                        System.out.println(RED + "Error updating patient." + RESET);
                    }

                    input.nextLine();
                    pause(input);
                    break;
                }

                case 4: {// DELETE PATIENT
                	if (!requirePermission(currentUser, "deletePatient")) {
                	    pause(input);
                	    break;
                	}
                    try {

                        System.out.print("Enter Patient ID to delete: ");
                        String deleteId = input.next();

                        boolean deleted = FileManager.deletePatient(deleteId);
                        if (deleted) {
                            FileManager.logAudit("Deleted patient: " + deleteId);
                        }

                    } catch (Exception e) {

                        System.out.println(RED + "Error deleting patient." + RESET);
                    }

                    input.nextLine();
                    pause(input);
                    break;
            }
                // =========================================================
                // USER / ROLE MANAGEMENT
                // =========================================================

                case 5: {
                    if (!requirePermission(currentUser, "createUser")) {
                        pause(input);
                        break;
                    }

                    try {
                        input.nextLine();
                        System.out.print("Enter Full Name: ");
                        String fullName = input.nextLine();

                        System.out.print("Enter Username: ");
                        String username = input.next();

                        System.out.print("Enter Password: ");
                        String password = input.next();

                        System.out.print("Enter Role (ADMIN/MANAGER/USER): ");
                        String roleName = input.next();

                        User newUser = new User(fullName, username, password);
                        newUser.addRole(new Role(roleName));

                        boolean userAdded = userManager.addUser(newUser);

                        if (userAdded) {
                            FileManager.logAudit("User created by " + currentUser.getUsername() + ": " + username);
                            System.out.println(GREEN + "User added successfully." + RESET);
                        } else {
                            System.out.println(RED + "Failed to add user." + RESET);
                        }
                    } catch (Exception e) {
                        System.out.println(RED + "Error adding user." + RESET);
                        input.nextLine();
                    }

                    pause(input);
                    break;
                }

                case 6: {
                    if (!requirePermission(currentUser, "viewUser")) {
                        pause(input);
                        break;
                    }

                    System.out.print("Enter Username to View: ");
                    String usernameToView = input.next();

                    userManager.viewSingleUser(usernameToView);
                    FileManager.logAudit("Viewed single user: " + usernameToView + " by " + currentUser.getUsername());

                    input.nextLine();
                    pause(input);
                    break;
                }

                case 7: {
                    if (!requirePermission(currentUser, "viewAllUsers")) {
                        pause(input);
                        break;
                    }

                    userManager.viewAllUsers();
                    FileManager.logAudit("Viewed all users by " + currentUser.getUsername());

                    input.nextLine();
                    pause(input);
                    break;
                }

                case 8: {
                    if (!requirePermission(currentUser, "updateUser")) {
                        pause(input);
                        break;
                    }

                    try {
                        System.out.print("Enter Username to Update: ");
                        String usernameToUpdate = input.next();

                        input.nextLine();
                        System.out.print("Enter New Full Name: ");
                        String newFullName = input.nextLine();

                        System.out.print("Enter New Password: ");
                        String newPassword = input.next();

                        boolean userUpdated = userManager.updateUser(usernameToUpdate, newFullName, newPassword);

                        if (userUpdated) {
                            FileManager.logAudit("User updated by " + currentUser.getUsername() + ": " + usernameToUpdate);
                            System.out.println(GREEN + "User updated successfully." + RESET);
                        } else {
                            System.out.println(RED + "User update failed." + RESET);
                        }
                    } catch (Exception e) {
                        System.out.println(RED + "Error updating user." + RESET);
                        input.nextLine();
                    }

                    input.nextLine();
                    pause(input);
                    break;
                }

                case 9: {
                    if (!requirePermission(currentUser, "deleteUser")) {
                        pause(input);
                        break;
                    }

                    System.out.print("Enter Username to Delete: ");
                    String usernameToDelete = input.next();

                    boolean userDeleted = userManager.deleteUser(usernameToDelete);

                    if (userDeleted) {
                        FileManager.logAudit("User deleted by " + currentUser.getUsername() + ": " + usernameToDelete);
                        System.out.println(GREEN + "User deleted successfully." + RESET);
                    } else {
                        System.out.println(RED + "User delete failed." + RESET);
                    }

                    input.nextLine();
                    pause(input);
                    break;
                }

                case 10: {
                    if (!requirePermission(currentUser, "viewOwnProfile")) {
                        pause(input);
                        break;
                    }

                    userManager.viewOwnProfile(currentUser);
                    FileManager.logAudit("Viewed own profile: " + currentUser.getUsername());

                    input.nextLine();
                    pause(input);
                    break;
                }

                case 11: {
                    if (!requirePermission(currentUser, "scheduleAppointment")) {
                        pause(input);
                        break;
                    }

                    try {
                        System.out.print("Enter Patient ID: ");
                        String patientID = input.next();

                        System.out.print("Enter Staff ID: ");
                        String staffID = input.next();

                        input.nextLine();
                        System.out.print("Enter Appointment Date and Time (yyyy-MM-dd HH:mm): ");
                        String inputTime = input.nextLine();

                        LocalDateTime time = LocalDateTime.parse(inputTime, format);

                        Appointment appt = new Appointment(patientID, staffID, time, Status.SCHEDULED);

                        boolean appointmentAdded = appointmentManager.addAppointment(appt);

                        if (appointmentAdded) {
                            FileManager.saveAppointment(appt);
                            Metrics.appointmentsBooked++;
                            notificationService.notifyObservers("Appointment booked for patient: " + patientID);
                            FileManager.logAudit("Booked appointment for patient: " + patientID + " by " + currentUser.getUsername());
                            System.out.println(GREEN + "Appointment scheduled successfully." + RESET);
                        } else {
                            Metrics.conflicts++;
                            FileManager.logAudit("Appointment conflict for patient: " + patientID + " by " + currentUser.getUsername());
                            System.out.println(RED + "Scheduling conflict detected." + RESET);
                        }

                    } catch (Exception e) {
                        System.out.println(RED + "Error scheduling appointment." + RESET);
                        input.nextLine();
                    }

                    pause(input);
                    break;
                }

                case 12: {
                    if (!requirePermission(currentUser, "viewAppointments")) {
                        pause(input);
                        break;
                    }

                    appointmentManager.viewAppointments();
                    FileManager.logAudit("Viewed appointments by " + currentUser.getUsername());

                    input.nextLine();
                    pause(input);
                    break;
                }

                case 13: {
                    if (!requirePermission(currentUser, "updateAppointmentStatus")) {
                        pause(input);
                        break;
                    }

                    try {
                        input.nextLine();
                        System.out.print("Enter Appointment Time to Update (yyyy-MM-dd HH:mm): ");
                        String statusTime = input.nextLine();

                        LocalDateTime appointmentTime = LocalDateTime.parse(statusTime, format);

                        System.out.print("Enter New Status (SCHEDULED, COMPLETED, CANCELLED, NO_SHOW): ");
                        String statusInput = input.next().toUpperCase();

                        Appointment.Status newStatus = Appointment.Status.valueOf(statusInput);

                        boolean statusUpdated = appointmentManager.updateStatus(appointmentTime, newStatus);

                        if (statusUpdated) {
                            FileManager.overwriteAppointments(appointmentManager.getAppointments());
                            FileManager.logAudit("Updated appointment status to " + newStatus + " by " + currentUser.getUsername());
                            System.out.println(GREEN + "Appointment status updated." + RESET);
                        } else {
                            System.out.println(RED + "Appointment not found." + RESET);
                        }
                    } catch (Exception e) {
                        System.out.println(RED + "Error updating appointment status." + RESET);
                        input.nextLine();
                    }

                    pause(input);
                    break;
                }

                case 14: {
                    if (!requirePermission(currentUser, "processPayment")) {
                        pause(input);
                        break;
                    }

                    try {
                        System.out.print("Enter Billing Amount: ");
                        double amount = input.nextDouble();

                        System.out.print("Choose Payment Method (1-Cash, 2-Card, 3-Insurance): ");
                        int method = input.nextInt();

                        Payment payment = null;

                        switch (method) {
                            case 1:
                                payment = new CashPayment();
                                break;
                            case 2:
                                payment = new CardPayment();
                                break;
                            case 3:
                                payment = new InsuranceBilling();
                                break;
                            default:
                                System.out.println(RED + "Invalid payment option." + RESET);
                                input.nextLine();
                                pause(input);
                                break;
                        }

                        if (payment != null) {
                            payment.processPayment(amount);
                            FileManager.logAudit("Processed payment by " + currentUser.getUsername());
                            System.out.println(GREEN + "Payment processed successfully." + RESET);
                        }

                    } catch (Exception e) {
                        System.out.println(RED + "Error processing payment." + RESET);
                        input.nextLine();
                    }

                    input.nextLine();
                    pause(input);
                    break;
                }

                case 15: {
                    if (!requirePermission(currentUser, "recordEHR")) {
                        pause(input);
                        break;
                    }

                    try {
                        System.out.print("Enter Patient ID: ");
                        String pid = input.next();

                        System.out.print("Enter Visit Date (yyyy-MM-dd): ");
                        String date = input.next();

                        input.nextLine();
                        System.out.print("Enter Purpose for Processing: ");
                        String purpose = input.nextLine();

                        System.out.print("Enter Temperature: ");
                        double temperature = input.nextDouble();

                        System.out.print("Enter Heart Rate: ");
                        int hr = input.nextInt();

                        input.nextLine();
                        System.out.print("Enter Blood Pressure (example 120/80): ");
                        String bp = input.nextLine();

                        System.out.print("Enter Oxygen Level: ");
                        int oxygen = input.nextInt();

                        input.nextLine();
                        System.out.print("Enter Diagnosis: ");
                        String diagnosis = input.nextLine();

                        System.out.print("Enter Prescription: ");
                        String prescription = input.nextLine();

                        System.out.print("Enter Allergies: ");
                        String allergies = input.nextLine();

                        System.out.print("Enter Lab Results: ");
                        String labResults = input.nextLine();

                        System.out.print("Enter Immunization Date: ");
                        String immunizationDate = input.nextLine();

                        vitals.Vitals vitals = new vitals.Vitals(temperature, hr, bp, oxygen);

                        VisitRecord visit = new VisitRecord(
                            pid, date, purpose, vitals, diagnosis, prescription,
                            allergies, labResults, immunizationDate
                        );

                        FileManager.saveVisitRecord(visit);

                        System.out.println(GREEN + "\nVisit Record Saved." + RESET);
                        visit.display();

                        FileManager.logAudit("Recorded visit for patient: " + pid + " by " + currentUser.getUsername());

                    } catch (Exception e) {
                        System.out.println(RED + "Error recording patient visit." + RESET);
                        input.nextLine();
                    }

                    pause(input);
                    break;
                }

                case 16: {
                    if (!requirePermission(currentUser, "viewEHR")) {
                        pause(input);
                        break;
                    }

                    FileManager.readAllVisitRecords();
                    FileManager.logAudit("Viewed all EHR records by " + currentUser.getUsername());

                    input.nextLine();
                    pause(input);
                    break;
                }

                case 17: {
                    if (!requirePermission(currentUser, "searchEHR")) {
                        pause(input);
                        break;
                    }

                    System.out.print("Enter Patient ID: ");
                    String patientIdSearch = input.next();

                    List<VisitRecord> patientVisits = FileManager.getVisitRecordsByPatientId(patientIdSearch);
                    FileManager.displayVisitRecordList(patientVisits);

                    FileManager.logAudit("Searched EHR by patient ID: " + patientIdSearch + " by " + currentUser.getUsername());

                    input.nextLine();
                    pause(input);
                    break;
                }

                case 18: {
                    if (!requirePermission(currentUser, "searchEHR")) {
                        pause(input);
                        break;
                    }

                    input.nextLine();
                    System.out.print("Enter Diagnosis: ");
                    String diagnosisSearch = input.nextLine();

                    List<VisitRecord> diagnosisResults = FileManager.searchVisitRecordsByDiagnosis(diagnosisSearch);
                    FileManager.displayVisitRecordList(diagnosisResults);

                    FileManager.logAudit("Searched EHR by diagnosis: " + diagnosisSearch + " by " + currentUser.getUsername());

                    pause(input);
                    break;
                }

                case 19: {
                    if (!requirePermission(currentUser, "searchEHR")) {
                        pause(input);
                        break;
                    }

                    System.out.print("Enter Date (yyyy-MM-dd): ");
                    String dateSearch = input.next();

                    List<VisitRecord> dateResults = FileManager.searchVisitRecordsByDate(dateSearch);
                    FileManager.displayVisitRecordList(dateResults);

                    FileManager.logAudit("Searched EHR by date: " + dateSearch + " by " + currentUser.getUsername());

                    input.nextLine();
                    pause(input);
                    break;
                }

                case 20: {
                    if (!requirePermission(currentUser, "runTriage")) {
                        pause(input);
                        break;
                    }

                    try {
                        System.out.print("Enter Patient ID: ");
                        String triagePatientId = input.next();

                        System.out.print("Enter Current Heart Rate: ");
                        int heartRate = input.nextInt();

                        ArrayList<Integer> previousRates = new ArrayList<>();

                        for (VisitRecord record : FileManager.getVisitRecordsByPatientId(triagePatientId)) {
                            previousRates.add(record.getVitals().getHeartRate());
                        }

                        String risk = Triage.riskScore(heartRate);
                        double average = previousRates.isEmpty() ? heartRate : Triage.movingAverage(previousRates);
                        boolean deviation = Triage.hasLargeDeviation(heartRate, average);

                        System.out.println(BLUE + "\n--- TRIAGE RESULT ---" + RESET);
                        System.out.println("Patient ID: " + triagePatientId);
                        System.out.println("Current Heart Rate: " + heartRate);
                        System.out.println("Risk Level: " + risk);
                        System.out.println("Average of Previous Readings: " + average);

                        if (deviation) {
                            System.out.println(RED + "Alert: Large deviation detected." + RESET);
                        } else {
                            System.out.println(GREEN + "No major deviation detected." + RESET);
                        }

                        if (risk.equals("HIGH")) {
                            Metrics.highRiskPatients++;
                            notificationService.notifyObservers("HIGH RISK TRIAGE ALERT for patient " + triagePatientId);
                        }

                        FileManager.logAudit("Performed triage for patient: " + triagePatientId + " by " + currentUser.getUsername());

                    } catch (Exception e) {
                        System.out.println(RED + "Invalid triage input." + RESET);
                        input.nextLine();
                    }

                    pause(input);
                    break;
                }

                case 21: {
                    if (!requirePermission(currentUser, "viewReports")) {
                        pause(input);
                        break;
                    }

                    System.out.println(YELLOW + "\n--- WEEKLY METRICS REPORT ---" + RESET);
                    System.out.println("Appointments Booked: " + Metrics.appointmentsBooked);
                    System.out.println("Scheduling Conflicts: " + Metrics.conflicts);
                    System.out.println("High Risk Patients: " + Metrics.highRiskPatients);

                    FileManager.logAudit("Viewed weekly metrics report by " + currentUser.getUsername());

                    input.nextLine();
                    pause(input);
                    break;
                }

                case 22: {
                    System.out.println(GREEN + "Exiting EMHR System. Goodbye!" + RESET);
                    FileManager.logAudit("User logged out: " + currentUser.getUsername());
                    input.close();
                    System.exit(0);
                    break;
                }

                default:

                    System.out.println(RED + "Invalid Choice. Please try again." + RESET);
                    input.nextLine();
                    pause(input);
            }

        } while (choice != 22);

        // Close scanner to free system resources
        input.close();
    }

    
    // Displays the system menu
    public static void displayMenu() {
    	
    	
    	System.out.println(CYAN + "\n===== EMHR SYSTEM MENU =====" + RESET);
    	System.out.println("1. Add Patient");
    	System.out.println("2. View Patients");
    	System.out.println("3. Update Patient");
    	System.out.println("4. Delete Patient");

    	System.out.println("5. Add User");
    	System.out.println("6. View Single User");
    	System.out.println("7. View All Users");
    	System.out.println("8. Update User");
    	System.out.println("9. Delete User");
    	System.out.println("10. View My Profile");

    	System.out.println("11. Schedule Appointment");
    	System.out.println("12. View Appointments");
    	System.out.println("13. Update Appointment Status");
    	System.out.println("14. Process Payment");

    	System.out.println("15. Record Patient Visit (EHR)");
    	System.out.println("16. View All EHR Records");
    	System.out.println("17. Search EHR by Patient ID");
    	System.out.println("18. Search EHR by Diagnosis");
    	System.out.println("19. Search EHR by Date");
    	System.out.println("20. Triage Risk Score + Analytics");
    	System.out.println("21. Weekly Metrics Report");
    	System.out.println("22. Exit");

    }

    // Clears the console screen
    public static void clearScreen() {

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Simple loading animation
    public static void loading() throws InterruptedException {

        System.out.print("Loading");

        for (int i = 0; i < 3; i++) {

            Thread.sleep(500);
            System.out.print(".");

        }

        System.out.println();
    }

    // Pauses program until user presses Enter
    public static void pause(Scanner input) {
        System.out.println("\nPress Enter to continue...");
        input.nextLine();
    }
    
    
    public static boolean requirePermission(User currentUser, String permission) {
        if (currentUser == null || !currentUser.hasPermission(permission)) {
            System.out.println(RED + "Access Denied. Missing permission: " + permission + RESET);
            return false;
        }
        return true;
    }
