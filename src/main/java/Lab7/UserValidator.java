package Lab7;

public class UserValidator {
    public boolean validate(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            System.out.println("Invalid username");
            return false;
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            System.out.println("Invalid email");
            return false;
        }
        System.out.println("User " + user.getUsername() + " validated");
        return true;
    }
}