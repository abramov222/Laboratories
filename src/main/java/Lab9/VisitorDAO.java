package Lab9;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VisitorDAO {
    private final Connection connection;

    public VisitorDAO(Connection connection) {
        this.connection = connection;
    }

    public void addVisitor(Visitor visitor) {
        String checkSql = "SELECT COUNT(*) FROM visitors WHERE email = ?";
        String insertSql = "INSERT INTO visitors (name, email) VALUES (?, ?)";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setString(1, visitor.getEmail());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count == 0) {
                try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                    insertStmt.setString(1, visitor.getName());
                    insertStmt.setString(2, visitor.getEmail());
                    insertStmt.executeUpdate();
                    System.out.println("Добавлен посетитель: " + visitor.getName());
                }
            } else {
                System.out.println("Посетитель уже существует: " + visitor.getEmail());
            }
        } catch (SQLException e) {
            System.err.println("Ошибка добавления посетителя: " + e.getMessage());
        }
    }

    public List<Visitor> getAllVisitors() {
        List<Visitor> visitors = new ArrayList<>();
        String sql = "SELECT * FROM visitors ORDER BY name";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Visitor visitor = new Visitor(
                        rs.getString("name"),
                        rs.getString("email")
                );
                visitor.setId(rs.getInt("id"));
                visitors.add(visitor);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка получения посетителей: " + e.getMessage());
        }
        return visitors;
    }

    public void printAllVisitors() {
        System.out.println("\n=== Все посетители ===");
        List<Visitor> visitors = getAllVisitors();
        if (visitors.isEmpty()) {
            System.out.println("Посетителей нет в базе");
        } else {
            visitors.forEach(System.out::println);
        }
    }
}