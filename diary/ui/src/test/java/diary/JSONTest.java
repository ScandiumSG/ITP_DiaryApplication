package diary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import diary.core.Entry;
import diary.json.*;
import java.io.IOException;

public class JSONTest {

    @Test
    public void testMakingNewFile() throws IOException {
        String user = new String("TestWriting");
        File jsonFile = new File("./src/main/resources/DiaryEntries/" + interpretName(user) + ".json");
        jsonFile.deleteOnExit();
        Assertions.assertFalse(jsonFile.exists());
        Entry entry = new Entry(user, "TestFile - Should auto delete");
        EntryToJSON.write(entry);
    }

    @Test
    public void testWritingAndReading() throws IOException {
        // Check if entry's can be successfully written and read
        Entry entry = new Entry("Testbruker_A", "Testinnhold_B", "11-11-2011");
        EntryToJSON.write(entry);
        Entry readEntry = EntryFromJSON.read("Testbruker_A", "11-11-2011");
        Assertions.assertEquals(entry.getUsername(), readEntry.getUsername());
        Assertions.assertEquals(entry.getContent(), readEntry.getContent());
        Assertions.assertEquals(entry.getDate(), readEntry.getDate());

        // Check if writing a entry on same date as previous entry successfully
        // overwrites
        Entry overwriteEntry = new Entry("Testbruker_A", "Testinnhold_C", "11-11-2011");
        EntryToJSON.write(overwriteEntry);
        Entry readOverwriteEntry = EntryFromJSON.read("Testbruker_A", "11-11-2011");
        Assertions.assertEquals(overwriteEntry.getUsername(), readOverwriteEntry.getUsername());
        Assertions.assertEquals(overwriteEntry.getContent(), readOverwriteEntry.getContent());
        Assertions.assertEquals(overwriteEntry.getDate(), readOverwriteEntry.getDate());

        File jsonFile = new File(
                "./src/main/resources/DiaryEntries/" + interpretName(overwriteEntry.getUsername()) + ".json");
        jsonFile.deleteOnExit();

    }

    private static String interpretName(String input) {
        // Would make it easy to obfuscate these names in the future
        return input;
    }

}
