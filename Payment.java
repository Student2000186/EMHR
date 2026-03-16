package payment;

// Abstract class means this class cannot be used directly
// It is used as a base for other payment types
public abstract class Payment {

    // Every payment type must define its own processing logic
    public abstract void processPayment(double amount);
}