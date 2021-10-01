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
     * @param user    A string of the name of whoever made the entry.
     * @param content A string containing the diary entry.
     */
    public Entry(final String user, final String content) {
        this.username = user;
        this.entryContent = content;
        this.entryDate = parseCurrentTime();
    }

    /**
     * Constructor that utilize a provided name and content of the entry,
     * as well as a userprovided time string.
     * @param user    A string of the name of whoever made the entry.
     * @param content A string containing the diary entry.
     * @param date    A date string. Has to be in the format "dd-mm-yyyy"
     */
    public Entry(final String user, final String content, final String date) {
        this.username = user;
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
        final int splitAmount = 3;
        final int dayAndMonthLength = 2;
        final int maxDayMonthLength = 4;

        String[] date = input.split("-", splitAmount);

        for (int i = 0; i < dayAndMonthLength; i++) {
            if (isNumerical(date[i]) && date[i].length() != dayAndMonthLength) {
                return false;
            }
        }

        if (isNumerical(date[2]) && date[2].length() != maxDayMonthLength) {
            return false;
        }

        return true;
    }

    /**
     * Verify is input string is numerical
     * @param input The string to check
     * @return Boolean true if inputstring only contains numbers,
     * else returns false.
     */
    private Boolean isNumerical(final String input) {
        return input.matches("[0-9]+");
    }

    public final String getUsername() {
        return new String(this.username);
    }

    public final String getContent() {
        return new String(this.entryContent);
    }

    public final String getDate() {
        return new String(this.entryDate);
    }
}
