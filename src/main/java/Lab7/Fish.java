package Lab7;

public class Fish implements Animal {
    private String name;

    public Fish(String name) {
        this.name = name;
    }

    @Override
    public void move() {
        System.out.println(name + " is swimming in water");
    }

    @Override
    public String getName() {
        return name;
    }
}