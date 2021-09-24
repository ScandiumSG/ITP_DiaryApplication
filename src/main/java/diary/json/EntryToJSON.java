package diary.json;

import diary.core.Entry;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import com.google.gson.*;
import java.util.List;
import java.util.ArrayList;

public class EntryToJSON {
    // Use GSON to read/write the JSON files
    // https://github.com/google/gson

    public static void write(Entry entry) throws IOException, FileNotFoundException {
        File filePath = new File("./src/main/resources/DiaryEntries");

        if (!filePath.exists()) {
            if (!filePath.mkdirs()) {
                throw new IOException("Could not find chosen path to " + filePath.getName());
            }
        }

        File jsonFile = new File(filePath + "/" + interpretName(entry.getUsername()) + ".json");
        List<Entry> Entries = new ArrayList<Entry>();

        if (jsonFile.exists()) {
            Entries.addAll(EntryFromJSON.read(entry.getUsername()));
            jsonFile.delete();
            Entries.removeIf(d -> d.getDate().equals(entry.getDate()));

        }
        Entries.add(entry);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter fw = new FileWriter(jsonFile, false);
        gson.toJson(Entries, fw);
        fw.close();
    }

    private static String interpretName(String input) {
        // Would make it easy to obfuscate these names in the future
        return input;
    }

    public static void main(String[] args) {
        Entry entry = new Entry("Ola", "Pre-overwrite on 10-09-2021", "10-09-2021");
        Entry entry2 = new Entry("Ola", "Content3");
        Entry entry3 = new Entry("Ola", "Post-overwrite of content this day", "10-09-2021");
        Entry entry4 = new Entry("Ola", "3rd Entry to file", "15-09-2021");
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