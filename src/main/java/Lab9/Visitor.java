package Lab9;

public class Visitor {
    private int id;
    private String name;
    private String email;

    public Visitor(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return id + ". " + name + " (" + email + ")";
    }
}