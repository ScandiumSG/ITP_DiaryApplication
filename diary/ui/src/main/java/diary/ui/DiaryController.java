package diary.ui;

import diary.core.Entry;
import diary.core.User;
import diary.json.EntryFromJSON;
import diary.json.EntryToJSON;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.StringConverter;

public class DiaryController {
    private static final String tempDiaryName = "diary1";
    private static final User tempUser = new User("User1", "1234"); //needed int to be string in order to succed for test

    @FXML
    private TextArea textEntry;

    @FXML
    private Label dateId;

    @FXML
    private Button entrySubmit;

    @FXML
    private DatePicker dateInput;

    /**
     * Sets the DatePickers date format and initializes the diary to display todays
     * date.
     */
    @FXML
    public void initialize() {
        setDateConverter();
        updateGraphics(EntryFromJSON.read(tempUser, tempDiaryName, Entry.parseCurrentTime()));
    }

    /**
     * Saves the current page context as a json entry.
     */
    @FXML
    public void saveDateEntry() {
        Entry entry = new Entry(textEntry.getText(), getDateInput());

        try {
            EntryToJSON.write(tempUser, tempDiaryName, entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the page context using the values linked to the currently selected
     * date on dateInput.
     */
    @FXML
    public void retrieveDateEntry() {
        String date = getDateInput();

        Entry entry = EntryFromJSON.read(tempUser, tempDiaryName, date);

        if (entry == null) {
            entry = new Entry("", date);
        }
        updateGraphics(entry);
    }

    /**
     * Gets the chosen date from the datepicker. Returns todays date if the
     * datepicker is empty.
     *
     * @return Datestring on the dd-MM-yyyy format.
     */
    private String getDateInput() {
        if (dateInput.getValue() == null) {
            return Entry.parseCurrentTime();
        }

        String date = dateInput.getValue().toString();
        String[] dateSplit = date.split("-");

        return dateSplit[2] + "-" + dateSplit[1] + "-" + dateSplit[0];
    }

    /**
     * Sets the context of the diary page to match a given entry.
     *
     * @param entry The entry to show
     */
    private void updateGraphics(final Entry entry) {
        dateId.setText("Current date: " + entry.getDate());
        textEntry.setText(entry.getContent());
    }

    /**
     * Updates the datepicker to only accept input date on the format "dd-MM-yyyy".
     * Datepicker uses the windows system date format by default, which can be
     * confusing if you're switching between different systems
     */
    private void setDateConverter() {
        dateInput.setConverter(new StringConverter<LocalDate>() {
            String pattern = "dd-MM-yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                dateInput.setPromptText(pattern.toLowerCase());
            }

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }
}
