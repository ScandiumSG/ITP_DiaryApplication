package diary.json;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import diary.core.Entry;
import diary.core.User;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;

public class ReadingTest {
    private final static String testFileName = "TestEntires";
    private final static User user = new User("TestUser", "2425");
    private final static File testFilePath =
        EntryToJSON.getJsonFile(user, testFileName);

    @BeforeAll
    public static void deleteFileIfExists() {
        if (testFilePath.exists()) {
            testFilePath.delete();
        } else
            ;
    }

    @Test
    public void testReading() throws IOException {
        // Check if a non-existant filepath will return a empty list object
        List<Entry> output = EntryFromJSON.read(user, testFileName);
        Assertions.assertTrue(output.isEmpty());
        Assertions.assertTrue(output instanceof List<Entry>);

        // Check if entry's can be successfully written and read
        Entry entry = new Entry("Testinnhold_B", "11-11-2011");
        EntryToJSON.write(user, testFileName, entry);
        Entry readEntry = EntryFromJSON.read(user, testFileName, entry.getDate());
        Assertions.assertEquals(entry.getContent(), readEntry.getContent());
        Assertions.assertEquals(entry.getDate(), readEntry.getDate());

        // Check if writing a entry on same date as previous entry successfully
        // overwrites
        Entry overwriteEntry = new Entry("Testinnhold_C", "11-11-2011");
        EntryToJSON.write(user, testFileName, overwriteEntry);
        Entry readOverwriteEntry = EntryFromJSON.read(user, testFileName, overwriteEntry.getDate());
        Assertions.assertEquals(overwriteEntry.getContent(), readOverwriteEntry.getContent());
        Assertions.assertEquals(overwriteEntry.getDate(), readOverwriteEntry.getDate());
    }

    @Test
    public void testReadEmptyFile() throws IOException {
        Entry emptyFile = EntryFromJSON.read(user, testFileName, "15-10-1990");
        Assertions.assertEquals("", emptyFile.getContent());
        Assertions.assertEquals("15-10-1990", emptyFile.getDate());
    }

    @Test
    public void testReadToString() throws IOException {
        String jsonContent = "Read Json to string content";
        String jsonDate = "15-10-2000";
        Entry entry = new Entry( jsonContent, jsonDate);
        EntryToJSON.write(user, "toStringTest", entry);
        String retrievedJson = EntryFromJSON.readToString(user.getUserID() + "+" + "toStringTest", true);
        File file = new File(
            PersistanceUtil.makeResourcesPathString(user, "toStringTest"));
        file.delete();

        Assertions.assertFalse(retrievedJson.isEmpty());
        Assertions.assertTrue(retrievedJson.contains(entry.getContent()));
        Assertions.assertTrue(retrievedJson.contains(entry.getDate()));
    }

    @AfterAll
    public static void deleteIfStillExists() {
        if (testFilePath.exists()) {
            testFilePath.delete();
        } else
            ;
    }
}
