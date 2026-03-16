package notificationService;

import java.util.ArrayList;
import notificationObserver.NotificationObserver;
         //Sends system notification message
public class NotificationService {
    private ArrayList<NotificationObserver> observers = new ArrayList<>();

    public void addObserver(NotificationObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(String message) {
        for (NotificationObserver observer : observers) {
            observer.update(message);
        }
    }
}