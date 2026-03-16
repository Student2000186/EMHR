package smsNotification;

import notification.Notification;

public class SMSNotification implements Notification {

    public void send(String message){
        System.out.println("SMS sent: " + message);
    }

}