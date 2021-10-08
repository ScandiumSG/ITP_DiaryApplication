package diary.json;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import diary.core.Entry;
import java.io.IOException;

public class WritingTest {

    private final File testFilePath = new File("src/main/resources/TestEntries.json");


    @BeforeEach
    public void testNoTestFile() throws IOException {
        Assertions.assertFalse(testFilePath.exists());
    }

    @Test
    public void testWriting() throws IOException {
        Entry entry = new Entry("TestFile - Should auto delete");
        EntryToJSON.write(entry, testFilePath);
        Assertions.assertTrue(testFilePath.exists());
        testFilePath.deleteOnExit();
    }
}
