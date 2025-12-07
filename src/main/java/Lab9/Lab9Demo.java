package Lab9;
import java.util.List;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;

public class Lab9Demo {
    public static void main(String[] args) {
        System.out.println("=== Лабораторная работа №9: JDBC + H2 ===\n");

        // Создаем books.json если его нет
        createSampleJsonFile();

        DatabaseManager dbManager = new DatabaseManager();
        dbManager.createTables();

        MusicDAO musicDAO = new MusicDAO(dbManager.getConnection());
        BookDAO bookDAO = new BookDAO(dbManager.getConnection());
        VisitorDAO visitorDAO = new VisitorDAO(dbManager.getConnection());
        JsonParser jsonParser = new JsonParser();

        // 1-3: Музыка
        System.out.println("\n--- Задание 1-3: Работа с музыкой ---");
        musicDAO.seedMusicData();
        musicDAO.printAllMusic();
        musicDAO.printMusicWithoutMT();
        musicDAO.addMusic(new Music("Моя любимая песня", "Мой артист", 2023));
        musicDAO.printAllMusic();

        // 4: Книги из JSON
        System.out.println("\n--- Задание 4: Книги из JSON ---");
        List<Book> books = jsonParser.parseBooksFromJson("books.json");
        for (Book book : books) {
            bookDAO.addBook(book);
        }

        // 5: Книги по году
        System.out.println("\n--- Задание 5: Книги по году издания ---");
        bookDAO.printAllBooks();

        // 6: Книги до 2000
        System.out.println("\n--- Задание 6: Книги до 2000 года ---");
        bookDAO.printBooksBefore2000();

        // 7: Добавить себя
        System.out.println("\n--- Задание 7: Добавить себя и книги ---");
        visitorDAO.addVisitor(new Visitor("Илья Абрамов", "abramov@example.com"));
        bookDAO.addBook(new Book("Чистый код", "Роберт Мартин", 2008, "Программирование"));
        bookDAO.addBook(new Book("Java. Эффективное программирование", "Джошуа Блох", 2018, "Программирование"));
        visitorDAO.printAllVisitors();
        bookDAO.printAllBooks();

        // 8: Удалить таблицы
        System.out.println("\n--- Задание 8: Удаление таблиц ---");
        dbManager.dropTables();
        dbManager.close();

        System.out.println("\n=== Лабораторная работа завершена ===");
    }

    private static void createSampleJsonFile() {
        try {
            File jsonFile = new File("books.json");
            if (!jsonFile.exists()) {
                String jsonContent = """
                    {
                      "books": [
                        {"title": "Война и мир", "author": "Лев Толстой", "year": 1869, "genre": "Роман"},
                        {"title": "Преступление и наказание", "author": "Фёдор Достоевский", "year": 1866, "genre": "Роман"},
                        {"title": "Мастер и Маргарита", "author": "Михаил Булгаков", "year": 1967, "genre": "Фэнтези"},
                        {"title": "1984", "author": "Джордж Оруэлл", "year": 1949, "genre": "Антиутопия"},
                        {"title": "Гарри Поттер", "author": "Джоан Роулинг", "year": 1997, "genre": "Фэнтези"}
                      ]
                    }
                    """;
                Files.writeString(jsonFile.toPath(), jsonContent);
                System.out.println("Создан books.json");
            }
        } catch (IOException e) {
            System.err.println("Ошибка создания файла: " + e.getMessage());
        }
    }
}