package Lab7;

public class EmailService {
    public void sendWelcomeEmail(User user) {
        System.out.println("Sending welcome email to: " + user.getEmail());
    }

    public void sendNotification(User user, String message) {
        System.out.println("Sending notification to " + user.getEmail() + ": " + message);
    }
}