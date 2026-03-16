package userManager;

import user.User;
import java.util.ArrayList;

public class UserManager {

    // Stores all users currently in the system
    private ArrayList<User> users = new ArrayList<>();

    // Adds a new user to the system
    public void addUser(User user) {

        // Prevent duplicate usernames
        if (findUser(user.getUsername()) != null) {
            System.out.println("Error: Username already exists.");
            return;
        }

        users.add(user);
        System.out.println("User Added Successfully");
    }

    // Displays all users in the system
    public void viewUsers() {

        // If list is empty, show message
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        for (User u : users) {
            u.display();
            System.out.println("-------------------");
        }
    }

    // Deletes a user using username
    public void deleteUser(String username) {

        User user = findUser(username);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        users.remove(user);
        System.out.println("User Deleted Successfully");
    }

    // Searches for a user by username
    public User findUser(String username) {

        for (User u : users) {

            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }

        // Return null if user not found
        return null;
    }
    
    public void updateUser(String username, String newPassword) {
        User user = findUser(username);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        user.setPassword(newPassword);
        System.out.println("User updated successfully.");
    }
    
    
}