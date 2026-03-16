package cashPayment;

import payment.Payment;

public class CashPayment extends Payment {

    // Processes a cash payment
    @Override
    public void processPayment(double amount) {
        System.out.println("Cash Payment Processed: $" + amount);
    }
}  