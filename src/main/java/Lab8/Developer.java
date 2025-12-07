package Lab8;

public class Developer implements Workable, Coder {
    private String name;
    private BackendService backendService; // DIP: зависимость от интерфейса

    public Developer(String name, BackendService backendService) {
        this.name = name;
        this.backendService = backendService;
    }

    @Override
    public void work() {
        System.out.println(name + " is working");
    }

    @Override
    public void code() {
        System.out.println(name + " is writing code");
        backendService.processRequest(); // Используем интерфейс
    }

    @Override
    public void reviewCode() {
        System.out.println(name + " is reviewing code");
    }

    public void useBackend() {
        System.out.println(name + " got: " + backendService.getData());
    }
}