package diary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import diary.core.Entry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EntryTest {

    @Test
    public void testNameAndContent() {
        String user = new String("Ola Nordmann");
        String content = new String("Dette er en teststreng.");
        Entry entry = new Entry(user, content);

        Assertions.assertEquals(user, entry.getUsername());
        Assertions.assertEquals(content, entry.getContent());
    }

    @Test
    public void testTimestamp() {
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime currentTime = LocalDateTime.now();
        String thisTime = currentTime.format(dft);

        Entry entry = new Entry("Name", "content", thisTime);
        Assertions.assertEquals(thisTime, entry.getDate());
    }
}
