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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * EntryFromJSON returns a list or specific entry from JSON.
 * @since 1.0
 * @author Stian K. Gaustad, Lars Overskeid
 */
public final class EntryFromJSON {

    private static File filePath = EntryToJSON.getJsonFile();


    // Use GSON to read/write the JSON files
    // https://github.com/google/gson

    private EntryFromJSON() {
        // Not called
    }


    /**
     * Read any json file with provided username from
     * main/resources/DiaryEntries and returns as a unsorted ArrayList of Entry.
     * @param overridePath path to testFile.
     * @return List of all found Entry's stored under the provided username.
     * @throws IOException If filepath to resources is nonexistant.
     *
     * @see diary.json#read()
     */
    public static List<Entry> read(final File overridePath) throws IOException {
        File orgFilePath = filePath;
        filePath = overridePath;
        List<Entry> result = read();
        filePath = orgFilePath;
        return result;
    }


    /**
     * Read any json file with provided username and date from
     * main/resources/DiaryEntries and returns an Entry object if found.
     * @param overridePath path to testFile.
     * @param date     The date to check
     * @return The Entry object if found, otherwise return a new Entry object
     *
     * @see diary.json#read(String date)
     */
    public static Entry read(final String date, final File overridePath) {
        File orgFilePath = filePath;
        filePath = overridePath;
        Entry result = read(date);
        filePath = orgFilePath;
        return result;
    }


    /**
     * Read any json file with provided username from
     * main/resources/DiaryEntries and returns as a unsorted ArrayList of Entry.
     * @return List of all found Entry's stored under the provided username.
     * @throws IOException If filepath to resources is nonexistant.
     */
    public static List<Entry> read() throws IOException {

        List<Entry> readEntries = new ArrayList<Entry>();

        if (!filePath.exists()) {
            return readEntries;
        }

        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(
                new FileInputStream(filePath), StandardCharsets.UTF_8));

        Gson gson = new GsonBuilder().setLenient().create();
        Entry[] entries = gson
            .fromJson(bufferedReader, Entry[].class);

        if (entries != null) {
            readEntries = Arrays.asList(entries);
        }

        return readEntries;
    }

    /**
     * Read any json file with provided username and date from
     * main/resources/DiaryEntries and returns an Entry object if found.
     * @param date     The date to check
     * @return The Entry object if found, otherwise return a new Entry object
     */
    public static Entry read(final String date) {
        try {
            List<Entry> entries = read();

            if (entries != null) {
                for (Entry entry : entries) {
                    if (entry.getDate().equals(date)) {
                        return entry;
                    }
                }
            }

            return new Entry("", date);

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
}
