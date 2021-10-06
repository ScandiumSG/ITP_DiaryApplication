package diary.json;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import diary.core.Entry;
import java.io.IOException;
import java.util.List;

public class ReadingTest {

    @Test
    public void testReading() throws IOException {
        // Check if entry's can be successfully written and read
        Entry entry = new Entry("ReadTest", "Testinnhold_B", "11-11-2011");
        EntryToJSON.write(entry);
        Entry readEntry = EntryFromJSON.read("ReadTest", "11-11-2011");
        Assertions.assertEquals(entry.getUsername(), readEntry.getUsername());
        Assertions.assertEquals(entry.getContent(), readEntry.getContent());
        Assertions.assertEquals(entry.getDate(), readEntry.getDate());

        // Check if writing a entry on same date as previous entry successfully
        // overwrites
        Entry overwriteEntry = new Entry(
            "ReadTest", "Testinnhold_C", "11-11-2011");
        EntryToJSON.write(overwriteEntry);
        Entry readOverwriteEntry = EntryFromJSON.read(
            overwriteEntry.getUsername(), overwriteEntry.getDate());
        Assertions.assertEquals(
            overwriteEntry.getUsername(), readOverwriteEntry.getUsername());
        Assertions.assertEquals(
            overwriteEntry.getContent(), readOverwriteEntry.getContent());
        Assertions.assertEquals(
            overwriteEntry.getDate(), readOverwriteEntry.getDate());

        File writtenFile = new File("./src/main/resources/DiaryEntries/"
            + interpretName(overwriteEntry.getUsername())
            + ".json");
            writtenFile.deleteOnExit();

        }

    @Test
    public void testReadEmptyFile() throws IOException {
        Entry emptyFile = EntryFromJSON.read("NoFile", "15-10-2020");
        Assertions.assertEquals("NoFile", emptyFile.getUsername());
        Assertions.assertEquals("", emptyFile.getContent());
        Assertions.assertEquals("15-10-2020", emptyFile.getDate());

        List<Entry> emptyFile2 = EntryFromJSON.read("NoFile2");
        Assertions.assertEquals(null, emptyFile2);
    }

    private static String interpretName(String input) {
        // Would make it easy to obfuscate these names in the future
        return input;
    }
}
