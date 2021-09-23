package diary.json;

import diary.core.Entry;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import com.fasterxml.jackson.jr.ob.*;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.lang.StringBuilder;

public class EntryToJSON {
    // Use Jackson Jr to read/write the JSON files
    // https://github.com/FasterXML/jackson-jr

    public void write(Entry entry) throws IOException, FileNotFoundException {
        File filePath = new File("./src/main/resources/DiaryEntries");

        if (!filePath.exists()) {
            if (!filePath.mkdirs()) {
                throw new IOException("Could not find chosen path to " + filePath.getName());
            }
        }

        StringBuilder jsonString = new StringBuilder();
        File jsonFile = new File(filePath + "/" + makeFileName(entry.getUsername()) + ".json");
        if (new File(filePath + "/" + makeFileName(entry.getUsername()) + ".json").exists()) {
            jsonString.append(JSONtoString(jsonFile, entry.getUsername()));

            if (jsonString.toString().contains(entry.getDate())) {
                String string = jsonString.toString();
                int startIndex = string.indexOf(entry.getDate());
                int endIndex = string.indexOf("}", startIndex);
                String newEntry = JSON.std.with(JSON.Feature.PRETTY_PRINT_OUTPUT).composeString().startObject()
                        .startObjectField(entry.getDate()).put("entry", entry.getContent()).end().end().finish();
                newEntry = newEntry.substring(3, newEntry.length() - 3);
                jsonString.replace(startIndex - 3, endIndex + 1, newEntry);
            } else {
                String newEntry = JSON.std.with(JSON.Feature.PRETTY_PRINT_OUTPUT).composeString().startObject()
                        .startObjectField(entry.getDate()).put("entry", entry.getContent()).end().end().finish();
                newEntry = newEntry.substring(2, newEntry.length() - 3);
                jsonString.insert(jsonString.toString().length() - 3, ",");
                jsonString.insert(jsonString.toString().length() - 3, newEntry);
            }
            writeToFile(jsonFile, jsonString.toString(), false);
        } else {
            String newEntry = JSON.std.with(JSON.Feature.PRETTY_PRINT_OUTPUT).composeString().startObject()
                    .startObjectField(entry.getDate()).put("entry", entry.getContent()).end().end().finish();
            jsonString.append(newEntry);
            writeToFile(jsonFile, jsonString.toString(), false);
        }

    }

    private void writeToFile(File jsonFile, String jsonString, Boolean append) {
        // Write to the JSON file
        try (FileWriter file = new FileWriter(jsonFile, append)) {
            // Write the JSON object to file
            file.write(jsonString.toString());
            // Flush writer stream
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A support method to open and read the content of a specific .json file
     * 
     * @param json Absolute filepath to the chosen .json file as a string.
     * @return A string of the entire .json file
     * @throws FileNotFoundException
     * @throws IOException
     */
    private String JSONtoString(File path, String username) throws FileNotFoundException, IOException {
        String json = (path.getAbsolutePath());
        return new String(Files.readAllBytes(Paths.get(json)));
    }

    private String makeFileName(String input) {
        // Would make it easy to hash these names in the future
        return input;
    }

    public static void main(String[] args) {
        Entry entry = new Entry("Ola", "Pre-overwrite on 10-09-2021", "10-09-2021");
        Entry entry2 = new Entry("Ola", "Content3");
        Entry entry3 = new Entry("Ola", "Post-overwrite of content this day", "10-09-2021");
        Entry entry4 = new Entry("Ola", "grewerfwewerrwewer", "15-09-2021");
        try {
            EntryToJSON saver = new EntryToJSON();
            saver.write(entry);
            saver.write(entry2);
            saver.write(entry3);
            saver.write(entry4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}