package cardPayment;

import payment.Payment;

public class CardPayment extends Payment {

    // Processes a card payment
    @Override
    public void processPayment(double amount) {
        System.out.println("Card Payment Processed: $" + amount);
    }
}
