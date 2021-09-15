package diary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Entry {
    private String username;
    private String entryDate;
    private String entryContent;

    /**
     * 
     * @param user    A string of the name of whoever made the entry.
     * @param content A string containing the diary entry.
     */
    public Entry(String user, String content) {
        this.username = user;
        this.entryContent = content;
        this.entryDate = parseCurrentTime();
    }

    /**
     * s
     * 
     * @param user    A string of the name of whoever made the entry.
     * @param content A string containing the diary entry.
     * @param date    A date string
     */
    public Entry(String user, String content, String date) {
        this.username = user;
        this.entryContent = content;

    }

    private String parseCurrentTime() {
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd-mm-yyyy HH:MM");
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.format(dft);
    }

    public String getUsername() {
        return this.username;
    }

    public String getContent() {
        return this.entryContent;
    }

    public String getDate() {
        return entryDate;
    }
}