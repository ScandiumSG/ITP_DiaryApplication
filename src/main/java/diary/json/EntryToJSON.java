package diary.json;

import diary.core.Entry;
import java.io.IOException;
import java.io.File;
import com.fasterxml.jackson.jr.ob.*;
import java.io.FileWriter;

public class EntryToJSON {
    // Use Jackson Jr to read/write the JSON files
    // https://github.com/FasterXML/jackson-jr

    public void write(Entry entry, Boolean append) throws IOException {
        File filePath = new File(System.getProperty("user.home") + "\\Documents\\Diary\\lib");

        if (!filePath.exists()) {
            if (!filePath.mkdirs()) {
                throw new IOException("Could not find chosen path to USER\\Diary");
            }

        }

        File jsonFile = new File(filePath + "\\" + makeFileName(entry.getUsername()) + ".json");

        String jsonString = JSON.std.with(JSON.Feature.PRETTY_PRINT_OUTPUT)
                .without(JSON.Feature.FAIL_ON_DUPLICATE_MAP_KEYS).composeString().startObject()
                .startObjectField(entry.getDate()).put("entry", entry.getContent()).end().end().finish();

        // Write to the JSON file
        try (FileWriter file = new FileWriter(jsonFile, append)) {
            // Write the JSON object to file
            file.write(jsonString);
            // Flush writer stream
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String makeFileName(String input) {
        // Would make it easy to hash these names in the future
        return input;
    }

    public static void main(String[] args) {
        Entry entry = new Entry("Ola", "Content");
        Entry entry2 = new Entry("Ola", "Content2");
        Entry entry3 = new Entry("Ola", "Content3");
        Entry entry4 = new Entry("John", "Content1");
        try {
            EntryToJSON saver = new EntryToJSON();
            saver.write(entry, true);
            saver.write(entry2, true);
            saver.write(entry3, true);
            saver.write(entry4, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}