package diary.json;

import diary.core.Entry;
import java.io.IOException;
import java.io.File;
import com.fasterxml.jackson.jr.ob.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EntryFromJSON {
    /**
     * Read any json file with provided username from Documents\Diary\lib and
     * returns as a unsorted ArrayList<Entry>
     * 
     * @param username A string that indicate user identify. Used to locate
     *                 corresponding json file.
     * @return ArrayList of all found Entry's stored under the provided username.
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Entry> read(String username) throws IOException {
        File filePath = new File(System.getProperty("user.home") + "\\Documents\\Diary\\lib");

        if (!filePath.exists()) {
            throw new IOException("Could not find chosen path to USER\\Documents\\Diary\\lib");
        }

        String jsonContent = JSONtoString(filePath, username);
        Map<String, Object> map = JSON.std.mapFrom(jsonContent);
        ArrayList<Entry> readEntries = new ArrayList<Entry>();
        for (String date : map.keySet()) {
            if (map.get(date) instanceof Map<?, ?>) {
                Map<String, String> subMap = (Map<String, String>) map.get(date);
                readEntries.add(new Entry(username, subMap.get("entry").toString(), date));
            }
        }
        return readEntries;
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
        String json = (path.getAbsolutePath() + "\\" + username + ".json");
        return new String(Files.readAllBytes(Paths.get(json)));
    }

    public static void main(String[] args) {
        EntryFromJSON reader = new EntryFromJSON();
        try {
            ArrayList<Entry> entries = reader.read("John");
            for (Entry entry : entries) {
                System.out.println(entry.getUsername() + " - " + entry.getDate() + " -- " + entry.getContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}