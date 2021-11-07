package diary.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import diary.core.Entry;
import diary.core.User;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Classes containing static methods to read and write to/from JSON.
 *
 * @since 1.0
 * @author Stian K. Gaustad
 */

public final class EntryToJSON {

    // Use GSON to read/write the JSON files
    // https://github.com/google/gson

    private EntryToJSON() {
        // Not called
    }

    /**
     * EntryToJSON.write() will only write the content of a single entry to a file,
     * either appending new content or overwriting existing content at the specified
     * date.
     *
     * @param fileName A string of the name of diary/.json file to be written to.
     * @param entry    A diary.Entry object to be added to the users .JSON storage
     *                 file is deleted after completed write.
     * @throws IOException .JSON location does not exist.
     */
    public static void write(final User user, final String fileName, final Entry entry) throws IOException {
        File writeLocation = new File(
            PersistanceUtil.makeResourcesPathString(user, fileName));

        fileWrite(user, fileName, entry, writeLocation);
    }

    public static void write(final String fileName, final String content,
        final String date, boolean relPath) throws IOException {
        File writeLocation;

        String personalInfo = fileName.substring(
            0, fileName.lastIndexOf("+") + 1);
        String diaryName = fileName.substring(
            fileName.lastIndexOf("+") + 1, fileName.length());
        String userName = fileName.substring(
            0, fileName.indexOf("+") + 1);
        String userPin = fileName.substring(
            fileName.indexOf("+") + 1, personalInfo.length());
        User user = new User(userName, Integer.valueOf(userPin));
        Entry entry = new Entry(content, date);
        if (relPath) {
            writeLocation = new File(
                PersistanceUtil.makeResourcesPathString(fileName));
        } else {
            writeLocation = new File(
                PersistanceUtil.makeCurrentDirectoryPathString(fileName));
        }
        fileWrite(user, diaryName, entry, writeLocation);
    }

    @SuppressWarnings("unused")
    private static void fileWrite(final User user, final String fileName,
        final Entry entry, final File writeLocation) throws IOException {
        List<Entry> entries = new ArrayList<Entry>();
        if (!writeLocation.exists()) {
            if (!writeLocation.createNewFile()) {
                throw new IOException("Could not find chosen path to "
                    + writeLocation.getName());
            }
        }

        entries.addAll(EntryFromJSON.read(user, fileName));
        Boolean del = writeLocation.delete();

        if (entries.size() > 0) {
            entries.removeIf(d -> d.getDate().equals(entry.getDate()));
        }
        entries.add(entry);

        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        // FileWriter fw = new FileWriter(jsonFile, false);
        FileOutputStream sm = new FileOutputStream(writeLocation);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sm, StandardCharsets.UTF_8));
        gson.toJson(entries, bw);
        sm.flush();
        bw.flush();
        sm.close();
        bw.close();
    }

    public static File getJsonFile(final User user, final String fileName) {
        String testString = PersistanceUtil.makeResourcesPathString(user, fileName);
        File currentLocation = new File("");
        return new File(currentLocation.getAbsolutePath() + "/" + testString);
    }
}
