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
import java.util.HashMap;
import java.util.stream.Collectors;

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
    public static HashMap<String, Entry> read(final User user, final String fileName) throws IOException {

        HashMap<String, Entry> readEntries = new HashMap<String, Entry>();
        File chosenFile = new File(
            PersistancePaths.makeResourcesPathString(user, fileName));

        PersistanceUtil.checkDirExistance(chosenFile.getAbsolutePath());
        if (!chosenFile.exists()) {
            return readEntries;
        }

        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(
                new FileInputStream(chosenFile), StandardCharsets.UTF_8));

        Gson gson = new GsonBuilder().setLenient().create();
        Entry[] entries = gson.fromJson(bufferedReader, Entry[].class);
        if (entries != null) {
            for (Entry entry : entries) {
                readEntries.put(entry.getDate(), entry);
            }
        }
        bufferedReader.close();
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
            HashMap<String, Entry> entries = read(user, fileName);
            if (entries.keySet().contains(date)) {
                return entries.get(date);
            } else {
                return new Entry("", date);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves the content of a JSON file as a un-interpreted string. Method intended
     * to easy integration with REST-API.
     * @param fileName full filename for the json file that is to be read.
     * @param relPath Boolean, if file is located in root-dir or src/main/resources
     * @return String A json string of the content of loaded entry.
     * @throws IOException If no file of provided name can be read.
     */
    public static String readToString(final String fileName, boolean relPath)
        throws IOException {
        String filePath;
        if (relPath) {
            filePath = PersistancePaths.makeResourcesPathString(fileName);
        } else {
            filePath = PersistancePaths.makeCurrentDirectoryPathString(fileName);
        }

        PersistanceUtil.checkDirExistance(filePath);
        return retrieveJsonString(filePath);
    }

    private static String retrieveJsonString(final String fullFilePath)
        throws IOException {
        File chosenFile = new File(fullFilePath);

        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(new FileInputStream(chosenFile), StandardCharsets.UTF_8));

        String retrievedString = bufferedReader.lines().collect(Collectors.joining());
        bufferedReader.close();

        return retrievedString;
    }
}
