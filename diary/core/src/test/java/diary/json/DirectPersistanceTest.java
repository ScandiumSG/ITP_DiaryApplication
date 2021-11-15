package diary.json;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

public class DirectPersistanceTest {
    
    @Test
    public void testDirectInResources() throws IOException {
        String content = "test";
        String testFileName = "direct+Write+File";
        EntryToJSON.write(testFileName, content, true);
        File directWriteFile = PersistanceUtil.getJsonFile(testFileName);
        Assertions.assertTrue(directWriteFile.exists());

        String directRead = EntryFromJSON.readToString(testFileName, true);
        Assertions.assertTrue(directRead.contains(content));

        directWriteFile.delete();
    }

    @Test
    public void testDirectInRoot() throws IOException {
        String content = "test file, should auto-delete";
        String testFileName = "direct+Write+File";
        EntryToJSON.write(testFileName, content, false);
        File directWriteFile = new File(
            PersistancePaths.makeCurrentDirectoryPathString(testFileName));
        Assertions.assertTrue(directWriteFile.exists());

        String directRead = EntryFromJSON.readToString(testFileName, false);
        Assertions.assertTrue(directRead.contains(content));

        directWriteFile.delete();
    }
}
