package userManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fileManager.FileManager;
import role.Role;
import user.User;

public class UserManager {

    private ArrayList<User> users;

    public UserManager() {
        users = new ArrayList<>();
        loadUsersFromFile();
    }

    public void loadUsersFromFile() {
        users.clear();
        List<String> lines = FileManager.loadRawUsers();

        for (String line : lines) {
            User user = User.fromFileString(line);
            if (user != null) {
                users.add(user);
            }
        }

        if (users.isEmpty()) {
            User defaultAdmin = new User("System Administrator", "admin", "Admin123");
            defaultAdmin.addRole(new Role("ADMIN"));
            users.add(defaultAdmin);
            FileManager.saveUser(defaultAdmin.toFileString());
            System.out.println("Default admin account created.");
        }
    }

    public boolean usernameExists(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            }
            if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }

        return hasLetter && hasDigit;
    }

    public boolean addUser(User user) {
        if (user == null) {
            return false;
        }

        if (usernameExists(user.getUsername())) {
            System.out.println("Duplicate username not allowed.");
            return false;
        }

        if (!isValidPassword(user.getPassword())) {
            System.out.println("Password must be at least 8 characters and contain letters and numbers.");
            return false;
        }

        users.add(user);
        FileManager.saveUser(user.toFileString());
        return true;
    }

    public User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)
                    && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean deleteUser(String username) {
        User userToDelete = findUserByUsername(username);

        if (userToDelete == null) {
            return false;
        }

        if (userToDelete.hasPermission("updateSystemConfig") && countAdmins() == 1) {
            System.out.println("Cannot delete the last admin account.");
            return false;
        }

        boolean removed = users.removeIf(user -> user.getUsername().equalsIgnoreCase(username));

        if (removed) {
            overwriteUsersFile();
        }

        return removed;
    }

    public boolean updateUser(String username, String newFullName, String newPassword) {
        for (int i = 0; i < users.size(); i++) {
            User existingUser = users.get(i);

            if (existingUser.getUsername().equalsIgnoreCase(username)) {
                if (!isValidPassword(newPassword)) {
                    System.out.println("Password must be at least 8 characters and contain letters and numbers.");
                    return false;
                }

                User updatedUser = new User(newFullName, existingUser.getUsername(), newPassword);

                for (Role role : existingUser.getRoles()) {
                    updatedUser.addRole(role);
                }

                users.set(i, updatedUser);
                overwriteUsersFile();
                return true;
            }
        }
        return false;
    }

    public void viewAllUsers() {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        for (User user : users) {
            user.display();
            System.out.println("----------------------");
        }
    }

    public void viewSingleUser(String username) {
        User user = findUserByUsername(username);

        if (user == null) {
            System.out.println("User not found.");
        } else {
            user.display();
        }
    }

    public void viewOwnProfile(User currentUser) {
        if (currentUser == null) {
            System.out.println("No user is logged in.");
            return;
        }
        currentUser.display();
    }

    private int countAdmins() {
        int count = 0;
        for (User user : users) {
            if (user.hasPermission("updateSystemConfig")) {
                count++;
            }
        }
        return count;
    }

    private void overwriteUsersFile() {
        try (FileWriter fw = new FileWriter("users.txt", false)) {
            for (User user : users) {
                fw.write(user.toFileString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }
}
