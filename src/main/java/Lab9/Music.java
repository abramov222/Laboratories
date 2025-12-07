package Lab9;

public class Music {
    private int id;
    private String title;
    private String artist;
    private int year;

    public Music(String title, String artist, int year) {
        this.title = title;
        this.artist = artist;
        this.year = year;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    @Override
    public String toString() {
        return id + ". " + title + " - " + artist + " (" + year + ")";
    }
}