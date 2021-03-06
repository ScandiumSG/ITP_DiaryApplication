package diary.json;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import diary.core.Entry;
import diary.core.User;
import java.io.IOException;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;

public class ReadingTest {
    private final static String testFileName = "TestEntires";
    private final static User user = new User("TestUser", "2425");
    private final static File testFilePath =
        PersistanceUtil.getJsonFile(user, testFileName);

    /**
     * Prepare for test.
     * Remove file if it exists, as each test will use existance of file as
     * indication of method validity.
     */
    @BeforeAll
    public static void deleteFileIfExists() {
        if (testFilePath.exists()) {
            testFilePath.delete();
        }
    }

    @Test
    public void testReading() throws IOException {
        // Check if a non-existant filepath will return a empty list object
        HashMap<String, Entry> output = EntryFromJSON.read(user, testFileName);
        Assertions.assertTrue(output.isEmpty());
        Assertions.assertTrue(output instanceof HashMap<String, Entry>);

        // Check if entry's can be successfully written and read
        Entry entry = new Entry("Testinnhold_B", "11-11-2011");
        EntryToJSON.write(user, testFileName, entry);
        HashMap<String, Entry> mapInnholdB = EntryFromJSON.read(user, testFileName);
        Entry readEntry = mapInnholdB.get("11-11-2011");
        Assertions.assertEquals(entry.getContent(), readEntry.getContent());
        Assertions.assertEquals(entry.getDate(), readEntry.getDate());

        // Check if writing a entry on same date as previous entry successfully
        // overwrites
        Entry overwriteEntry = new Entry("Testinnhold_C", "11-11-2011");
        EntryToJSON.write(user, testFileName, overwriteEntry);
        HashMap<String, Entry> mapInnholdC = EntryFromJSON.read(user, testFileName);
        Entry readOverwriteEntry = mapInnholdC.get("11-11-2011");
        Assertions.assertEquals(
            overwriteEntry.getContent(), readOverwriteEntry.getContent());
        Assertions.assertEquals(
            overwriteEntry.getDate(), readOverwriteEntry.getDate());
    }

    @Test
    public void testReadEmptyFile() throws IOException {
        user.updateUserDiaries();
        Entry emptyFile = user.getEntryByDate(testFileName, "15-10-1990");
        Assertions.assertEquals("", emptyFile.getContent());
        Assertions.assertEquals("15-10-1990", emptyFile.getDate());
    }

    @Test
    public void testReadToString() throws IOException {
        String jsonContent = "Read Json to string content";
        String jsonDate = "15-10-2000";
        Entry entry = new Entry( jsonContent, jsonDate);
        EntryToJSON.write(user, "toStringTest", entry);
        String retrievedJson = EntryFromJSON.readToString(
            user.getUserID() + "+" + "toStringTest", true);
        File file = new File(
            PersistancePaths.makeResourcesPathString(user, "toStringTest"));
        file.delete();

        Assertions.assertFalse(retrievedJson.isEmpty());
        Assertions.assertTrue(retrievedJson.contains(entry.getContent()));
        Assertions.assertTrue(retrievedJson.contains(entry.getDate()));
    }

    /**
     * Clean up testfiles after the method is ran.
     */
    @AfterAll
    public static void deleteIfStillExists() {
        if (testFilePath.exists()) {
            testFilePath.delete();
        }
    }
}
