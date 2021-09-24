package diary.json;

import diary.core.Entry;
import java.io.IOException;
import java.io.File;
import com.google.gson.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class EntryFromJSON {
    // Use GSON to read/write the JSON files
    // https://github.com/google/gson

    /**
     * Read any json file with provided username from main/resources/DiaryEntries and
     * returns as a unsorted ArrayList<Entry>
     * 
     * @param username A string that indicate user identify. Used to locate
     *                 corresponding json file.
     * @return List of all found Entry's stored under the provided username.
     * @throws IOException
     */
    public List<Entry> read(String username) throws IOException {
        File filePath = new File("./src/main/resources/DiaryEntries");

        if (!filePath.exists()) {
            throw new IOException("Could not find chosen path to " + filePath.getName());
        }

        File jsonFile = new File(filePath + "/" + interpretName(username) + ".json");
        if (jsonFile.exists()) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFile.getAbsolutePath()));

            Gson gson = new GsonBuilder().setLenient().create();
            Entry[] entries = gson.fromJson(bufferedReader, Entry[].class);
            List<Entry> readEntries = Arrays.asList(entries);
            return readEntries;
        } else {
            return null;
        }
    }


    /**
     * Read any json file with provided username and date from main/resources/DiaryEntries
     * and returns an Entry object if found
     * 
     * @param username  A string that indicate user identify. Used to locate
     *                  corresponding json file.
     * @param date      The date to check
     * @return          The Entry object, if found
     */
    public Entry read(String username, String date){
        try {
            List<Entry> entries = read(username);

            for (Entry entry : entries) {
                if (entry.getDate() == date){
                    return entry;
                }
            }
            return null;

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    private String interpretName(String input) {
        // Would make it easy to obfuscate these names in the future
        return input;
    }

    public static void main(String[] args) {
        EntryFromJSON reader = new EntryFromJSON();
        try {
            List<Entry> entries = reader.read("Ola");
            for (Entry entry : entries) {
                System.out.println(entry.getUsername() + " - " + entry.getDate() + " -- " + entry.getContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}