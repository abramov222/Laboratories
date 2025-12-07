package Lab7;

public class UserRepository {
    public void save(User user) {
        System.out.println("Saving user " + user.getUsername() + " to database");
    }

    public User findById(String username) {
        System.out.println("Finding user " + username + " in database");
        return new User(username, username + "@example.com");
    }
}