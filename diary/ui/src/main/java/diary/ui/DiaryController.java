package diary.ui;

import diary.core.Entry;
import diary.core.User;
import diary.frontend.Client;
import diary.json.EntryFromJSON;
import diary.json.EntryToJSON;
import diary.json.RetrieveDiaries;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class DiaryController {

    public Scene loginScene;

    public LoginController loginController;

    private User user;

    @FXML
    private Pane pane;

    @FXML
    private TextArea textEntry;

    @FXML
    private Label dateId;

    @FXML
    private ComboBox<String> title;

    @FXML
    private DatePicker dateInput;

    @FXML
    private Button entrySubmit;

    @FXML
    private Button leftButton;

    @FXML
    private Button rightButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button loadDiaryButton;

    /**
     * Sets the DatePickers date format and initializes the diary to display todays
     * date.
     * If the user has several diaries, it loads the top item in the dropdown menu
     */
    @FXML
    public void initialize() {
        setDateConverter();
    }

    /**
     * Updates the diary context based on selected user, diary, and date.
     */
    @FXML
    private void updateGraphics() {
        if (title.getValue() == null) {
            return;
        }

        Entry entry = EntryFromJSON.read(user, title.getValue(), getDateInput());

        if (entry == null) {
            entry = new Entry("", getDateInput());
        }

        dateId.setText("Current date: " + entry.getDate());
        textEntry.setText(entry.getContent());
    }

    /**
     * Saves the current page context as a json entry.
     * If this creates a new diary, it is added to the dropdown menu
     */
    @FXML
    public void saveDateEntry() {
        Entry entry = new Entry(textEntry.getText(), getDateInput());

        try {
            EntryToJSON.write(user, title.getValue(), entry);
            if (!title.getItems().contains(title.getValue())) {
                title.getItems().add(title.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Client.postDiary(user.getUserID() + "+" + title.getValue());
    }

    /**
     * Retrieves and displays the entry for the day before the currently
     * selected date.
     */
    @FXML
    public void getPreviousDate() {
        incrementDate(-1);
    }

    /**
     * Retrieves and displays the entry for the day after the currently
     * selected date.
     */
    @FXML
    public void getNextDate() {
        incrementDate(1);
    }

    /**
     * Changes active scene back to the login screen.
    */
    @FXML
    public void logout() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(loginScene);
        loginController.updateUserList();
    }

    /**
     * Stores the login scene to enable switching back to it
     * 
     * @param scene the javafx scene to load
     */
    public void setLoginScene(Scene scene) {
        loginScene = scene;
    }

    public void setLoginController(LoginController controller) {
        loginController = controller;
    }

    /**
     * Sets the active user.
     * 
     * @param user The user to set.
     */
    public void openNewUser(User user) {
        this.user = user;

        //SendGET to retrieve all user's diaries from server
        Client.getDiaries(user.getUserID());

        updateDiaryList();

        setDatePickerValue(Entry.parseCurrentTime());

        updateGraphics();
    }

    /**
     * Fills the dropdown menu with registered diaries and selects the first item if it exists      
     */
    private void updateDiaryList() {
        try {
            title.getItems().clear();

            HashMap<String, List<Entry>> diaries = RetrieveDiaries.findDiaries(user);
            for (String name : diaries.keySet()) {
                title.getItems().add(name);
            }

            title.getSelectionModel().selectFirst();

            if (title.getValue() == null) {
                title.setValue(user.getUserName() + "'s diary");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException f)  {

        }
    }

    /**
     * Updates the page to show a date thats offset from curretly selected date by increment.
     * 
     * @param increment how many pages to move.
     */
    private void incrementDate(int increment) {
        String date = getDateInput();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(formatter.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.add(Calendar.DATE, increment);

        setDatePickerValue(formatter.format(calendar.getTime()));
        updateGraphics();
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
     * Updates the datepicker to only accept input date on the format "dd-MM-yyyy".
     * Datepicker uses the windows system date format by default, which can be
     * confusing if you're switching between different systems.
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

    /**
     * Sets the date value displayed on the datepicker.
     * 
     * @param date The date to display. A String in the format dd-MM-yyyy
     */
    private void setDatePickerValue(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate localDate = LocalDate.parse(date, formatter);

        dateInput.setValue(localDate);
    }
}
