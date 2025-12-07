package Lab7;

public class Bird implements Animal {
    private String name;

    public Bird(String name) {
        this.name = name;
    }

    @Override
    public void move() {
        System.out.println(name + " is flying");
    }

    @Override
    public String getName() {
        return name;
    }

    public void fly() {
        System.out.println(name + " is flying high");
    }
}