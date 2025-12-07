package Lab7;

public class Logger {
    public void log(String message) {
        System.out.println("LOG: " + message);
    }

    public void logActivity(User user, String activity) {
        System.out.println("ACTIVITY: User " + user.getUsername() + " performed: " + activity);
    }
}