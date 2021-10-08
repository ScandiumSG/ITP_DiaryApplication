package diary.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EntryTest {

    @Test
    public void testContent() {
        String content = new String("Dette er en teststreng.");
        Entry entry = new Entry(content);

        Assertions.assertEquals(content, entry.getContent());
    }

    @Test
    public void testTimestamp() {
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime currentTime = LocalDateTime.now();
        String thisTime = currentTime.format(dft);

        Entry entry = new Entry("content", thisTime);
        Assertions.assertEquals(thisTime, entry.getDate());
    }
}
