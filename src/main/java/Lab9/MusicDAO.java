package Lab9;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MusicDAO {
    private final Connection connection;

    public MusicDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Music> getAllMusic() {
        List<Music> musicList = new ArrayList<>();
        String sql = "SELECT * FROM music ORDER BY year";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Music music = new Music(
                        rs.getString("title"),
                        rs.getString("artist"),
                        rs.getInt("year")
                );
                music.setId(rs.getInt("id"));
                musicList.add(music);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка получения музыки: " + e.getMessage());
        }
        return musicList;
    }

    public List<Music> getMusicWithoutMT() {
        List<Music> musicList = new ArrayList<>();
        String sql = """
            SELECT * FROM music 
            WHERE LOWER(title) NOT LIKE '%m%' 
            AND LOWER(title) NOT LIKE '%t%'
            ORDER BY title
            """;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Music music = new Music(
                        rs.getString("title"),
                        rs.getString("artist"),
                        rs.getInt("year")
                );
                music.setId(rs.getInt("id"));
                musicList.add(music);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка фильтрации: " + e.getMessage());
        }
        return musicList;
    }

    public void addMusic(Music music) {
        String sql = "INSERT INTO music (title, artist, year) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, music.getTitle());
            pstmt.setString(2, music.getArtist());
            pstmt.setInt(3, music.getYear());
            pstmt.executeUpdate();
            System.out.println("Добавлена композиция: " + music.getTitle());
        } catch (SQLException e) {
            System.err.println("Ошибка добавления: " + e.getMessage());
        }
    }

    public void seedMusicData() {
        addMusic(new Music("Bohemian Rhapsody", "Queen", 1975));
        addMusic(new Music("Imagine", "John Lennon", 1971));
        addMusic(new Music("Smells Like Teen Spirit", "Nirvana", 1991));
        addMusic(new Music("Shape of You", "Ed Sheeran", 2017));
        addMusic(new Music("Bad Guy", "Billie Eilish", 2019));
    }

    public void printAllMusic() {
        System.out.println("\n=== Все музыкальные композиции ===");
        getAllMusic().forEach(System.out::println);
    }

    public void printMusicWithoutMT() {
        System.out.println("\n=== Композиции без букв 'm' и 't' ===");
        getMusicWithoutMT().forEach(System.out::println);
    }
}