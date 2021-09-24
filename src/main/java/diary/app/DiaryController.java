package diary.app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;

import diary.core.Entry;
import diary.json.EntryFromJSON;
import diary.json.EntryToJSON;

public class DiaryController {

    private String activeUser;

    @FXML
    private TextArea textEntry;

    @FXML
    private Label dateId;

    @FXML
    private Button entrySubmit;

    @FXML
    private DatePicker dateInput;

    /**
     * Initializes the diary to display todays date
     */
    @FXML
    public void initialize() {
        Entry entry = EntryFromJSON.read(this.activeUser, Entry.parseCurrentTime());
        updateGraphics(entry);
    }

    /**
     * Sets the active user to "Ola" TODO implement a way to change between users
     */
    public DiaryController() {
        this.activeUser = "Ola";
    }

    /**
     * Saves the current page context as a json entry
     */
    @FXML
    public void saveDateEntry() {
        Entry entry = new Entry(activeUser, getText(), getDate());

        try {
            EntryToJSON.write(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the page context using the values linked to the currently selected
     * date on dateInput
     */
    @FXML
    public void retrieveDateEntry() {
        if (dateInput.getValue() != null) {
            String date = dateFormatConverter(dateInput.getValue().toString());

            Entry entry = EntryFromJSON.read(this.activeUser, date);

            if (entry == null) {
                entry = new Entry(this.activeUser, "", date);
            }
            updateGraphics(entry);
        }
    }

    /**
     * Converts datestring from yyyy-mm-dd to dd-mm-yyyy format
     */
    private String dateFormatConverter(String date) {
        String[] dates = date.split("-");

        return dates[2] + "-" + dates[1] + "-" + dates[0];
    }

    /**
     * @return the currently displayed text
     */
    private String getText() {
        return textEntry.getText();
    }

    /**
     * @return the currently displayed date
     */
    private String getDate() {
        String[] datelabel = dateId.getText().split(" ");

        if (datelabel.length < 3) {
            throw new IllegalStateException("Something went wrong while accessing the current date");
        }

        return datelabel[2];
    }

    /**
     * Sets the context of the diary page to match a given entry
     * 
     * @param entry The entry to show
     */
    private void updateGraphics(Entry entry) {
        dateId.setText("Current date: " + entry.getDate());
        textEntry.setText(entry.getContent());
    }
}
