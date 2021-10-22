package diary.json;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import diary.core.Entry;
import java.io.IOException;

public class WritingTest {

    private final static File testFilePath = new File("src/main/resources/TestEntries.json");

    @BeforeAll
    public static void deleteFileIfExists(){
        if(testFilePath.exists()){
            testFilePath.delete();
        }
        else;
    }

    @Test
    public void testWriting() throws IOException {
        Entry entry = new Entry("TestFile - Should auto delete");
        EntryToJSON.write(entry, testFilePath);
        Assertions.assertTrue(testFilePath.exists());
    }

    @AfterAll
    public static void deleteIfStillExists(){
        if(testFilePath.exists()){
            testFilePath.delete();
        }
        else;
    }
}
