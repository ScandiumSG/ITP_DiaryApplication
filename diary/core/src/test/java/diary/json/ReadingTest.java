package diary.json;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import diary.core.Entry;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;

public class ReadingTest {

    private final static File testFilePath = new File("src/main/resources/TestEntries.json");

    @BeforeAll
    public static void deleteFileIfExists() {
        if(testFilePath.exists()){
            testFilePath.delete();
        }
        else;
    }

    @Test
    public void testReading() throws IOException {
        // Check if a non-existant filepath will return a empty list object
        List<Entry> output = EntryFromJSON.read(testFilePath);
        Assertions.assertTrue(output.isEmpty());
        Assertions.assertTrue(output instanceof List<Entry>);

        // Check if entry's can be successfully written and read
        Entry entry = new Entry("Testinnhold_B", "11-11-2011");
        EntryToJSON.write(entry, testFilePath);
        Entry readEntry = EntryFromJSON.read(entry.getDate(), testFilePath);
        Assertions.assertEquals(entry.getContent(), readEntry.getContent());
        Assertions.assertEquals(entry.getDate(), readEntry.getDate());

        // Check if writing a entry on same date as previous entry successfully
        // overwrites
        Entry overwriteEntry = new Entry("Testinnhold_C", "11-11-2011");
        EntryToJSON.write(overwriteEntry, testFilePath);
        Entry readOverwriteEntry = EntryFromJSON.read(overwriteEntry.getDate(), testFilePath);
        Assertions.assertEquals(
            overwriteEntry.getContent(), readOverwriteEntry.getContent());
        Assertions.assertEquals(
            overwriteEntry.getDate(), readOverwriteEntry.getDate());

        }

    @Test
    public void testReadEmptyFile() throws IOException {
        Entry emptyFile = EntryFromJSON.read("15-10-1990", testFilePath);
        Assertions.assertEquals("", emptyFile.getContent());
        Assertions.assertEquals("15-10-1990", emptyFile.getDate());
    }

    @AfterAll
    public static void deleteIfStillExists(){
        if(testFilePath.exists()){
            testFilePath.delete();
        }
        else;
    }
}
