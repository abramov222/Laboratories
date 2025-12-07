package Lab9;
import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:h2:mem:lab9db";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Подключение к БД установлено");
        } catch (SQLException e) {
            System.err.println("Ошибка подключения: " + e.getMessage());
        }
    }

    public Connection getConnection() { return connection; }

    public void createTables() {
        String musicTable = """
            CREATE TABLE IF NOT EXISTS music (
                id INT AUTO_INCREMENT PRIMARY KEY,
                title VARCHAR(255) NOT NULL,
                artist VARCHAR(255),
                year INT
            )
            """;
        String booksTable = """
            CREATE TABLE IF NOT EXISTS books (
                id INT AUTO_INCREMENT PRIMARY KEY,
                title VARCHAR(255) NOT NULL UNIQUE,
                author VARCHAR(255) NOT NULL,
                year INT,
                genre VARCHAR(100)
            )
            """;
        String visitorsTable = """
            CREATE TABLE IF NOT EXISTS visitors (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                email VARCHAR(255) UNIQUE
            )
            """;

        executeUpdate(musicTable);
        executeUpdate(booksTable);
        executeUpdate(visitorsTable);
        System.out.println("Таблицы созданы");
    }

    public void dropTables() {
        executeUpdate("DROP TABLE IF EXISTS visitors");
        executeUpdate("DROP TABLE IF EXISTS books");
        executeUpdate("DROP TABLE IF EXISTS music");
        System.out.println("Таблицы удалены");
    }

    private void executeUpdate(String sql) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Ошибка SQL: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Подключение закрыто");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка закрытия: " + e.getMessage());
        }
    }
}