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
import java.io.PrintWriter;
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
    public static void write(final User user, final String fileName, final Entry entry)
        throws IOException {
        File writeLocation = new File(
            PersistancePaths.makeResourcesPathString(user, fileName));

        PersistanceUtil.checkDirExistance(writeLocation.getAbsolutePath());

        fileWrite(user, fileName, entry, writeLocation);
    }

    /**
     * Write a provided content-string and date-string directly to a json file
     * instead of making Entry intermediate objects. Method intended to easy
     * integration with REST-API.
     * @param fileName The filename of the file to write the content to.
     * @param content The entire diary
     * @param relPath Boolean switch to send to root-dir or src/main/resources
     * storage paths.
     * @throws IOException If EntryToJSON could not write to specified location.
     */
    public static void write(final String fileName, final String content,
        boolean relPath) throws IOException {
        File writeLocation;

        if (relPath) {
            writeLocation = new File(
                PersistancePaths.makeResourcesPathString(fileName));
        } else {
            writeLocation = new File(
                PersistancePaths.makeCurrentDirectoryPathString(fileName));
        }

        PersistanceUtil.checkDirExistance(writeLocation.getAbsolutePath());

        PrintWriter out = new PrintWriter(writeLocation, StandardCharsets.UTF_8);
        out.print(content);
        out.close();
    }

    // Delete boolean not used, suppressing warning.
    @SuppressWarnings("unused")
    private static void fileWrite(final User user, final String fileName,
        final Entry entry, final File writeLocation) throws IOException {
        List<Entry> entries = new ArrayList<Entry>();

        PersistanceUtil.checkDirExistance(writeLocation.getAbsolutePath());
        if (!writeLocation.exists()) {
            if (!writeLocation.createNewFile()) {
                throw new IOException("Could not find chosen path to "
                    + writeLocation.getName());
            }
        }

        entries.addAll(EntryFromJSON.read(user, fileName).values());

        Boolean del = writeLocation.delete();

        if (entries.size() > 0) {
            entries.removeIf(d -> d.getDate().equals(entry.getDate()));
        }
        entries.add(entry);

        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        FileOutputStream sm = new FileOutputStream(writeLocation);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sm, StandardCharsets.UTF_8));
        gson.toJson(entries, bw);
        sm.flush();
        bw.flush();
        sm.close();
        bw.close();
    }
}
