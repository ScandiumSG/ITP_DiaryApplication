package diary.json;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import diary.core.Entry;
import diary.core.User;
import java.io.IOException;

public class WritingTest {
    private final static String testFileName = "TestEntires";
    private final static User user = new User("TestUser", 2425);
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
    public void testWriting() throws IOException {
        Entry entry = new Entry("TestFile - Should auto delete");
        EntryToJSON.write(user, testFileName, entry);
        Assertions.assertTrue(testFilePath.exists());
    }

    @Test
    public void testGetJsonFile() {
        Object output = EntryToJSON.getJsonFile(user, testFileName);
        Assertions.assertTrue(output instanceof File);
    }

    @AfterAll
    public static void deleteIfStillExists() {
        if (testFilePath.exists()) {
            testFilePath.delete();
        } else
            ;
    }
}
