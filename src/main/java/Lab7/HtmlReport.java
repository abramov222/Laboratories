package Lab7;

public class HtmlReport implements Report {
    @Override
    public void generate() {
        System.out.println("Generating HTML report...");
    }

    @Override
    public String getType() {
        return "HTML";
    }
}