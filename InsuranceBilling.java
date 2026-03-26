package insuranceBilling;

import payment.Payment;

public class InsuranceBilling extends Payment {

    // Insurance values used in billing calculation
    private final double coverage = 0.80;
    private final double deductible = 500.0;
    private final double perVisitCap = 3000.0;

    // Processes insurance billing
    @Override
    public void processPayment(double amount) {

        // Calculate what remains after 80% insurance coverage
        double patientPortion = amount * (1 - coverage);

        // Ensure patient pays at least the deductible
        double finalAmount = Math.max(patientPortion, deductible);

        // Apply visit cap if necessary
        if (finalAmount > perVisitCap) {
            finalAmount = perVisitCap;
        }

        System.out.println("Insurance Payment Processed");
        System.out.println("Patient Pays: $" + finalAmount);
    }
}
