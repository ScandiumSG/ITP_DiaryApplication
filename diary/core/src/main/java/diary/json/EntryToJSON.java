package diary.json;

import diary.core.Entry;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import java.util.ArrayList;

/**
 * Classes containing static methods to read and write to/from JSON.
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
     * EntryToJSON.write() will only write the content of a single entry to
     * a file, either appending new content or overwriting existing content
     * at the specified date.
     * @param entry A diary.Entry object to be added to the users .JSON storage
     * @throws IOException .JSON location does not exist.
     */
    public static void write(final Entry entry)
    throws IOException {
        File filePath = new File(
            "./src/main/resources/DiaryEntries");

        if (!filePath.exists()) {
            if (!filePath.mkdirs()) {
                throw new IOException(
                    "Could not find chosen path to "
                    + filePath.getName());
            }
        }

        File jsonFile = new File(
            filePath
            + "/"
            + interpretName(entry.getUsername())
            + ".json");
        List<Entry> entries = new ArrayList<Entry>();

        if (jsonFile.exists()) {
            entries.addAll(EntryFromJSON.read(entry.getUsername()));
            jsonFile.delete();
            entries.removeIf(d -> d.getDate().equals(entry.getDate()));

        }
        entries.add(entry);

        Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
        FileWriter fw = new FileWriter(jsonFile, false);
        gson.toJson(entries, fw);
        fw.close();
    }

    private static String interpretName(final String input) {
        // Would make it easy to obfuscate these names in the future
        return input;
    }

    /**
     * Basic test writing of some .JSON file.
     * @param args
     */
    public static void main(final String[] args) {
        Entry entry = new Entry(
            "Ola", "Pre-overwrite on 10-09-2021", "10-09-2021");
        Entry entry2 = new Entry(
            "Ola", "Content3");
        Entry entry3 = new Entry(
            "Ola", "Post-overwrite of content this day", "10-09-2021");
        Entry entry4 = new Entry(
            "Ola", "3rd Entry to file", "15-09-2021");
        try {
            EntryToJSON.write(entry);
            EntryToJSON.write(entry2);
            EntryToJSON.write(entry3);
            EntryToJSON.write(entry4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
