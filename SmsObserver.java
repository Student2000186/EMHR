package smsObserver;

import notificationObserver.NotificationObserver;

public class SmsObserver implements NotificationObserver {
    @Override
    public void update(String message) {
        System.out.println("SMS SENT: " + message);
    }
}