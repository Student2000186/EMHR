//EMHR Group PROJECT//
// Rohan Edmond 2000186
// Ryan Cole	2106111
// Malachi-Shavario Shouta	2404920
// Trev-ann Cameron	2405379
//Gabriel Francis	2205720

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.util.Date;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class Main {

    private static JFrame frame = new JFrame("EMHR System");
    private static GridBagConstraints gbc = new GridBagConstraints();

    private static UserManager userManager;
    private static User user;

     private static NotificationService notificationService = new NotificationService();

    public static void main(String[] args){
        notificationService.addObserver(new EmailObserver());
        notificationService.addObserver(new SmsObserver());

        frame.setLayout(new GridBagLayout());
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
         // Show app from
        login();

        frame.setVisible(true); 
    }

    private static void place(Component component, int x, int y, int width, int height) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Make component fill horizontal space
        gbc.weightx = 1.0;  // Give horizontal expansion priority
        frame.add(component,gbc);
    }

    private static void place(Panel panel, Component component, int x, int y, int width, int height) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        panel.add(component,gbc);
    }

    private static void clearPanel() {
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();
    }

    private static void replacePanel(JPanel newPanel) {
        frame.getContentPane().removeAll();
        place(newPanel, 0, 0, 1, 1);
        frame.revalidate();
        frame.repaint();
    }
    private static void repaintPanel() {
        frame.revalidate();
        frame.repaint();
    }
    private static void buttonDoes(JButton button, Runnable action) {
        button.addActionListener(e -> action.run());
    }

    private static void login(){
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");
        userManager = new UserManager();
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            user = userManager.authenticate(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(frame, "Login successful! Welcome, " + user.getUsername() + "!");
                mainMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        //Exit to main menu with escape
        frame.getRootPane().registerKeyboardAction(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to return to main menu?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                mainMenu();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

        // Display login form
        JPanel loginPanel = new JPanel(new GridBagLayout());
        place(new JLabel("Username:"), 0, 0, 1, 1);
        place(usernameField, 1, 0, 1, 1);
        place(new JLabel("Password:"), 0, 1, 1, 1);
        place(passwordField, 1, 1, 1, 1);
        place(loginButton, 0, 2, 2, 1);
       // replacePanel(loginPanel);
        frame.setTitle("EMHR System - Login");

    }

    // Main menu for the system, displays options based on user role
    private static void mainMenu() {
        JButton btnAddPatient = new JButton("Add Patient");
        JButton btnViewPatients = new JButton("View Patients");
        JButton btnUpdatePatient = new JButton("Update Patient");
        JButton btnDeletePatient = new JButton("Delete Patient");

        JButton btnAddUser = new JButton("Add User");
        JButton btnViewSingleUser = new JButton("View Single User");
        JButton btnViewAllUsers = new JButton("View All Users");
        JButton btnUpdateUser = new JButton("Update User");
        JButton btnDeleteUser = new JButton("Delete User");
        JButton btnViewProfile = new JButton("View My Profile");

        JButton btnScheduleAppointment = new JButton("Schedule Appointment");
        JButton btnViewAppointments = new JButton("View Appointments");
        JButton btnUpdateAppointmentStatus = new JButton("Update Appointment Status");
        JButton btnProcessPayment = new JButton("Process Payment");

        JButton btnRecordVisit = new JButton("Record Patient Visit (EHR)");
        JButton btnViewAllEHR = new JButton("View All EHR Records");
        JButton btnSearchEHRByPatientId = new JButton("Search EHR by Patient ID");
        JButton btnSearchEHRByDiagnosis = new JButton("Search EHR by Diagnosis");
        JButton btnSearchEHRByDate = new JButton("Search EHR by Date");
        JButton btnTriageAnalytics = new JButton("Triage Risk Score + Analytics");
        JButton btnWeeklyReport = new JButton("Weekly Metrics Report");
        JButton btnExit = new JButton("Exit"); 

        clearPanel();

        place(btnAddPatient, 0, 0, 1, 1);
        place(btnViewPatients, 1, 0, 1, 1);
        place(btnUpdatePatient, 0, 1, 1, 1);
        place(btnDeletePatient, 1, 1, 1, 1);
        
        place(btnAddUser, 0, 2, 1, 1);
        place(btnViewSingleUser, 1, 2, 1, 1);
        place(btnViewAllUsers, 0, 3, 1, 1);
        place(btnUpdateUser, 1, 3, 1, 1);
        place(btnDeleteUser, 0, 4, 1, 1);
        place(btnViewProfile, 1, 4, 1, 1);

        place(btnScheduleAppointment, 0, 5, 1, 1);
        place(btnViewAppointments, 1, 5, 1, 1);
        place(btnUpdateAppointmentStatus, 0, 6, 1, 1);
        place(btnProcessPayment, 1, 6, 1, 1);

        place(btnRecordVisit, 0, 7, 1, 1);
        place(btnViewAllEHR, 1, 7, 1, 1);
        place(btnSearchEHRByPatientId, 0, 8, 1, 1);
        place(btnSearchEHRByDiagnosis, 1, 8, 1, 1);
        place(btnSearchEHRByDate, 0, 9, 1, 1);
        place(btnTriageAnalytics, 1, 9, 1, 1);
        place(btnWeeklyReport, 0, 10, 1, 1);
        place(btnExit, 1, 10, 1, 1);

        buttonDoes(btnAddPatient,Main::addPatient);
        buttonDoes(btnViewPatients,Main::viewPatients);
        buttonDoes(btnUpdatePatient,Main::updatePatient);
        buttonDoes(btnDeletePatient,Main::deletePatient);
        buttonDoes(btnAddUser,Main::addUser);
        buttonDoes(btnViewSingleUser,Main::viewSingleUser);
        buttonDoes(btnViewAllUsers,Main::viewAllUsers);
        buttonDoes(btnUpdateUser,Main::updateUser);
        buttonDoes(btnDeleteUser,Main::deleteUser);
        buttonDoes(btnViewProfile,Main::viewMyProfile);
        buttonDoes(btnScheduleAppointment,Main::scheduleAppointment);
        buttonDoes(btnViewAppointments,Main::viewAppointments);
        buttonDoes(btnUpdateAppointmentStatus,Main::updateAppointmentStatus);
        buttonDoes(btnProcessPayment,Main::processPayment);
        buttonDoes(btnRecordVisit,Main::recordEHR);
        buttonDoes(btnViewAllEHR,Main::viewEHR);
        buttonDoes(btnSearchEHRByDate,Main::searchEHRByDateRange);
        buttonDoes(btnSearchEHRByDiagnosis,Main::searchEHRByDiagnosis);
        buttonDoes(btnSearchEHRByPatientId,Main::searchEHRByPatientId);
        buttonDoes(btnTriageAnalytics,Main::runTriage);
        buttonDoes(btnWeeklyReport,Main::weeklyReport);

        btnExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        btnExit.setBackground(new Color(255,102,102));
        btnExit.setForeground(Color.WHITE);
        
        repaintPanel();

    }

    
    public static void addPatient() {
        if(!userAllowed("addPatient")){
            return;
        }
        JLabel nameLabel = new JLabel("Enter Patient Name:");
        JTextField nameField = new JTextField(20); 
        JLabel idLabel = new JLabel("Enter Patient ID:");
        JTextField idField = new JTextField(20);
        JLabel contactLabel = new JLabel("Enter Contact Number:");
        JTextField contactField = new JTextField(20);
        JLabel kinLabel = new JLabel("Enter Next of Kin:");
        JTextField kinField = new JTextField(20);
        JButton submitButton = new JButton("Submit");
        clearPanel();

        place(nameLabel, 0, 0, 1, 1);
        place(nameField, 1, 0, 2, 1);
        
        place(idLabel, 0, 1, 1, 1);
        place(idField, 1, 1, 2, 1);
        
        place(contactLabel, 0, 2, 1, 1);
        place(contactField, 1, 2, 2, 1);
        
        place(kinLabel, 0, 3, 1, 1);
        place(kinField, 1, 3, 2, 1);
        
        place(submitButton, 1, 4, 1, 1);

        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String id = idField.getText();
            String contact = contactField.getText();
            String kin = kinField.getText();

            if (name.isEmpty() || id.isEmpty() || contact.isEmpty() || kin.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try{
                Patient patient = new Patient(name, id, contact, kin);
                FileManager.savePatient(patient);
                JOptionPane.showMessageDialog(frame, "Patient added successfully!");
                mainMenu();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(frame, "Error saving patient: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        repaintPanel();
    }
    private static void viewPatients() {
        if(!userAllowed("viewPatients")){
            return;
        }
        List<Patient> patients = FileManager.loadPatients();
        if (patients.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No patients found.", "View Patients", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
       JTable table = new JTable(patients.size(), 4);
       JScrollPane scrollPane = new JScrollPane(table);
       JTableHeader header = table.getTableHeader();
       header.setFont(new Font("Arial", Font.BOLD, 14));
       DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(new Object[]{"Name", "ID", "Contact", "Next of Kin"});
       clearPanel();
        for (Patient patient : patients) {
            table.setValueAt(patient.getName(), patients.indexOf(patient), 0);
            table.setValueAt(patient.getId(), patients.indexOf(patient), 1);
            table.setValueAt(patient.getContact(), patients.indexOf(patient), 2);
            table.setValueAt(patient.getNextOfKin(), patients.indexOf(patient), 3);
        }
        table.setPreferredScrollableViewportSize(new Dimension(600,400));
        table.setFillsViewportHeight(true);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setMinimumSize(new Dimension(600,300));

        clearPanel();
        place(scrollPane,0,0,1,4);
        JButton backButton = new JButton("Back to Main Menu");
        place(backButton, 0, 5, 1, 1);
        backButton.addActionListener(e -> mainMenu());
        repaintPanel();

    }
    private static void updatePatient(){
        if(!userAllowed("updatePatient")){
            return;
        }
        JLabel idLabel = new JLabel("Enter Patient ID:");
        JTextField idField = new JTextField(20);
        JLabel contactLabel = new JLabel("Enter Contact Number:");
        JTextField contactField = new JTextField(20);
        JLabel kinLabel = new JLabel("Enter Next of Kin:");
        JTextField kinField = new JTextField(20);
        JButton submitButton = new JButton("Submit");
        clearPanel();

        
        place(idLabel, 0, 1, 1, 1);
        place(idField, 1, 1, 2, 1);
        
        place(contactLabel, 0, 2, 1, 1);
        place(contactField, 1, 2, 2, 1);
        
        place(kinLabel, 0, 3, 1, 1);
        place(kinField, 1, 3, 2, 1);
        
        place(submitButton, 1, 4, 1, 1);

        submitButton.addActionListener(e -> {
            String id = idField.getText();
            String contact = contactField.getText();
            String kin = kinField.getText();

            if (id.isEmpty() || contact.isEmpty() || kin.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try{
                boolean patientUpdated = FileManager.updatePatient(id, contact, kin);
                if(patientUpdated){
                    JOptionPane.showMessageDialog(frame, "Patient updated successfully!");
                }else{
                    JOptionPane.showMessageDialog(frame, "Patient ID not found.", "Update Failed", JOptionPane.ERROR_MESSAGE);
                }

                mainMenu();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(frame, "Error saving patient: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        repaintPanel();
    }
    private static void deletePatient(){
        if(!userAllowed("deletePatient")){
            return;
        }
        JLabel idLabel = new JLabel("Enter Patient ID:");
        List<Patient> patients = FileManager.loadPatients();
        JComboBox<String> idComboBox = new JComboBox<>();
        idComboBox.setEditable(true);
        idComboBox.addItem(""); // Default
        for (Patient patient : patients) {
            idComboBox.addItem(patient.getId());
        }
        JButton submitButton = new JButton("Submit");
        clearPanel();

        
        place(idLabel, 0, 1, 1, 1);
        place(idComboBox, 1, 1, 2, 1);
        
        place(submitButton, 1, 4, 1, 1);

        submitButton.addActionListener(e -> {
            String id = (String) idComboBox.getSelectedItem();

            if(id == ""){
                JOptionPane.showMessageDialog(frame, "Please select a patient ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try{
                boolean patientDeleted = FileManager.deletePatient(id);
                if(patientDeleted){
                    JOptionPane.showMessageDialog(frame, "Patient deleted successfully!");
                }else{
                    JOptionPane.showMessageDialog(frame, "Patient ID not found.", "Deletion Failed", JOptionPane.ERROR_MESSAGE);
                }

                mainMenu();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(frame, "Error saving patient: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        repaintPanel();
    }
    public static void addUser() {
        if(!userAllowed("createUser")){
            return;
        }
        JLabel nameLabel = new JLabel("Enter Full Name:");
        JTextField nameField = new JTextField(20); 
        JLabel usernameLabel = new JLabel("Enter username:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Enter Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JLabel roleLabel = new JLabel("Enter Role:");
        JComboBox<String> roleComboBox = new JComboBox<>();
        roleComboBox.setPreferredSize(new Dimension(new JTextField(20).getPreferredSize().width, roleComboBox.getPreferredSize().height));
        roleComboBox.addItem("Admin");
        roleComboBox.addItem("Manager");
        roleComboBox.addItem("User");

        JButton submitButton = new JButton("Submit");
        clearPanel();

        place(nameLabel, 0, 0, 1, 1);
        place(nameField, 1, 0, 2, 1);
        
        place(usernameLabel, 0, 1, 1, 1);
        place(usernameField, 1, 1, 2, 1);
        
        place(passwordLabel, 0, 2, 1, 1);
        place(passwordField, 1, 2, 2, 1);
        
        place(roleLabel, 0, 3, 1, 1);
        place(roleComboBox, 1, 3, 2, 1);
        
        place(submitButton, 1, 4, 1, 1);

        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String username = usernameField.getText();
            String password = passwordField.getPassword().length > 0 ? new String(passwordField.getPassword()) : "";
            String role = (String) roleComboBox.getSelectedItem();

            if (name.isEmpty() || username.isEmpty() || password.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try{
                User newUser = new User(name, username, password);
                newUser.addRole(new Role(role));
                boolean userAdded = userManager.addUser(newUser);
                if(userAdded){
                    JOptionPane.showMessageDialog(frame, "User added successfully!");
                }else{
                    JOptionPane.showMessageDialog(frame, "Error while trying to create users", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainMenu();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(frame, "Error adding user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        repaintPanel();
    }
    public static void viewSingleUser(){
        if(!userAllowed("viewUser")){
            return;
        }
        JComboBox<String> usernameComboBox = new JComboBox<>();
        userManager.getUsers().forEach(user -> usernameComboBox.addItem(user.getUsername()));
        JButton submitButton = new JButton("Submit");
        clearPanel();
        place(new JLabel("Select User:"), 0, 0, 1, 1);
        place(usernameComboBox, 1, 0, 2, 1);
        place(submitButton, 1, 1, 1, 1);
        repaintPanel();

        submitButton.addActionListener(e -> {
            String username = (String) usernameComboBox.getSelectedItem();
            if(username == null || username.isEmpty()){
                JOptionPane.showMessageDialog(frame, "Please select a user.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            User user = userManager.findUserByUsername(username);
            if(user == null){
                JOptionPane.showMessageDialog(frame, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JTextArea userInfoArea = new JTextArea(10, 30);
            userInfoArea.setEditable(false);
            userInfoArea.setText("Full Name: " + user.getFullName() + "\nUsername: " + user.getUsername() + "\nRoles: " + user.rolesToString());
            JOptionPane.showMessageDialog(frame, new JScrollPane(userInfoArea), "User Information", JOptionPane.INFORMATION_MESSAGE);   
        });
    }
    public static void viewAllUsers(){
        if(!userAllowed("viewAllUsers")){
            return;
        }
        List<User> users = userManager.getUsers();
        if(users.isEmpty()){
            JOptionPane.showMessageDialog(frame, "No users found.", "View Users", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JTable table = new JTable(users.size(), 3);
        JScrollPane scrollPane = new JScrollPane(table);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(new Object[]{"Full Name", "Username", "Roles"});
        clearPanel();
        for (User user : users) {
            table.setValueAt(user.getFullName(), users.indexOf(user), 0);
            table.setValueAt(user.getUsername(), users.indexOf(user), 1);
            table.setValueAt(user.rolesToString(), users.indexOf(user), 2);
        }
        table.setPreferredScrollableViewportSize(new Dimension(600,400));
        table.setFillsViewportHeight(true);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setMinimumSize(new Dimension(600,300));

        clearPanel();
        place(scrollPane,0,0,1,4);
        JButton backButton = new JButton("Back to Main Menu");
        place(backButton, 0, 5, 1, 1);
        backButton.addActionListener(e -> mainMenu());
        repaintPanel();
    }
    public static void updateUser() {
        if(!userAllowed("updateUser")){
            return;
        }
        // Similar to addUser but with a dropdown to select existing user and pre-filled fields
        JLabel nameLabel = new JLabel("Enter Full Name:");
        JTextField nameField = new JTextField(20); 
        JLabel usernameLabel = new JLabel("Enter username:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Enter New Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JLabel roleLabel = new JLabel("Enter New Role:");
        JComboBox<String> roleComboBox = new JComboBox<>();
        roleComboBox.setPreferredSize(new Dimension(new JTextField(20).getPreferredSize().width, roleComboBox.getPreferredSize().height));
        roleComboBox.addItem("Admin");
        roleComboBox.addItem("Manager");
        roleComboBox.addItem("User");
        roleComboBox.setEditable(false);
        roleComboBox.setEnabled(false);

        JButton submitButton = new JButton("Submit");
        clearPanel();

        place(usernameLabel, 0, 0, 1, 1);
        place(usernameField, 1, 0, 2, 1);

        place(nameLabel, 0, 1, 1, 1);
        place(nameField, 1, 1, 2, 1);
        
        
        place(passwordLabel, 0, 2, 1, 1);
        place(passwordField, 1, 2, 2, 1);
        
       // place(roleLabel, 0, 3, 1, 1);
       // place(roleComboBox, 1, 3, 2, 1);
        
        place(submitButton, 1, 4, 1, 1);

        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String username = usernameField.getText();
            String password = passwordField.getPassword().length > 0 ? new String(passwordField.getPassword()) : "";
            String role = (String) roleComboBox.getSelectedItem();

            if (name.isEmpty() || username.isEmpty() || password.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try{
                boolean userAdded = userManager.updateUser(name,username,password);
                if(userAdded){
                    JOptionPane.showMessageDialog(frame, "User updated successfully!");
                }else{
                    JOptionPane.showMessageDialog(frame, "Error while trying to update user.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mainMenu();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(frame, "Error saving user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        repaintPanel();
    }
    public static void deleteUser() {
        if(!userAllowed("deleteUser")){
            return;
        }
        JLabel idLabel = new JLabel("Select User ID to Delete:");
        JComboBox<String> idComboBox = new JComboBox<>();
        idComboBox.setPreferredSize(new Dimension(new JTextField(20).getPreferredSize().width, idComboBox.getPreferredSize().height));
        idComboBox.setEditable(true);
        
        idComboBox.addItem("Select Username");

        for (User user : userManager.getUsers()) {
            idComboBox.addItem(user.getUsername());
        }
        
        JButton deleteButton = new JButton("Delete User");
        clearPanel();

        place(idLabel, 0, 0, 1, 1);
        place(idComboBox, 1, 0, 2, 1);
        place(deleteButton, 1, 2, 1, 1);

        deleteButton.addActionListener(e -> {
            String username = (String) idComboBox.getSelectedItem();
            
            if (username == null || username.equals("Select User ID")) {
                JOptionPane.showMessageDialog(frame, "Please select a username.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(frame, 
                "Are you sure you want to delete user with ID: " + username + "?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    boolean deleted = userManager.deleteUser(username);
                    if (deleted) {
                        JOptionPane.showMessageDialog(frame, "User deleted successfully!");
                        mainMenu();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Error while trying to delete user.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error deleting user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        repaintPanel();
    }
    public static void viewMyProfile(){
        if(!userAllowed("viewOwnProfile")){
            return;
        }
        JTextArea userInfoArea = new JTextArea(10, 30);
        userInfoArea.setEditable(false);
        userInfoArea.setText("Full Name: " + user.getFullName() + "\nUsername: " + user.getUsername() + "\nRoles: " + user.rolesToString());
        JOptionPane.showMessageDialog(frame, new JScrollPane(userInfoArea), "My Profile", JOptionPane.INFORMATION_MESSAGE);   
    }
    public static void scheduleAppointment(){
        if(!userAllowed("scheduleAppointment")){
            return;
        }
        JLabel patientIdLabel = new JLabel("Patient ID:");
        JTextField patientIdField = new JTextField(20);
        JLabel staffIdLabel = new JLabel("Staff ID:");
        JTextField staffIdField = new JTextField(20);
        JLabel dateTimeLabel = new JLabel("Enter Date & Time (yyyy-MM-dd HH:mm):");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        JFormattedTextField dateTimeField = new JFormattedTextField(format);
        dateTimeField.setColumns(20);
        JButton submitButton = new JButton("Submit");
        clearPanel();
        place(patientIdLabel, 0, 0, 1, 1);
        place(patientIdField, 1, 0, 2, 1);
        place(staffIdLabel, 0, 1, 1, 1);
        place(staffIdField, 1, 1, 2, 1);
        place(dateTimeLabel, 0, 2, 1, 1);
        place(dateTimeField, 1, 2, 2, 1);
        place(submitButton, 1, 3, 1, 1);
        repaintPanel();

        // Set placeholder text
        String placeholder = "yyyy-MM-dd HH:mm";
        dateTimeField.setText(placeholder);
        dateTimeField.setForeground(java.awt.Color.GRAY);
        
        // Add focus listener for placeholder behavior
        dateTimeField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (dateTimeField.getText().equals(placeholder)) {
                    dateTimeField.setText("");
                    dateTimeField.setForeground(java.awt.Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (dateTimeField.getText().trim().isEmpty()) {
                    dateTimeField.setText(placeholder);
                    dateTimeField.setForeground(java.awt.Color.GRAY);
                }
            }
        });

        submitButton.addActionListener(e -> {
            String patientId = patientIdField.getText();
            String staffId = staffIdField.getText();
            String dateTimeStr = dateTimeField.getText();

            if (patientId.isEmpty() || staffId.isEmpty() || dateTimeStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                Appointment appt = new Appointment(patientId, staffId, dateTime,Status.SCHEDULED);
                AppointmentManager appointmentManager = new AppointmentManager(FileManager.loadAppointments());
                NotificationService notificationService = new NotificationService();
                boolean appointmentAdded = appointmentManager.addAppointment(appt);

                if (appointmentAdded) {
                            FileManager.saveAppointment(appt);
                            Metrics.appointmentsBooked++;
                            notificationService.notifyObservers("Appointment booked for patient: " + patientId);
                            JOptionPane.showMessageDialog(frame, "Appointment scheduled successfully!");
                }else{
                    Metrics.conflicts++;
                    JOptionPane.showMessageDialog(frame, "Scheduling conflict detected. Please choose a different time.", "Scheduling Conflict", JOptionPane.ERROR_MESSAGE);
                }
                mainMenu();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error scheduling appointment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    public static void viewAppointments() {
        if(!userAllowed("viewAppointments")){
            return;
        }
        List<Appointment> appointments = FileManager.loadAppointments();
        if (appointments.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No appointments found.", "View Appointments", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
       JTable table = new JTable(appointments.size(), 4);
       JScrollPane scrollPane = new JScrollPane(table);
       JTableHeader header = table.getTableHeader();
       header.setFont(new Font("Arial", Font.BOLD, 14));
       DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(new Object[]{"Patient ID", "Staff ID", "Date & Time", "Status"});
       clearPanel();
        for (Appointment appt : appointments) {
            table.setValueAt(appt.getPatientID(), appointments.indexOf(appt), 0);
            table.setValueAt(appt.getStaffID(), appointments.indexOf(appt), 1);
            table.setValueAt(appt.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), appointments.indexOf(appt), 2);
            table.setValueAt(appt.getStatus().toString(), appointments.indexOf(appt), 3);
        }
        table.setPreferredScrollableViewportSize(new Dimension(600,400));
        table.setFillsViewportHeight(true);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setMinimumSize(new Dimension(600,300));

        clearPanel();
        place(scrollPane,0,0,1,4);
        JButton backButton = new JButton("Back to Main Menu");
        place(backButton, 0, 5, 1, 1);
        backButton.addActionListener(e -> mainMenu());
        repaintPanel();

    }
    public static void updateAppointmentStatus(){
        if (!userAllowed("processPayment")) {
            return;
        }
    JTextField patientIdField = new JTextField(20);
        JTextField staffIdField = new JTextField(20);
        JTextField timeField = new JTextField(20);
        JLabel patientIdLabel = new JLabel("Patient ID:");
        JLabel staffIdLabel = new JLabel("Staff ID:");
        JLabel timeLabel = new JLabel("Appointment Time (yyyy-MM-dd HH:mm):");
        JLabel statusLabel = new JLabel("Select New Status:");
        JComboBox<String> statusComboBox = new JComboBox<>();
        statusComboBox.setPreferredSize(new Dimension(new JTextField(20).getPreferredSize().width, statusComboBox.getPreferredSize().height));
        statusComboBox.addItem("SCHEDULED");
        statusComboBox.addItem("COMPLETED");
        statusComboBox.addItem("CANCELLED");
        statusComboBox.addItem("NO_SHOW");
        JButton submitButton = new JButton("Submit");
        clearPanel();
        place(patientIdLabel, 0, 0, 1, 1);
        place(patientIdField, 1, 0, 2, 1);
        place(staffIdLabel, 0, 1, 1, 1);
        place(staffIdField, 1, 1, 2, 1);
        place(timeLabel, 0, 2, 1, 1);
        place(timeField, 1, 2, 2, 1);
        place(statusLabel, 0, 3, 1, 1);
        place(statusComboBox, 1, 3, 2, 1);
        place(submitButton, 1, 4, 1, 1);
        repaintPanel();

        submitButton.addActionListener(e -> {
            if(!userAllowed("updateAppointmentStatus")) {
                return;
            }
            
            try {
                String patientID = patientIdField.getText().trim();
                String staffID = staffIdField.getText().trim();
                String statusTime = timeField.getText().trim();
                LocalDateTime appointmentTime = LocalDateTime.parse(statusTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                String statusInput = (String) statusComboBox.getSelectedItem();
                Appointment.Status newStatus = Appointment.Status.valueOf(statusInput);
                AppointmentManager appointmentManager = new AppointmentManager(FileManager.loadAppointments());
                
                boolean statusUpdated = appointmentManager.updateStatus(appointmentTime, newStatus);
                
                if (statusUpdated) {
                    FileManager.overwriteAppointments(appointmentManager.getAppointments());
                    FileManager.logAudit("Updated appointment status to " + newStatus + " by " + user.getUsername());
                    JOptionPane.showMessageDialog(null, "Appointment status updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Appointment not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error updating appointment status", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void processPayment(){
        if (!userAllowed("processPayment")) {
            return;
        }

        JLabel amountLabel = new JLabel("Billing Amount:");
        JFormattedTextField amountField = new JFormattedTextField(new java.text.DecimalFormat("#0.00"));
        amountField.setValue(Double.valueOf(0.00));
        amountField.setColumns(10);
        JLabel methodLabel = new JLabel("Payment Method:");
        JRadioButton cashButton = new JRadioButton("Cash");
        JRadioButton cardButton = new JRadioButton("Card");
        JRadioButton insuranceButton = new JRadioButton("Insurance");
        JRadioButton insuranceCashButton = new JRadioButton("Insurance + Cash");
        JRadioButton insuranceCardButton = new JRadioButton("Insurance + Card");
        ButtonGroup methodGroup = new ButtonGroup();
        JButton processButton = new JButton("Process Payment");

        methodGroup.add(cashButton);
        methodGroup.add(cardButton);
        methodGroup.add(insuranceButton);
        methodGroup.add(insuranceCashButton);
        methodGroup.add(insuranceCardButton);

        clearPanel();
        place(amountLabel, 0, 0, 1, 1);
        place(amountField, 1, 0, 2, 1);
        place(methodLabel, 0, 1, 1, 1);
        place(cashButton, 0, 2, 1, 1);
        place(cardButton, 0, 3, 1, 1);
        place(insuranceButton, 0, 4, 1, 1);
        place(insuranceCashButton, 0, 5, 1, 1);
        place(insuranceCardButton, 0, 6, 2, 1);
        place(processButton, 0, 7, 2, 1);  
        repaintPanel();

        processButton.addActionListener(e -> {
                    
            try {
                double amount = Double.parseDouble(amountField.getText());
                
                if (cashButton.isSelected()) {
                    new CashPayment().processPayment(amount);
                } else if (cardButton.isSelected()) {
                    new CardPayment().processPayment(amount);
                } else if (insuranceButton.isSelected()) {
                    new InsuranceBilling().processPayment(amount);
                } else if (insuranceCashButton.isSelected()) {
                    InsuranceBilling insurance = new InsuranceBilling();
                    insurance.processPayment(amount);
                    double remainder = amount * 0.20;
                    JOptionPane.showMessageDialog(null, "Remaining balance handled by cash: $" + remainder, "Info", JOptionPane.INFORMATION_MESSAGE);
                    new CashPayment().processPayment(remainder);
                } else if (insuranceCardButton.isSelected()) {
                    InsuranceBilling insurance = new InsuranceBilling();
                    insurance.processPayment(amount);
                    double remainder = amount * 0.20;
                    JOptionPane.showMessageDialog(null, "Remaining balance handled by card: $" + remainder, "Info", JOptionPane.INFORMATION_MESSAGE);
                    new CardPayment().processPayment(remainder);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid payment option.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                FileManager.logAudit("Processed payment by " + user.getUsername());
                JOptionPane.showMessageDialog(null, "Payment processed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error processing payment.", "Error", JOptionPane.ERROR_MESSAGE);
            }
                });
        }
        public static void recordEHR(){
            if(!userAllowed("recordEHR")){
                return;
            }
            clearPanel();
            JLabel pidLabel = new JLabel("Enter Patient ID:");
            JTextField pidField = new JTextField(20);

            JLabel dateLabel = new JLabel("Enter Visit Date (yyyy-MM-dd):");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            JFormattedTextField dateField = new JFormattedTextField(dateFormat);
            dateField.setColumns(20);

            JLabel purposeLabel = new JLabel("Enter Purpose for Processing:");
            JTextField purposeField = new JTextField(20);

            JLabel tempLabel = new JLabel("Enter Temperature:");
            JTextField tempField = new JTextField(10);

            JLabel hrLabel = new JLabel("Enter Heart Rate:");
            JTextField hrField = new JTextField(10);

            JLabel bpLabel = new JLabel("Enter Blood Pressure:");
            MaskFormatter bpFormatter;

            try {
                bpFormatter = new MaskFormatter("###/##");
            } catch (ParseException e) {
                e.printStackTrace();
                bpFormatter = null;
            }
            bpFormatter.setPlaceholderCharacter('_');
            JFormattedTextField bpField = new JFormattedTextField(bpFormatter);
            bpField.setColumns(10);

            JLabel oxygenLabel = new JLabel("Enter Oxygen Level:");
            JTextField oxygenField = new JTextField(10);

            JLabel diagnosisLabel = new JLabel("Enter Diagnosis:");
            JTextField diagnosisField = new JTextField(20);

            JLabel prescriptionLabel = new JLabel("Enter Prescription:");
            JTextField prescriptionField = new JTextField(20);

            JLabel allergiesLabel = new JLabel("Enter Allergies:");
            JTextField allergiesField = new JTextField(20);

            JLabel labResultsLabel = new JLabel("Enter Lab Results:");
            JTextField labResultsField = new JTextField(20);

            JLabel immunizationLabel = new JLabel("Enter Immunization Date:");
            JFormattedTextField immunizationField = new JFormattedTextField(dateFormat);
            immunizationField.setColumns(20);

            JButton submitButton = new JButton("Save Visit Record");

            int row = 0;

            place(pidLabel, 0, row, 2, 1);
            place(pidField, 2, row, 2, 1);
            row++;

            place(dateLabel, 0, row, 2, 1);
            place(dateField, 2, row, 2, 1);
            row++;

            place(purposeLabel, 0, row, 2, 1);
            place(purposeField, 2, row, 2, 1);
            row++;

            place(tempLabel, 0, row, 2, 1);
            place(tempField, 2, row, 2, 1);
            row++;

            place(hrLabel, 0, row, 2, 1);
            place(hrField, 2, row, 2, 1);
            row++;

            place(bpLabel, 0, row, 2, 1);
            place(bpField, 2, row, 2, 1);
            row++;

            place(oxygenLabel, 0, row, 2, 1);
            place(oxygenField, 2, row, 2, 1);
            row++;

            place(diagnosisLabel, 0, row, 2, 1);
            place(diagnosisField, 2, row, 2, 1);
            row++;

            place(prescriptionLabel, 0, row, 2, 1);
            place(prescriptionField, 2, row, 2, 1);
            row++;

            place(allergiesLabel, 0, row, 2, 1);
            place(allergiesField, 2, row, 2, 1);
            row++;

            place(labResultsLabel, 0, row, 2, 1);
            place(labResultsField, 2, row, 2, 1);
            row++;

            place(immunizationLabel, 0, row, 2, 1);
            place(immunizationField, 2, row, 2, 1);
            row++;

            place(submitButton, 0, row, 4, 1);

            repaintPanel();

            submitButton.addActionListener(e -> {
                try {
                    String pid = pidField.getText();
                    String date = dateField.getText();
                    String purpose = purposeField.getText();
                    double temperature = Double.parseDouble(tempField.getText());
                    int hr = Integer.parseInt(hrField.getText());
                    String bp = bpField.getText();
                    int oxygen = Integer.parseInt(oxygenField.getText());
                    String diagnosis = diagnosisField.getText();
                    String prescription = prescriptionField.getText();
                    String allergies = allergiesField.getText();
                    String labResults = labResultsField.getText();
                    String immunizationDate = immunizationField.getText();

                    vitals.Vitals vitals = new vitals.Vitals(temperature, hr, bp, oxygen);
                    VisitRecord visit = new VisitRecord(
                        pid, date, purpose, vitals, diagnosis, prescription,
                        allergies, labResults, immunizationDate
                    );

                    FileManager.saveVisitRecord(visit);
                    JOptionPane.showMessageDialog(null, "Visit Record Saved.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    visit.display();
                    FileManager.logAudit("Recorded visit for patient: " + pid + " by " + user.getUsername());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error recording patient visit: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        public static void viewEHR() {
            if(!userAllowed("viewEHR")){
                return;
            }
            JTextField pidField = new JTextField(20);
            JLabel pidLabel = new JLabel("Enter Patient ID:");
            JButton submitButton = new JButton("View Records");
            clearPanel();
            place(pidLabel, 0, 0, 1, 1);
            place(pidField, 1, 0, 2, 1);
            place(submitButton, 1, 1, 1, 1);
            repaintPanel();

            submitButton.addActionListener(e -> {
                String pid = pidField.getText().trim();
                if (pid.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a patient ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                List<VisitRecord> records = FileManager.getVisitRecordsByPatientId(pid);
                if (records.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No records found for patient ID: " + pid, "No Records", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (VisitRecord record : records) {
                    sb.append("Date: ").append(record.getDate()).append("\n")
                      .append("Purpose: ").append(record.getPurposeForProcessing()).append("\n")
                      .append("Vitals: ").append(record.getVitals().toString()).append("\n")
                      .append("Diagnosis: ").append(record.getDiagnosis()).append("\n")
                      .append("Prescription: ").append(record.getPrescription()).append("\n")
                      .append("Allergies: ").append(record.getAllergies()).append("\n")
                      .append("Lab Results: ").append(record.getLabResults()).append("\n")
                      .append("Immunization Date: ").append(record.getImmunizationDate()).append("\n")
                      .append("-----------------------------\n");
                }
                JTextArea recordArea = new JTextArea(sb.toString());
                recordArea.setEditable(false);
                JOptionPane.showMessageDialog(frame, new JScrollPane(recordArea), "EHR for Patient ID: " + pid, JOptionPane.INFORMATION_MESSAGE);
            });
        }

        public static void searchEHRByPatientId() {
            if(!userAllowed("searchEHR")){
                return;
            }
            JTextField pidField = new JTextField(20);
            JLabel pidLabel = new JLabel("Search by Patient ID:");
            JButton submitButton = new JButton("Search");
            clearPanel();
            place(pidLabel, 0, 0, 1, 1);
            place(pidField, 1, 0, 2, 1);
            place(submitButton, 1, 1, 1, 1);
            repaintPanel();

            submitButton.addActionListener(e -> {
                String pid = pidField.getText().trim();
                if (pid.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a patient ID to search for.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                List<VisitRecord> records = FileManager.getVisitRecordsByPatientId(pid);
                if (records.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No records found for patient ID: " + pid, "No Records", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (VisitRecord record : records) {
                    sb.append("Date: ").append(record.getDate()).append("\n")
                      .append("Purpose: ").append(record.getPurposeForProcessing()).append("\n")
                      .append("Vitals: ").append(record.getVitals().toString()).append("\n")
                      .append("Diagnosis: ").append(record.getDiagnosis()).append("\n")
                      .append("Prescription: ").append(record.getPrescription()).append("\n")
                      .append("Allergies: ").append(record.getAllergies()).append("\n")
                      .append("Lab Results: ").append(record.getLabResults()).append("\n")
                      .append("Immunization Date: ").append(record.getImmunizationDate()).append("\n")
                      .append("-----------------------------\n");
                }
                JTextArea recordArea = new JTextArea(sb.toString());
                recordArea.setEditable(false);
                JOptionPane.showMessageDialog(frame, new JScrollPane(recordArea), "Search Results for Patient ID: " + pid, JOptionPane.INFORMATION_MESSAGE);
            });
        }
        public static void searchEHRByDiagnosis() {
            if(!userAllowed("searchEHR")){
                return;
            }
            JTextField searchField = new JTextField(20);
            JLabel searchLabel = new JLabel("Search by Diagnosis:");
            JButton submitButton = new JButton("Search");
            clearPanel();
            place(searchLabel, 0, 0, 1, 1);
            place(searchField, 1, 0, 2, 1);
            place(submitButton, 1, 1, 1, 1);
            repaintPanel();

            submitButton.addActionListener(e -> {
                String diagnosis = searchField.getText().trim();
                if (diagnosis.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a diagnosis to search for.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                List<VisitRecord> records = FileManager.searchVisitRecordsByDiagnosis(diagnosis);
                if (records.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No records found with diagnosis: " + diagnosis, "No Records", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (VisitRecord record : records) {
                    sb.append("Patient ID: ").append(record.getPatientID()).append("\n")
                      .append("Date: ").append(record.getDate()).append("\n")
                      .append("Purpose: ").append(record.getPurposeForProcessing()).append("\n")
                      .append("Vitals: ").append(record.getVitals().toString()).append("\n")
                      .append("Prescription: ").append(record.getPrescription()).append("\n")
                      .append("Allergies: ").append(record.getAllergies()).append("\n")
                      .append("Lab Results: ").append(record.getLabResults()).append("\n")
                      .append("Immunization Date: ").append(record.getImmunizationDate()).append("\n")
                      .append("-----------------------------\n");
                }
                JTextArea recordArea = new JTextArea(sb.toString());
                recordArea.setEditable(false);
                JOptionPane.showMessageDialog(frame, new JScrollPane(recordArea), "Search Results for Diagnosis: " + diagnosis, JOptionPane.INFORMATION_MESSAGE);
            });
        }
        public static void searchEHRByDateRange() {
            if(!userAllowed("searchEHR")){
                return;
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            JFormattedTextField startDateField = new JFormattedTextField(dateFormat);
            startDateField.setColumns(20);
            JFormattedTextField endDateField = new JFormattedTextField(dateFormat);
            endDateField.setColumns(20);
            JLabel startLabel = new JLabel("Start Date (yyyy-MM-dd):");
            JLabel endLabel = new JLabel("End Date (yyyy-MM-dd):");
            JButton submitButton = new JButton("Search");
            clearPanel();
            place(startLabel, 0, 0, 1, 1);
            place(startDateField, 1, 0, 2, 1);
            place(endLabel, 0, 1, 1, 1);
            place(endDateField, 1, 1, 2, 1);
            place(submitButton, 1, 2, 1, 1);
            repaintPanel();

            submitButton.addActionListener(e -> {
                try {
                    String startStr = startDateField.getText().trim();
                    String endStr = endDateField.getText().trim();
                    if (startStr.isEmpty() || endStr.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please enter both start and end dates.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    Date startDate = dateFormat.parse(startStr);
                    Date endDate = dateFormat.parse(endStr);
                    List<VisitRecord> records = FileManager.searchVisitRecordsByDateRange(startDate.toString(), endDate.toString());
                    if (records.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "No records found in the specified date range.", "No Records", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    StringBuilder sb = new StringBuilder();
                    for (VisitRecord record : records) {
                        sb.append("Patient ID: ").append(record.getPatientID()).append("\n")
                          .append("Date: ").append(record.getDate()).append("\n")
                          .append("Purpose: ").append(record.getPurposeForProcessing()).append("\n")
                          .append("Vitals: ").append(record.getVitals().toString()).append("\n")
                          .append("Diagnosis: ").append(record.getDiagnosis()).append("\n")
                          .append("Prescription: ").append(record.getPrescription()).append("\n")
                          .append("Allergies: ").append(record.getAllergies()).append("\n")
                          .append("Lab Results: ").append(record.getLabResults()).append("\n")
                          .append("Immunization Date: ").append(record.getImmunizationDate()).append("\n")
                          .append("-----------------------------\n");
                    }
                    JTextArea recordArea = new JTextArea(sb.toString());
                    recordArea.setEditable(false);
                    JOptionPane.showMessageDialog(frame, new JScrollPane(recordArea), "Search Results for Date Range: " + startStr + " to " + endStr, JOptionPane.INFORMATION_MESSAGE);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid date format. Please enter dates in yyyy-MM-dd format.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error searching records: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        public static void runTriage() {
            if (!userAllowed("runTriage")) {
                return;
            }

            clearPanel();

            JLabel patientIdLabel = new JLabel("Enter Patient ID:");
            JTextField patientIdField = new JTextField(15);
            JLabel heartRateLabel = new JLabel("Enter Current Heart Rate:");
            JTextField heartRateField = new JTextField(5);
            JLabel lastNLabel = new JLabel("Enter number of previous visits to include:");
            JTextField lastNField = new JTextField(5);
            JButton runButton = new JButton("Run Triage");
            JTable resultsTable = new JTable();
            JScrollPane scrollPane = new JScrollPane(resultsTable);

            place(patientIdLabel, 0, 0, 4, 1);
            place(patientIdField, 4, 0, 6, 1);
            place(heartRateLabel, 0, 1, 4, 1);
            place(heartRateField, 4, 1, 6, 1);
            place(lastNLabel, 0, 2, 4, 1);
            place(lastNField, 4, 2, 6, 1);
            place(runButton, 0, 3, 10, 1);
            place(scrollPane, 0, 4, 10, 2);
            repaintPanel();

            runButton.addActionListener(e -> {
                try {
                    String triagePatientId = patientIdField.getText().trim();
                    if (triagePatientId.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Patient ID cannot be empty.");
                        return;
                    }
                    int heartRate = Integer.parseInt(heartRateField.getText().trim());
                    int lastN = Integer.parseInt(lastNField.getText().trim());

                    ArrayList<Integer> previousRates = new ArrayList<>();
                    for (VisitRecord record : FileManager.getVisitRecordsByPatientId(triagePatientId)) {
                        previousRates.add(record.getVitals().getHeartRate());
                    }

                    String risk = Triage.riskScore(heartRate);
                    double average = previousRates.isEmpty() ? heartRate : Triage.movingAverage(previousRates, lastN);
                    boolean deviation = Triage.hasLargeDeviation(heartRate, average);

                    String[] columnNames = {"Patient ID", "Current Heart Rate", "Risk Level", "Moving Average", "Deviation Alert"};
                    Object[][] data = {
                        {triagePatientId, heartRate, risk, String.format("%.2f", average), deviation ? "Large deviation detected" : "No major deviation"}
                    };
                    resultsTable.setModel(new DefaultTableModel(data, columnNames));

                    if (risk.equals("HIGH")) {
                        Metrics.highRiskPatients++;
                        notificationService.notifyObservers("HIGH RISK TRIAGE ALERT for patient " + triagePatientId);
                    }

                    FileManager.logAudit("Performed triage for patient: " + triagePatientId + " by " + user.getUsername());

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid numeric input. Please enter valid numbers.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error during triage: " + ex.getMessage());
                }
            });
        }
        public static void weeklyReport() {
            if (!userAllowed("viewReports")) {
                    JOptionPane.showMessageDialog(null, "Permission denied", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }

    // Clear the panel
    clearPanel();

    // Create metrics table
    String[] columns = {"Metric", "Value"};
    Object[][] data = {
        {"Appointments Booked", Metrics.appointmentsBooked},
        {"Scheduling Conflicts", Metrics.conflicts},
        {"High Risk Patients", Metrics.highRiskPatients}
    };
    JTable table = new JTable(data, columns);
    JScrollPane scrollPane = new JScrollPane(table);
    place(scrollPane, 0, 0, 2, 1);

    repaintPanel();

    FileManager.logAudit("Viewed weekly metrics report by " + user.getUsername());
}
            

    public static boolean userAllowed(String permision) {
        if(user == null || !user.hasPermission(permision)) {
            JOptionPane.showMessageDialog(frame, "You do not have permission to perform this action.", "Authentication Required", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    // Simple loading animation
    public static void loading() throws InterruptedException {
    }
    
}
