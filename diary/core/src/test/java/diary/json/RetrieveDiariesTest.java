package diary.json;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import diary.core.Entry;
import diary.core.User;


public class RetrieveDiariesTest {
    private static User user;
    private static User user2;
    private static String diary1;
    private static String diary2;
    private static String diary3;
    private static File testFile1;
    private static File testFile2;
    private static File testFile3;

    @BeforeAll
    public static void generateUserAndDiaries() throws IOException {
        diary1 = "testDiary1";
        Entry entry1 = new Entry("entry1", "10-10-2010");
        Entry entry2 = new Entry("entry2", "20-10-2010");

        diary2 = "testDiary2";
        Entry entry3 = new Entry("entry3", "10-10-2010");
        Entry entry4 = new Entry("entry4", "20-10-2010");

        diary3 = "testDiary3";
        Entry entry5 = new Entry("entry5", "10-10-2010");
        Entry entry6 = new Entry("entry6", "20-10-2010");

        String username = "testUser";
        Integer userpin = ThreadLocalRandom.current().nextInt(1000, 9999+1);
        user = new User(username,  String.valueOf(userpin));

        String username2 = "testOtherUser";
        Integer userpin2 = ThreadLocalRandom.current().nextInt(1000, 9999+1);
        user2 = new User(username2,  String.valueOf(userpin2));

        testFile1 = EntryToJSON.getJsonFile(user, diary1);
        testFile2 = EntryToJSON.getJsonFile(user, diary2);
        testFile3 = EntryToJSON.getJsonFile(user2, diary3);

        EntryToJSON.write(user, diary1, entry1);
        EntryToJSON.write(user, diary1, entry2);
        EntryToJSON.write(user, diary2, entry3);
        EntryToJSON.write(user, diary2, entry4);
        EntryToJSON.write(user2, diary3, entry5);
        EntryToJSON.write(user2, diary3, entry6);
    }

    @Test
    public void testRetrieveAllDiaries() throws IOException {
        HashMap<String, List<Entry>> retrievedEntries =
            RetrieveDiaries.findDiaries(user);

        Assertions.assertTrue(retrievedEntries.keySet().size() == 2);
        Assertions.assertTrue(retrievedEntries.keySet().contains(diary1));
        Assertions.assertTrue(retrievedEntries.keySet().contains(diary2));
        Assertions.assertFalse(retrievedEntries.keySet().contains(diary3));

        Assertions.assertTrue(retrievedEntries.get(diary1).size() == 2);
        Assertions.assertTrue(retrievedEntries.get(diary2).size() == 2);
    }

    @Test
    public void testListFilesStartingWith() {
        List<String> foundFiles = PersistanceUtil.getFilesStartingWith(user.getUserID(), true);

        Assertions.assertTrue(foundFiles.size() == 2);
        Assertions.assertTrue(foundFiles.get(0).contains(diary1) || foundFiles.get(0).contains(diary2));
        Assertions.assertTrue(foundFiles.get(1).contains(diary1) || foundFiles.get(1).contains(diary2));
    }

    @Test
    public void testInvalidUser() throws IOException {
        User invalidUser = new User("S+K", "1234");
        Entry testEntry = new Entry("test123");
        String invalidUserFileName = "invalid User Diary";
        EntryToJSON.write(invalidUser, invalidUserFileName, testEntry);
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {RetrieveDiaries.findDiaries(invalidUser);});

        EntryToJSON.getJsonFile(invalidUser, invalidUserFileName).delete();
    }

    @AfterAll
    public static void deleteTestFiles() throws Exception {
        testFile1.delete();
        testFile2.delete();
        testFile3.delete();
    }
}
