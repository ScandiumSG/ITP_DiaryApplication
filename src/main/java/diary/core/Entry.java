package diary.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Entry {
    private String username;
    // Potential rework, use UNIX time to store time as a long.
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
     * @param date    A date string. Has to be in the format "dd-mm-yyyy"
     */
    public Entry(String user, String content, String date) {
        this.username = user;
        this.entryContent = content;

        if (!validateDateInput(date)) {
            throw new IllegalArgumentException("Invalid format of input date for new entry: " + date);
        } else {
            this.entryDate = date;
        }
    }

    /**
     * Makes and returns a string of the current systems time and date.
     * 
     * @return A string of the date and time on the format "dd-mm-yyyy".
     */
    private String parseCurrentTime() {
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd-mm-yyyy");
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.format(dft);
    }

    /**
     * Returns true if inputstring is on format "dd-mm-yyyy", else returns false.
     * 
     * @param input A string of the user provided date.
     * @return Boolean
     */
    private Boolean validateDateInput(String input) {
        String[] date = input.split("-", 3);

        for (int i = 0; i < 2; i++){
            if (isNumerical(date[i]) && date[i].length() != 2) {
                return false;
            }
        }

        if (isNumerical(date[2]) && date[2].length() != 4){
            return false;
        }

        return true;
    }


    /**
     * Returns true if inputstring only contains numbers, else returns false
     * 
     * @param input The string to check
     * @return Boolean
     */
    private Boolean isNumerical(String input){
        return input.matches("[0-9]+");
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

    public static void main(String[] args) {
        Entry entry = new Entry("Ola nordmann", "Innhold", "11-02-1999");
        System.out.println(entry.getUsername() + " - " + entry.getDate() + " - " + entry.getContent());
    }
}