package role;

import java.util.ArrayList;

public class Role {

    // Stores the name of the role
    private String roleName;

    // Stores the permissions attached to this role
    private ArrayList<String> permissions;

    // Constructor creates role and empty permission list
    public Role(String roleName) {
        this.roleName = roleName;
        permissions = new ArrayList<>();
    }

    // Adds a permission to the role
    public void addPermission(String permission) {
        permissions.add(permission);
    }

    // Checks whether a role has a specific permission
    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }

    // Returns role name
    public String getRoleName() {
        return roleName;
    }
}