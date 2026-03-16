//EMHR Group PROJECT//

package main;

import java.util.ArrayList;
// Scanner allows the program to read input from the user
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
        AppointmentManager appointmentManager = new AppointmentManager();
        // Notification classes sends alerts to user and patients
        NotificationService notificationService = new NotificationService();
        notificationService.addObserver(new EmailObserver());
        notificationService.addObserver(new SmsObserver());
        
        
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

            System.out.print("Enter Choice: ");
            choice = input.nextInt();

            switch (choice) {

                // =========================================================
                // PATIENT MANAGEMENT
                // =========================================================

                case 1: // ADD PATIENT

                    try {

                        System.out.print("Enter Patient Name: ");
                        String name = input.next();

                        System.out.print("Enter Patient ID: ");
                        String id = input.next();

                        System.out.print("Enter Contact Number: ");
                        String contact = input.next();

                        System.out.print("Enter Next of Kin: ");
                        String kin = input.next();

                        // Create patient object
                        Patient patient = new Patient(name, id, contact, kin);

                        // Save patient details to file
                        FileManager.savePatient(name + " " + id + " " + contact + " " + kin);

                        System.out.println(GREEN + "Patient Added Successfully." + RESET);

                    } catch (Exception e) {

                        System.out.println(RED + "Invalid patient input." + RESET);
                    }

                    pause();
                    break;

                case 2: // VIEW PATIENTS

                    System.out.println(BLUE + "\n--- Patient Records ---" + RESET);

                    // Display all stored patients
                    FileManager.readPatients();

                    pause();
                    break;

                case 3: // UPDATE PATIENT

                    try {

                        System.out.print("Enter Patient ID to update: ");
                        String updateId = input.next();

                        System.out.print("Enter New Contact Number: ");
                        String newContact = input.next();

                        System.out.print("Enter New Next of Kin: ");
                        String newKin = input.next();

                        // Update the patient record in the file
                        FileManager.updatePatient(updateId, newContact, newKin);

                        // Record the action in an audit log if your FileManager supports it
                        FileManager.logAudit("Updated patient: " + updateId);

                    } catch (Exception e) {

                        System.out.println(RED + "Error updating patient." + RESET);
                    }

                    pause();
                    break;

                case 4: // DELETE PATIENT

                    try {

                        System.out.print("Enter Patient ID to delete: ");
                        String deleteId = input.next();

                        // Delete patient from file
                        FileManager.deletePatient(deleteId);

                        // Record the action in audit log
                        FileManager.logAudit("Deleted patient: " + deleteId);

                    } catch (Exception e) {

                        System.out.println(RED + "Error deleting patient." + RESET);
                    }

                    pause();
                    break;

                // =========================================================
                // USER / ROLE MANAGEMENT
                // =========================================================

                case 5: // ADD USER

                    try {

                        System.out.print("Enter Username: ");
                        String username = input.next();

                        System.out.print("Enter Password: ");
                        String password = input.next();

                        System.out.print("Enter Role (Admin/Manager/User): ");
                        String roleName = input.next();

                        // Create role and assign permissions
                        Role role = new Role(roleName);

                        if (roleName.equalsIgnoreCase("Admin")) {
                            role.addPermission("createUser");
                            role.addPermission("deleteUser");
                            role.addPermission("updateSystemConfig");
                        }

                        if (roleName.equalsIgnoreCase("Manager")) {
                            role.addPermission("approveTransaction");
                            role.addPermission("viewReports");
                        }

                        if (roleName.equalsIgnoreCase("User")) {
                            role.addPermission("viewOwnData");
                        }

                        // Create and store the user
                        User user = new User(username, password, role);
                        userManager.addUser(user);

                        // Persist to file if your FileManager supports it
                        FileManager.saveUser(username + " " + roleName);

                        // Record audit log
                        FileManager.logAudit("Added user: " + username);

                    } catch (Exception e) {

                        System.out.println(RED + "Error adding user." + RESET);
                    }

                    pause();
                    break;

                case 6: // VIEW USERS

                    System.out.println(BLUE + "\n--- User Records ---" + RESET);

                    userManager.viewUsers();

                    pause();
                    break;

                case 7: // UPDATE USER

                    try {

                        System.out.print("Enter Username to update: ");
                        String updateUsername = input.next();

                        System.out.print("Enter New Password: ");
                        String newPassword = input.next();

                        userManager.updateUser(updateUsername, newPassword);

                        FileManager.logAudit("Updated user: " + updateUsername);

                    } catch (Exception e) {

                        System.out.println(RED + "Error updating user." + RESET);
                    }

                    pause();
                    break;

                case 8: // DELETE USER

                    try {

                        System.out.print("Enter Username to delete: ");
                        String deleteUsername = input.next();

                        userManager.deleteUser(deleteUsername);

                        FileManager.logAudit("Deleted user: " + deleteUsername);

                    } catch (Exception e) {

                        System.out.println(RED + "Error deleting user." + RESET);
                    }

                    pause();
                    break;

                // =========================================================
                // APPOINTMENT MANAGEMENT
                // =========================================================

                case 9: // SCHEDULE APPOINTMENT

                    try {

                        System.out.print("Enter Patient ID: ");
                        String patientID = input.next();

                        System.out.print("Enter Staff ID: ");
                        String staffID = input.next();

                        // Clear leftover newline from previous scanner input
                        input.nextLine();

                        System.out.print("Enter Appointment Date and Time (yyyy-MM-dd HH:mm): ");
                        String inputTime = input.nextLine();

                        // Convert text into LocalDateTime
                        LocalDateTime time = LocalDateTime.parse(inputTime, format);

                        // Create appointment with default starting status
                        Appointment appt = new Appointment(patientID, staffID, time, Status.SCHEDULED);

                        // Add appointment to in-memory appointment list
                        appointmentManager.addAppointment(appt);

                        // Save appointment to file if your FileManager supports it
                        FileManager.saveAppointment(patientID + " " + staffID + " " + time + " " + Status.SCHEDULED);

                        // Increase appointment metric
                        Metrics.appointmentsBooked++;

                        // Notify staff or system observers
                        notificationService.notifyObservers("Appointment booked for patient: " + patientID);

                        // Record audit
                        FileManager.logAudit("Booked appointment for patient: " + patientID);

                    } catch (Exception e) {

                        System.out.println(RED + "Error scheduling appointment. Check date/time format." + RESET);

                        // Track scheduling conflicts or failed scheduling attempts
                        Metrics.conflicts++;
                    }

                    pause();
                    break;

                case 10: // VIEW APPOINTMENTS

                    System.out.println(BLUE + "\n--- Appointment Records ---" + RESET);

                    appointmentManager.viewAppointments();

                    pause();
                    break;

                case 11: // UPDATE APPOINTMENT STATUS

                    try {

                        // Clear scanner buffer before reading full line input
                        input.nextLine();

                        System.out.print("Enter Appointment Date and Time (yyyy-MM-dd HH:mm): ");
                        String timeInput = input.nextLine();

                        LocalDateTime appointmentTime = LocalDateTime.parse(timeInput, format);

                        System.out.print("Enter Status (COMPLETED/CANCELLED/NO_SHOW): ");
                        String statusInput = input.next();

                        // Convert entered text to enum value
                        Status newStatus = Status.valueOf(statusInput.toUpperCase());

                        // Update appointment status
                        appointmentManager.updateStatus(appointmentTime, newStatus);

                        FileManager.logAudit("Updated appointment status to " + newStatus + " for time: " + appointmentTime);

                    } catch (Exception e) {

                        System.out.println(RED + "Invalid appointment time or status entered." + RESET);
                    }

                    pause();
                    break;

                case 12: // VIEW WEEKLY METRICS REPORT

                    System.out.println(PURPLE + "\n--- Weekly Metrics Report ---" + RESET);

                    Metrics.weeklyReport();

                    pause();
                    break;

                // =========================================================
                // EHR / TRIAGE / BILLING
                // =========================================================

                case 13: // RECORD PATIENT VISIT (EHR)

                    try {

                        System.out.print("Enter Patient ID: ");
                        String pid = input.next();

                        System.out.print("Enter Visit Date: ");
                        String date = input.next();

                        System.out.print("Enter Heart Rate: ");
                        int hr = input.nextInt();

                        System.out.print("Enter Blood Pressure: ");
                        int bp = input.nextInt();

                        System.out.print("Enter Diagnosis: ");
                        String diagnosis = input.next();

                        // Create visit record
                        VisitRecord visit = new VisitRecord(pid, date, hr, bp, diagnosis);

                        // Show visit data on screen
                        System.out.println(GREEN + "\nVisit Record Saved." + RESET);
                        visit.display();

                        // Optional audit
                        FileManager.logAudit("Recorded visit for patient: " + pid);

                    } catch (Exception e) {

                        System.out.println(RED + "Error recording patient visit." + RESET);
                    }

                    pause();
                    break;

                case 14: // TRIAGE RISK SCORE + ANALYTICS

                    try {

                        System.out.print("Enter Current Heart Rate: ");
                        int heartRate = input.nextInt();

                        // Example previous readings used for moving average
                        // Later, this can be replaced with real visit history from file or memory
                        ArrayList<Integer> previousRates = new ArrayList<>();
                        previousRates.add(80);
                        previousRates.add(85);
                        previousRates.add(90);

                        // Determine risk category
                        String risk = Triage.riskScore(heartRate);

                        // Compute average of previous vitals
                        double average = Triage.movingAverage(previousRates);

                        // Check for large deviation from expected readings
                        boolean deviation = Triage.hasLargeDeviation(heartRate, average);

                        System.out.println(BLUE + "\n--- TRIAGE RESULT ---" + RESET);
                        System.out.println("Current Heart Rate: " + heartRate);
                        System.out.println("Risk Level: " + risk);
                        System.out.println("Average of Previous Readings: " + average);

                        if (deviation) {
                            System.out.println(RED + "Alert: Large deviation detected." + RESET);
                        } else {
                            System.out.println(GREEN + "No major deviation detected." + RESET);
                        }

                        // Increment high-risk counter if needed
                        if (risk.equals("HIGH")) {
                            Metrics.highRiskPatients++;

                            // Notify observers when a high-risk case occurs
                            notificationService.notifyObservers("HIGH RISK TRIAGE ALERT. Heart rate: " + heartRate);
                        }

                        FileManager.logAudit("Performed triage for heart rate: " + heartRate + ", risk: " + risk);

                    } catch (Exception e) {

                        System.out.println(RED + "Invalid triage input." + RESET);

                        // Clear bad scanner input if needed
                        input.nextLine();
                    }

                    pause();
                    break;

                case 15: // PROCESS PAYMENT

                    try {

                        System.out.println("Select Payment Type");
                        System.out.println("1. Cash");
                        System.out.println("2. Card");
                        System.out.println("3. Insurance");

                        int type = input.nextInt();

                        System.out.print("Enter Amount: ");
                        double amount = input.nextDouble();

                        // Use polymorphism to select the correct payment strategy
                        Payment payment;

                        if (type == 1) {
                            payment = new CashPayment();
                        } else if (type == 2) {
                            payment = new CardPayment();
                        } else {
                            payment = new InsuranceBilling();
                        }

                        // Process selected payment type
                        payment.processPayment(amount);

                        FileManager.logAudit("Processed payment of amount: " + amount);

                    } catch (Exception e) {

                        System.out.println(RED + "Error processing payment." + RESET);
                    }

                    pause();
                    break;

                case 16: // EXIT

                    System.out.println(GREEN + "System Closing..." + RESET);
                    break;

                default:

                    System.out.println(RED + "Invalid Choice. Please try again." + RESET);
                    pause();
            }

        } while (choice != 16);

        // Close scanner to free system resources
        input.close();
    }

    // Displays the system menu
    public static void displayMenu() {
    	 System.out.println(CYAN + "==================================================" + RESET);
         System.out.println(GREEN + "        EASTERN MEDICAL HEALTH REGION SYSTEM      " + RESET);
         System.out.println(CYAN + "==================================================" + RESET);

         System.out.println(YELLOW + "PATIENT MANAGEMENT" + RESET);
         System.out.println("1.  Add Patient");
         System.out.println("2.  View Patients");
         System.out.println("3.  Update Patient");
         System.out.println("4.  Delete Patient");

         System.out.println(YELLOW + "\nUSER / ROLE MANAGEMENT" + RESET);
         System.out.println("5.  Add User");
         System.out.println("6.  View Users");
         System.out.println("7.  Update User");
         System.out.println("8.  Delete User");

         System.out.println(YELLOW + "\nAPPOINTMENT MANAGEMENT" + RESET);
         System.out.println("9.  Schedule Appointment");
         System.out.println("10. View Appointments");
         System.out.println("11. Update Appointment Status");
         System.out.println("12. View Weekly Metrics Report");

         System.out.println(YELLOW + "\nCLINICAL / BILLING" + RESET);
         System.out.println("13. Record Patient Visit (EHR)");
         System.out.println("14. Triage Risk Score & Analytics");
         System.out.println("15. Process Payment");

         System.out.println(YELLOW + "\nSYSTEM" + RESET);
         System.out.println("16. Exit");

         System.out.println(CYAN + "--------------------------------------------------" + RESET);
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
    public static void pause() {

        System.out.println("\nPress Enter to continue...");

        try {

            System.in.read();

        } catch (Exception e) {
        }
    }
}