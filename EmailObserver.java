package emailObserver;

import notificationObserver.NotificationObserver;

public class EmailObserver implements NotificationObserver {
    @Override
    public void update(String message) {
        System.out.println("EMAIL SENT: " + message);
    }
}