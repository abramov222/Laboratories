package Lab8;

public class BackendOnlyDeveloper implements Workable, Coder {
    private String name;

    public BackendOnlyDeveloper(String name) {
        this.name = name;
    }

    @Override
    public void work() {
        System.out.println(name + " (Backend only) is working");
    }

    @Override
    public void code() {
        System.out.println(name + " is writing backend code");
    }

    @Override
    public void reviewCode() {
        System.out.println(name + " is reviewing backend code");
    }

    // НЕТ методов designUI() и createMockups() - не нужны бэкенд разработчику
}