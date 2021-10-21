package diary.ui;

import diary.core.Entry;
import diary.json.EntryFromJSON;
import diary.json.EntryToJSON;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import javafx.util.StringConverter;
import java.time.LocalDate;


public class DiaryController {

    @FXML
    private TextArea textEntry;

    @FXML
    private Label dateId;

    @FXML
    private Button entrySubmit;

    @FXML
    private DatePicker dateInput;

    
    /**
     * Initializes the diary to display todays date.
     * Also sets the DatePickers date format
     */
    @FXML
    public void initialize() {
        setDateConverter();
        Entry entry = EntryFromJSON.read(Entry.parseCurrentTime());
        updateGraphics(entry);
    }


    /**
     * Saves the current page context as a json entry.
     */
    @FXML
    public void saveDateEntry() {
        Entry entry = new Entry(getDisplayedText(), getChosenDate());

        try {
            EntryToJSON.write(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Updates the page context using the values linked to
     * the currently selected date on dateInput.
     */
    @FXML
    public void retrieveDateEntry() {
        if (dateInput.getValue() != null) {
            String date = dateFormatConverter(dateInput.getValue().toString());

            Entry entry = EntryFromJSON.read(date);

            if (entry == null) {
                entry = new Entry("", date);
            }
            updateGraphics(entry);
        }
    }


    /**
     * Converts datestring from yyyy-MM-dd to dd-MM-yyyy format.
     * @param date A datestring in format yyyy-MM-dd.
     * @return datestring of the dd-MM-yyyy format.
     */
    private String dateFormatConverter(final String date) {
        String[] dates = date.split("-");

        return dates[2] + "-" + dates[1] + "-" + dates[0];
    }


    /**
     * @return the currently displayed text
     */
    private String getDisplayedText() {
        return textEntry.getText();
    }


    /**
     * @return the currently displayed date
     */
    private String getChosenDate() {
        final int minDateLength = 3;
        String[] datelabel = dateId.getText().split(" ");

        if (datelabel.length < minDateLength) {
            throw new IllegalStateException(
                "Something went wrong while accessing the current date");
        }

        return datelabel[2];
    }


    /**
     * Sets the context of the diary page to match a given entry.
     * @param entry The entry to show
     */
    private void updateGraphics(final Entry entry) {
        dateId.setText("Current date: " + entry.getDate());
        textEntry.setText(entry.getContent());
    }


    /**
     * Updates the datepicker to only accept input date on the format "dd-MM-yyyy".
     * Datepicker uses the windows system date format by default,
     * which can be confusing if you're switching between different systems
     */
    private void setDateConverter(){
        dateInput.setConverter( new StringConverter<LocalDate>() {
            String pattern = "dd-MM-yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                dateInput.setPromptText(pattern.toLowerCase());
            }

            @Override public String toString(LocalDate date) {
                if (date != null){
                    return dateFormatter.format(date);
                }
                else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                }
                else {
                    return null;
                }
            }
        }
    );}
}
