package role;

import java.util.HashSet;
import java.util.Set;

public class Role {

    private String roleName;
    private Set<String> permissions;

    public Role(String roleName) {
        this.roleName = roleName;
        this.permissions = new HashSet<>();
        assignDefaultPermissions();
    }

    public Role(String roleName, Set<String> permissions) {
        this.roleName = roleName;
        this.permissions = permissions;
    }

    public String getRoleName() {
        return roleName;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }

    public void addPermission(String permission) {
        permissions.add(permission);
    }

    private void assignDefaultPermissions() {
        switch (roleName.toUpperCase()) {
            case "ADMIN":
                permissions.add("createUser");
                permissions.add("updateUser");
                permissions.add("deleteUser");
                permissions.add("viewUser");
                permissions.add("viewAllUsers");
                permissions.add("viewOwnProfile");
                permissions.add("updateSystemConfig");

                permissions.add("addPatient");
                permissions.add("viewPatients");
                permissions.add("updatePatient");
                permissions.add("deletePatient");

                permissions.add("scheduleAppointment");
                permissions.add("viewAppointments");
                permissions.add("updateAppointmentStatus");

                permissions.add("processPayment");
                permissions.add("approveTransaction");

                permissions.add("recordEHR");
                permissions.add("viewEHR");
                permissions.add("searchEHR");
                permissions.add("runTriage");

                permissions.add("viewReports");
                break;

            case "MANAGER":
                permissions.add("viewUser");
                permissions.add("viewAllUsers");
                permissions.add("viewOwnProfile");

                permissions.add("viewPatients");

                permissions.add("scheduleAppointment");
                permissions.add("viewAppointments");
                permissions.add("updateAppointmentStatus");

                permissions.add("processPayment");
                permissions.add("approveTransaction");

                permissions.add("recordEHR");
                permissions.add("viewEHR");
                permissions.add("searchEHR");
                permissions.add("runTriage");

                permissions.add("viewReports");
                break;

            case "USER":
                permissions.add("viewOwnProfile");
                permissions.add("viewOwnData");
                permissions.add("scheduleAppointment");
                permissions.add("viewAppointments");
                break;

            default:
                break;
        }
    }

    @Override
    public String toString() {
        return "Role: " + roleName + ", Permissions: " + permissions;
    }
}
