package diary.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EntryTest {

    /**
     * Test that created entries return correct values when calling its getter methods,
     * getContent and getDate.
     */
    @Test
    public void testContent() {
        String content = new String("Dette er en teststreng.");
        Entry entry = new Entry(content);
        Assertions.assertEquals(content, entry.getContent());

        Entry validDate = new Entry ("TestContent", "10-01-2001");
        Assertions.assertTrue(validDate instanceof Entry);
        Assertions.assertEquals("TestContent", validDate.getContent());
        Assertions.assertEquals("10-01-2001", validDate.getDate());
    }

    /**
     * Test that the provided timestamp on Entry constructor actually sets the
     * correct date.
     */
    @Test
    public void testTimestamp() {
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime currentTime = LocalDateTime.now();
        String thisTime = currentTime.format(dft);

        Entry entry = new Entry("content", thisTime);
        Assertions.assertEquals(thisTime, entry.getDate());
    }

    /**
     * Test that IllegalArgumentException is thrown when invalid day is provided.
     */
    @Test
    public void testInvalidDayValidation() {
        // 3 numbers in day
        String invalidDateFormat1 = "201-10-2020";
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {new Entry("TestContent", invalidDateFormat1);});
    }

    /**
     * Test that IllegalArgumentException is thrown when invalid month is provided.
     * Both 3-digit month numeral as well as letter month abbreviations.
     */
    @Test
    public void testInvalidMonthValidation() {
        // 3 numbers in month
        String invalidDateFormat3 = "20-109-2020";
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {new Entry("TestContent", invalidDateFormat3);});

        // Letter/written month
        String invalidDateFormat4 = "20-AUG-2020";
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {new Entry("TestContent", invalidDateFormat4);});
    }

    /**
     * Test that IllegalArgumentException is thrown when invalid year is provided.
     */
    @Test
    public void testInvalidYearValidation() {
        // 5 numbers in year
        String invalidDateFormat5 = "20-10-20202";
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {new Entry("TestContent", invalidDateFormat5);});

        // Letter instead of 1995
        String invalidDateFormat6 = "20-10-nineteennintyfive";
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {new Entry("TestContent", invalidDateFormat6);});
    }
}
