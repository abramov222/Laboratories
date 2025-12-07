package Lab7;

public class ExcelReport implements Report {
    @Override
    public void generate() {
        System.out.println("Generating Excel report...");
    }

    @Override
    public String getType() {
        return "Excel";
    }
}