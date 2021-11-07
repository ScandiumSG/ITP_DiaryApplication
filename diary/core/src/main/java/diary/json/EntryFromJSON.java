package diary.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import diary.core.Entry;
import diary.core.User;
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
 *
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
     * Read any json file with provided username from main/resources/DiaryEntries
     * and returns as a unsorted ArrayList of Entry.
     *
     * @param fileName A string of the name of diary/.json file to be read from.
     *
     * @return List of all found Entry's stored under the provided username.
     * @throws IOException If filepath to resources is nonexistant.
     */
    public static List<Entry> read(final User user, final String fileName) throws IOException {

        List<Entry> readEntries = new ArrayList<Entry>();

        String jsonString = retrieveJsonString(
            PersistanceUtil.makeResourcesPathString(user, fileName));

        Gson gson = new GsonBuilder().setLenient().create();
        Entry[] entries = gson.fromJson(jsonString, Entry[].class);
        if (entries != null) {
            readEntries = Arrays.asList(entries);
        }
        return readEntries;
    }

    /**
     * Read any json file with provided username and date from
     * main/resources/DiaryEntries and returns an Entry object if found.
     *
     * @param fileName A string of the name of diary/.json file to be read from.
     * @param date     The date to check
     *
     * @return The Entry object if found, otherwise return a new Entry object
     */
    public static Entry read(final User user, final String fileName, final String date) {
        try {
            List<Entry> entries = read(user, fileName);
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

    public static String readToString(final String fileName, boolean relPath)
        throws IOException {
        String filePath;
        if (relPath) {
            filePath = PersistanceUtil.makeResourcesPathString(fileName);
        } else
            filePath = PersistanceUtil.makeCurrentDirectoryPathString(fileName);
        return retrieveJsonString(filePath);
    }

    private static String retrieveJsonString(final String fullFilePath)
        throws IOException {
        File chosenFile = new File(fullFilePath);

        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(new FileInputStream(chosenFile), StandardCharsets.UTF_8));

        String retrievedString = bufferedReader.toString();
        bufferedReader.close();

        return retrievedString;
    }
}
