package Lab7;

public class PdfReport implements Report {
    @Override
    public void generate() {
        System.out.println("Generating PDF report...");
    }

    @Override
    public String getType() {
        return "PDF";
    }
}