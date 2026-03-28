package user;

import java.util.ArrayList;
import java.util.List;

import role.Role;


public class User {
	

    private String fullName;
    private String username;
    private String password;
    private List<Role> roles;
    
    

    public User(String fullName, String username, String password) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.roles = new ArrayList<>();
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public boolean hasPermission(String permission) {
        for (Role role : roles) {
            if (role.hasPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    public String rolesToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < roles.size(); i++) {
            sb.append(roles.get(i).getRoleName());
            if (i < roles.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public String toFileString() {
        return username + "|" + fullName + "|" + password + "|" + rolesToString();
    }

    public static User fromFileString(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length != 4) {
            return null;
        }

        User user = new User(parts[1], parts[0], parts[2]);

        if (!parts[3].isEmpty()) {
            String[] roleParts = parts[3].split(",");
            for (String roleName : roleParts) {
                user.addRole(new Role(roleName.trim()));
            }
        }

        return user;
    }

    public void display() {
        System.out.println("Full Name: " + fullName);
        System.out.println("Username: " + username);
        System.out.println("Roles: " + rolesToString());
    }

}
