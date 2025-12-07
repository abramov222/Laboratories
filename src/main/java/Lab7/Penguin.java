package Lab7;

public class Penguin implements Animal {
    private String name;

    public Penguin(String name) {
        this.name = name;
    }

    @Override
    public void move() {
        System.out.println(name + " is swimming");
    }

    @Override
    public String getName() {
        return name;
    }

    public void swim() {
        System.out.println(name + " is swimming fast");
    }
}