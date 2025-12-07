package Lab9;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Book> parseBooksFromJson(String filePath) {
        List<Book> books = new ArrayList<>();

        try {
            JsonNode root = mapper.readTree(new File(filePath));
            JsonNode booksNode = root.path("books");

            if (booksNode.isArray()) {
                for (JsonNode bookNode : booksNode) {
                    String title = bookNode.path("title").asText();
                    String author = bookNode.path("author").asText();
                    int year = bookNode.path("year").asInt();
                    String genre = bookNode.path("genre").asText();

                    books.add(new Book(title, author, year, genre));
                }
            }
            System.out.println("Прочитано " + books.size() + " книг из JSON");
        } catch (IOException e) {
            System.err.println("Ошибка чтения JSON: " + e.getMessage());
        }
        return books;
    }
}