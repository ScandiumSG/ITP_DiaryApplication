package diary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Entry {
    private String username;
    private String entryDate;
    private String entryContent;

    /**
     * Constructor that utilize a provided name and content of the entry.
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
     * Constructor that utilize a provided name and content of the entry, as well as
     * a userprovided time string
     * 
     * @param user    A string of the name of whoever made the entry.
     * @param content A string containing the diary entry.
     * @param date    A date string. Has to be in the format "dd-mm-yyyy HH:MM"
     */
    public Entry(String user, String content, String date) {
        this.username = user;
        this.entryContent = content;

        if (!validateDateInput(date)) {
            throw new IllegalArgumentException("Invalid format of input date for new entry.");
        } else {
            this.entryDate = date;
        }
    }

    /**
     * Makes and returns a string of the current systems time and date.
     * 
     * @return A string of the date and time on the format "dd-mm-yyyy HH:MM".
     */
    private String parseCurrentTime() {
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd-mm-yyyy HH:MM");
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.format(dft);
    }

    /**
     * Check if the input date contain 3 "-" separated substrings, then a " ", then
     * followed by 2 ":" separated substrings. Return true if string on format
     * "dd-mm-yyyy HH:MM", else return false.
     * 
     * @param input A string of the user provided date.
     * @return Boolean
     */
    private Boolean validateDateInput(String input) {
        String[] splitString = input.split(" ");

        String[] date = splitString[0].split("-", 3);
        String[] time = splitString[1].split(":", 2);

        if (time[1].contains(":")) {
            return false;
        } else if (date[2].contains("-")) {
            return false;
        } else {
            return true;
        }
    }

    public String getUsername() {
        return this.username;
    }

    public String getContent() {
        return this.entryContent;
    }

    /**
     * Returns a string of the time and date when the entry was created.
     * 
     * @return A string of the date and time on the format "dd-mm-yyyy HH:MM".
     */
    public String getDate() {
        return entryDate;
    }
}