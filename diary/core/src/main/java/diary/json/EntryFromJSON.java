package diary.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import diary.core.Entry;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * EntryFromJSON returns a list or specific entry from JSON.
 * @since 1.0
 * @author Stian K. Gaustad, Lars Overskeid
 */
public final class EntryFromJSON {
    // Use GSON to read/write the JSON files
    // https://github.com/google/gson

    private EntryFromJSON() {
        // Not called
    }

    /**
     * Read any json file with provided username from
     * main/resources/DiaryEntries and returns as a unsorted ArrayList of Entry.
     * @param username A string that indicate user identify.
     *  Used to locate corresponding json file.
     * @return List of all found Entry's stored under the provided username.
     * @throws IOException If filepath to resources is nonexistant.
     */
    public static List<Entry> read(final String username) throws IOException {
        File filePath = new File("./src/main/resources/DiaryEntries");

        if (!filePath.exists()) {
            throw new IOException(
                "Could not find chosen path to " + filePath.getName());
        }

        File jsonFile = new File(
            filePath + "/" + interpretName(username) + ".json");
        if (jsonFile.exists()) {
            BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(jsonFile), StandardCharsets.UTF_8));

            Gson gson = new GsonBuilder().setLenient().create();
            Entry[] entries = gson
                .fromJson(bufferedReader, Entry[].class);
            List<Entry> readEntries = Arrays.asList(entries);
            return readEntries;
        } else {
            return null;
        }
    }

    /**
     * Read any json file with provided username and date from
     * main/resources/DiaryEntries and returns an Entry object if found.
     * @param username A string that indicate user identify. Used to locate
     *                 corresponding json file.
     * @param date     The date to check
     * @return The Entry object if found, otherwise return a new Entry object
     */
    public static Entry read(final String username, final String date) {
        try {
            List<Entry> entries = read(username);

            if (entries == null) {
                return new Entry(username, "", date);
            }

            for (Entry entry : entries) {
                if (entry.getDate().equals(date)) {
                    return entry;
                }
            }
            return new Entry(username, "", date);

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    private static String interpretName(final String input) {
        // Would make it easy to obfuscate these names in the future
        return input;
    }

    /**
     * Basic test reading of 1 set .JSON file on specific date.
     * @param args No input parameters
     */
    public static void main(final String[] args) {
        try {
            Entry entry = EntryFromJSON.read("Ola", "15-09-2021");
            System.out.println(
                entry.getUsername()
                + " - "
                + entry.getDate()
                + " -- "
                + entry.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
