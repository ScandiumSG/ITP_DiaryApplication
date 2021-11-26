package diary.core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Entry {
    private String entryDate;
    private String entryContent;

    /**
     * Constructor that utilize a provided name and content of the entry.
     * @param content A string containing the diary entry.
     */
    public Entry(final String content) {
        this.entryContent = content;
        this.entryDate = parseCurrentTime();
    }

    /**
     * Constructor that utilize a provided name and content of the entry,
     * as well as a userprovided time string.
     * @param content A string containing the diary entry.
     * @param date    A date string. Has to be in the format "dd-mm-yyyy"
     */
    public Entry(final String content, final String date) {
        this.entryContent = content;

        if (!validateDateInput(date)) {
            throw new IllegalArgumentException(
                "Invalid format of input date for new entry: " + date);
        } else {
            this.entryDate = date;
        }
    }

    /**
     * Makes and returns a string of the current systems time and date.
     * @return A string of the date and time on the format "dd-mm-yyyy".
     */
    public static String parseCurrentTime() {
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.format(dft);
    }


    /**
     * Returns true if inputstring is on format "dd-mm-yyyy",
     * else returns false.
     * @param input A string of the user provided date.
     * @return Boolean
     */
    private Boolean validateDateInput(final String input) {
        if (input.length() != 10) {
            return false;
        }

        String pattern = "dd-mm-yyyy";
        DateFormat dateFormatter = new SimpleDateFormat(pattern);
        dateFormatter.setLenient(false);

        try {
            dateFormatter.parse(input);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    /**
     * Getter for the entry content
     * @return the entry content
     */
    public final String getContent() {
        return (this.entryContent);
    }

    /**
     * Getter for the entry date
     * @return the entry date
     */
    public final String getDate() {
        return (this.entryDate);
    }
}
