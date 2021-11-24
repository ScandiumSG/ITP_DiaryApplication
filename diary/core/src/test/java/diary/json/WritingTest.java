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

    /**
     * Test that EntryToJSON.write makes a file
     * @throws IOException When issue with {@link EntryToJSON.write()} occur.
     */
    @Test
    public void testWriting() throws IOException {
        Entry entry = new Entry("TestFile - Should auto delete");
        EntryToJSON.write(user, testFileName, entry);
        Assertions.assertTrue(testFilePath.exists());
    }

    /**
     * Test that the PersistanceUtil.getJsonFile method functions properly.
     */
    @Test
    public void testGetJsonFile() {
        Object output = PersistanceUtil.getJsonFile(user, testFileName);
        Assertions.assertTrue(output instanceof File);
        File outputFile = (File) output;
        Assertions.assertTrue(outputFile.getAbsolutePath().contains(user.getUserID()));
        Assertions.assertTrue(outputFile.getAbsolutePath().contains(testFileName));
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
