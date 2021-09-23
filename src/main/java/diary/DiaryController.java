package diary;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

import diary.core.Entry;
import diary.json.EntryFromJSON;
import diary.json.EntryToJSON;
import javafx.event.ActionEvent;

public class DiaryController {

    private Entry activeEntry;

    private String activeUser = "Per";

    @FXML
    private TextField textEntry;

    @FXML
    private Label dateId;

    @FXML
    private Button dateSubmit;

    @FXML
    private Button entrySubmit;

    @FXML
    private DatePicker dateInput;

    /**
     * Saves the current page context as a json entry
     * 
     * @param event (tror ikke denne trengs)
     */
    @FXML
    public void saveDateEntry(ActionEvent event) {
        EntryToJSON write = new EntryToJSON();
        Entry entry = new Entry(activeUser, getText(), getDate());

        try {
            write.write(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the page context using the values linked to the currently selected
     * date on dateInput
     * 
     * @param event (tror ikke denne trengs)
     */
    @FXML
    public void retrieveDateEntry(ActionEvent event) {
        EntryFromJSON fetch = new EntryFromJSON();
        String date = dateInput.getValue().toString();

        List<Entry> entries = null;

        try {
            entries = fetch.read(activeUser);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        for (Entry entry : entries) {
            if (entry.getDate() == date) {
                updateGraphics(entry);
                break;
            }
        }
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

        if (datelabel.length < 2) {
            throw new IllegalStateException("Something went wrong while accessing the current date");
        }

        return datelabel[1];
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
