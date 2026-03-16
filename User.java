package user;

import role.Role;

public class User {

    // Stores login name
    private String username;

    // Stores password
    private String password;

    // Stores user role such as Admin or Manager
    private Role role;
    
    
    
    // Constructor used to create a new user
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Returns username
    public String getUsername() {
        return username;
    }

    // Returns password
    public String getPassword() {
        return password;
    }

    // Returns role object
    public Role getRole() {
        return role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Displays user details
    public void display() {
        System.out.println("Username: " + username);
        System.out.println("Role: " + role.getRoleName());
    }
}