package design.patterns;

// Base interface for notifications
interface Notification {
    void send(String message);
}

// Concrete implementation of Notification - sends email notifications
class EmailNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending Email: " + message);
    }
}

// Base decorator class for notifications
class NotificationDecorator implements Notification {
    protected Notification wrappedNotification;

    public NotificationDecorator(Notification notification) {
        this.wrappedNotification = notification;
    }

    @Override
    public void send(String message) {
        wrappedNotification.send(message);
    }
}

// Concrete decorator - adds SMS notification functionality
class SMSNotificationDecorator extends NotificationDecorator {

    public SMSNotificationDecorator(Notification notification) {
        super(notification);
    }

    @Override
    public void send(String message) {
        super.send(message);
        sendSMS(message);
    }

    private void sendSMS(String message) {
        System.out.println("Sending SMS: " + message);
    }
}

// Another possible decorator for push notifications (optional)
class PushNotificationDecorator extends NotificationDecorator {

    public PushNotificationDecorator(Notification notification) {
        super(notification);
    }

    @Override
    public void send(String message) {
        super.send(message);
        sendPushNotification(message);
    }

    private void sendPushNotification(String message) {
        System.out.println("Sending Push Notification: " + message);
    }
}



public class DecoratorPattern {
    public static void main(String[] args) {

    }
}
