package Lab8;

public class UIUXDesigner implements Workable, Designer {
    private String name;

    public UIUXDesigner(String name) {
        this.name = name;
    }

    @Override
    public void work() {
        System.out.println(name + " (UI/UX Designer) is working");
    }

    @Override
    public void designUI() {
        System.out.println(name + " is designing beautiful UI");
    }

    @Override
    public void createMockups() {
        System.out.println(name + " is creating detailed mockups");
    }

    // НЕТ методов code() и reviewCode() - не нужны дизайнеру
}