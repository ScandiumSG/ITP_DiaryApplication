package diary.core;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import diary.json.EntryToJSON;

public class EntrySearchTest {
    private String fileName;
    private File Testfile;
    private User user = new User("TestPerson", "4252");

    @BeforeEach
    public void makeFile() throws IOException {
        String fileName = "SearchTest";
        Testfile = EntryToJSON.getJsonFile(user, fileName);
        this.fileName = fileName;
        Entry entry1 = new Entry("potato tomato", "01-01-2011");
        EntryToJSON.write(user, fileName, entry1);
        Entry entry2 = new Entry("potato tomato tornado", "02-01-2011");
        EntryToJSON.write(user, fileName, entry2);
        Entry entry3 = new Entry("tomato", "03-01-2011");
        EntryToJSON.write(user, fileName, entry3);
        Entry entry4 = new Entry("tomato tornado tobago", "04-01-2011");
        EntryToJSON.write(user, fileName, entry4);
    }

    @Test
    public void singleKeywordTest() throws IOException {
        String keyword1 = "tomato";
        String keyword2 = "potato";
        String keyword3 = "tornado";
        String keyword4 = "	cato";

        Assertions.assertTrue(
            EntrySearch.searchEntries(
                user, fileName, keyword1).size() == 4);
        Assertions.assertTrue(
            EntrySearch.searchEntries(
                user, fileName, keyword1, keyword2).size() == 2);
        Assertions.assertTrue(
            EntrySearch.searchEntries(
                user, fileName, keyword1, keyword2, keyword3).size() == 3);
        Assertions.assertTrue(
            EntrySearch.searchEntries(
                user, fileName, keyword4).size() == 0);
    }

    @AfterEach
    public void deleteFile() {
        Testfile.delete();
    }
}
