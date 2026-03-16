package fileManager;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {

    // Saves patient data to the patients.txt file
    // The 'true' means new data is added without deleting old data
    public static void savePatient(String data) {

        try {
            FileWriter fw = new FileWriter("patients.txt", true);
            fw.write(data + "\n");
            fw.close();
        }
        catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }

    // Reads and displays all patients stored in patients.txt
    public static void readPatients() {

        try {
            BufferedReader br = new BufferedReader(new FileReader("patients.txt"));
            String line;

            // Read each line until end of file
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            br.close();
        }
        catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }

    // Updates a patient's contact and next of kin using the patient ID
    public static void updatePatient(String id, String newContact, String newKin) {

        try {

            // Read all existing lines from file
            List<String> lines = Files.readAllLines(Paths.get("patients.txt"));

            // New list to hold updated records
            List<String> updated = new ArrayList<>();

            for (String line : lines) {

                // If the line belongs to the patient being updated
                if (line.startsWith(id + " ")) {
                    updated.add(id + " " + newContact + " " + newKin);
                }
                else {
                    updated.add(line);
                }
            }

            // Write the updated data back to the file
            Files.write(Paths.get("patients.txt"), updated);

            System.out.println("Patient Updated Successfully!");
        }
        catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }

    
 // Saves a user record from the file using userID
    public static void saveUser(String data) {
        try {
            FileWriter fw = new FileWriter("users.txt", true);
            fw.write(data + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }

    // Deletes a patient record from the file using patient ID
    public static void deletePatient(String id) {

        try {

            // Read all lines from file
            List<String> lines = Files.readAllLines(Paths.get("patients.txt"));

            // Remove any line that starts with the patient ID
            lines.removeIf(line -> line.startsWith(id + " "));

            // Save remaining lines back to file
            Files.write(Paths.get("patients.txt"), lines);

            System.out.println("Patient Deleted!");
        }
        catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }
    
    
    
    // Saves the Appointments created to a file
    public static void saveAppointment(String data) {
        try {
            FileWriter fw = new FileWriter("appointments.txt", true);
            fw.write(data + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }
    
    public static void logAudit(String action) {
        try {
            FileWriter fw = new FileWriter("audit.txt", true);
            fw.write(action + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }
}