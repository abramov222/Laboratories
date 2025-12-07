package Lab8;

public class FullStackDeveloper implements Workable, Coder, Designer {
    private String name;

    public FullStackDeveloper(String name) {
        this.name = name;
    }

    @Override
    public void work() {
        System.out.println(name + " (FullStack) is working");
    }

    @Override
    public void code() {
        System.out.println(name + " is coding backend and frontend");
    }

    @Override
    public void reviewCode() {
        System.out.println(name + " is reviewing fullstack code");
    }

    @Override
    public void designUI() {
        System.out.println(name + " is designing user interface");
    }

    @Override
    public void createMockups() {
        System.out.println(name + " is creating UI mockups");
    }
}