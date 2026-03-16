package metrics;

public class Metrics {

    // Tracks number of booked appointments
    public static int appointmentsBooked = 0;

    // Tracks number of appointment conflicts
    public static int conflicts = 0;

    // Tracks number of high-risk patients
    public static int highRiskPatients = 0;

    // Displays a simple weekly report
    public static void weeklyReport() {
        System.out.println("===== Weekly Metrics Report =====");
        System.out.println("Appointments Booked: " + appointmentsBooked);
        System.out.println("Conflicts: " + conflicts);
        System.out.println("High Risk Patients: " + highRiskPatients);
    }
}