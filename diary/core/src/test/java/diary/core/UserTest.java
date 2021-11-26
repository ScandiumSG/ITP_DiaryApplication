package diary.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import diary.json.EntryToJSON;
import diary.json.PersistanceUtil;

public class UserTest {

    /**
     * Test that a User object can be created and that basic getters return
     * correct values.
     */
    @Test
    public void testUserCreation() {
        String username = "Ola";
        String userpin = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999+1));
        User user = new User(username, userpin);

        Assertions.assertEquals(username, user.getUserName());
        Assertions.assertEquals(userpin, user.getUserPin());
        Assertions.assertEquals(
            username+"+"+String.valueOf(userpin), user.getUserID());
    }

    /**
     * Test that user name sanitization functions as anticipated, swapping
     * " " for "_" in userIDs, but still returning " " for userName getter.
     */
    @Test
    public void testUsernameSanitization() {
        String username = "Ola Nordmann ";
        String userpin = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999+1));
        User user = new User(username, userpin);

        Assertions.assertTrue(user.getUserID().contains("_"));
        Assertions.assertFalse(user.getUserName().contains("_"));
        Assertions.assertTrue(user.getUserName().contains(" "));
        Assertions.assertNotEquals(username.trim(), user.getUserName());
    }

    /**
     * Minor test to check on the (very miniscule) user name validation. Make sure name
     * cannot be just whitespace (" ").
     */
    @Test
    public void testUsernameValidation() {
        String username = " ";
        String userpin = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999+1));

        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {new User(username, userpin);});
    }

    /**
     * Test that only valid 4-digit pins are accepted.
     */
    @Test
    public void testUserpinValidation() {
        String username = "Ola Nordmann";
        String userpin = String.valueOf(ThreadLocalRandom.current().nextInt(100, 999+1));

        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {new User(username, userpin);});

        String invalidPin1 = String.valueOf(ThreadLocalRandom.current().nextInt(10000, 99999+1));
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {new User(username, invalidPin1);});

        String invalidPin2 = "abcd";
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {new User(username, invalidPin2);});

        String invalidPin3 = "abcdefg";
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {new User(username, invalidPin3);});

        String invalidPin4 = "a2cd";
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {new User(username, invalidPin4);});
    }

    /**
     * Add a diary to the user and retrieve the diary, both as HashMap and check that its
     * contained within all the diaries.
     */
    @Test
    public void testGetDiary() {
        String testDiaryName = "TestDiary";
        String testEntryContent = "Test123";
        String testEntryDate = "10-10-2010";

        User user = new User("BobTheBuilder", "1234");
        Entry newEntry1 = new Entry(testEntryContent, testEntryDate);
        user.setEntryInDiary(testDiaryName, newEntry1);

        Assertions.assertTrue(user.getAllDiaries().containsKey(testDiaryName));

        HashMap<String, Entry> specificDiary = user.getDiary(testDiaryName);
        Assertions.assertTrue(specificDiary.size() == 1);
        Assertions.assertTrue(specificDiary.containsKey(testEntryDate));
        Assertions.assertTrue(specificDiary.containsValue(newEntry1));
    }

    /**
     * Test that the user class can accept two entry classes, and then retrieve one of them.
     * Make sure that the retrieved entry matches in content and date, but is not the same object.
     */
    @Test
    public void testGetEntry() {
        String testDiaryName = "TestDiary";
        String testEntryContent1 = "Test123";
        String testEntryDate1 = "10-10-2010";
        Entry entry1 = new Entry(testEntryContent1, testEntryDate1);

        String testEntryContent2 = "Test123";
        String testEntryDate2 = "20-10-2010";
        Entry entry2 = new Entry(testEntryContent2, testEntryDate2);

        User user = new User("BobTheBuilder", "9876");
        user.setEntryInDiary(testDiaryName, entry1);
        user.setEntryInDiary(testDiaryName, entry2);

        Assertions.assertTrue(user.getDiary(testDiaryName).size() == 2);

        Entry retrievedEntry = user.getEntryByDate(testDiaryName, testEntryDate2);
        // Make sure the retrieved Entry is not the original object.
        Assertions.assertNotEquals(retrievedEntry, entry2);

        Assertions.assertEquals(retrievedEntry.getContent(), entry2.getContent());
        Assertions.assertEquals(retrievedEntry.getDate(), entry2.getDate());
    }

    @Test
    public void testMakeEmptyHashMapIfNoneFound() {
        String testUserName = "Jimmy";
        String testUserPin = "1234";


        User user = new User(testUserName, testUserPin);
        Assertions.assertTrue(user.getAllDiaries().isEmpty());
    }

    /**
     * Test that the updateUserEntires method actually retrieve new entries.
     */
    @Test
    public void testUpdateUserEntries() {
        String testUserName = "SomeGuy";
        String testUserPin = "1234";

        String testDiaryName = "TestDiary";
        String testEntryContent1 = "Test123";
        String testEntryDate1 = "10-10-2010";
        Entry entry1 = new Entry(testEntryContent1, testEntryDate1);

        String testEntryContent2 = "Test123";
        String testEntryDate2 = "20-10-2010";
        Entry entry2 = new Entry(testEntryContent2, testEntryDate2);

        User user = new User(testUserName, testUserPin);
        user.setEntryInDiary(testDiaryName, entry1);
        Assertions.assertTrue(user.getDiary(testDiaryName).size() == 1);
        try {
            EntryToJSON.write(user, testDiaryName, entry1);
            EntryToJSON.write(user, testDiaryName, entry2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        user.updateUserDiaries();
        Assertions.assertTrue(user.getDiary(testDiaryName).size() == 2);
        PersistanceUtil.getJsonFile(user, testDiaryName).delete();
    }

    @Test
    public void testGettersWhenEmptyUser() {
        User emptyUser = new User("NoUserOfThisNameExists", "1234");
        String nonExistantDiaryName = "NoDiaryOfThisNameShouldExist";

        Assertions.assertTrue(emptyUser.getAllDiaries() != null);
        Assertions.assertTrue(emptyUser.getAllDiaries().size() == 0);

        Assertions.assertTrue(emptyUser.getDiary(nonExistantDiaryName) != null);
        Assertions.assertTrue(emptyUser.getDiary(nonExistantDiaryName).size() == 0);
    }
}
