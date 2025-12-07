package Lab9;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private final Connection connection;

    public BookDAO(Connection connection) {
        this.connection = connection;
    }

    public void addBook(Book book) {
        String checkSql = "SELECT COUNT(*) FROM books WHERE title = ? AND author = ?";
        String insertSql = "INSERT INTO books (title, author, year, genre) VALUES (?, ?, ?, ?)";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setString(1, book.getTitle());
            checkStmt.setString(2, book.getAuthor());

            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count == 0) {
                try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                    insertStmt.setString(1, book.getTitle());
                    insertStmt.setString(2, book.getAuthor());
                    insertStmt.setInt(3, book.getYear());
                    insertStmt.setString(4, book.getGenre());
                    insertStmt.executeUpdate();
                    System.out.println("Добавлена книга: " + book.getTitle());
                }
            } else {
                System.out.println("Книга уже существует: " + book.getTitle());
            }
        } catch (SQLException e) {
            System.err.println("Ошибка добавления книги: " + e.getMessage());
        }
    }

    public List<Book> getBooksSortedByYear() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY year";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year"),
                        rs.getString("genre")
                );
                book.setId(rs.getInt("id"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка получения книг: " + e.getMessage());
        }
        return books;
    }

    public List<Book> getBooksBefore2000() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE year < 2000 ORDER BY year";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year"),
                        rs.getString("genre")
                );
                book.setId(rs.getInt("id"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка фильтрации книг: " + e.getMessage());
        }
        return books;
    }

    public void printAllBooks() {
        System.out.println("\n=== Все книги ===");
        List<Book> books = getBooksSortedByYear();
        if (books.isEmpty()) {
            System.out.println("Книг нет в базе");
        } else {
            books.forEach(System.out::println);
        }
    }

    public void printBooksBefore2000() {
        System.out.println("\n=== Книги до 2000 года ===");
        List<Book> books = getBooksBefore2000();
        if (books.isEmpty()) {
            System.out.println("Книг до 2000 года нет");
        } else {
            books.forEach(System.out::println);
        }
    }
}