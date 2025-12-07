package Lab8;

public class PythonBackend implements BackendService {
    @Override
    public void processRequest() {
        System.out.println("Processing request with Python Django");
    }

    @Override
    public String getData() {
        return "Data from Python backend";
    }
}