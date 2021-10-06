package diary.json;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import diary.core.Entry;
import java.io.IOException;

public class WritingTest {

    @Test
    public void testMakingNewFile() throws IOException {
        String user = new String("TestUser_A");
        File jsonFile = new File("./src/main/resources/DiaryEntries/" + interpretName(user) + ".json");
        Assertions.assertFalse(jsonFile.exists());
        Entry entry = new Entry(user, "TestFile - Should auto delete");
        Assertions.assertTrue(EntryToJSON.write(entry, true));
    }

    @Test
    public void testWriting() throws IOException {
        Entry entry = new Entry("TestUser_B", "Testinnhold_B", "11-11-2011");
        Assertions.assertTrue(EntryToJSON.write(entry, true));
    }

    private static String interpretName(String input) {
        // Would make it easy to obfuscate these names in the future
        return input;
    }

}
