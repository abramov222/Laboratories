package Lab8;

public class JavaBackend implements BackendService {
    @Override
    public void processRequest() {
        System.out.println("Processing request with Java Spring");
    }

    @Override
    public String getData() {
        return "Data from Java backend";
    }
}