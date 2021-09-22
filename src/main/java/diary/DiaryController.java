package diary;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class DiaryController {

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

    @FXML
    public void saveDateEntry(ActionEvent event) {
        return;
    }

    @FXML
    public void retrieveDateEntry(ActionEvent event) {
        return;
    }

}
