package diary.ui;

import diary.core.Entry;
import diary.core.User;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.StringConverter;

public class DiaryController {
    private String diaryName = "";
    private static User user;

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
        createDiaryList();
        setDateConverter();

        title.getSelectionModel().selectFirst();

        if (title.getValue().isEmpty()) {
            title.setValue(user.getUserName() + " 's diary");
        }

        loadDiary();
    }

    /**
     * Load the diary corresponding to the current content of the title field.
     * </p>The graphics is then updated with the current date and entry for the
     * current date from the chosen diary.
     */
    @FXML
    public void loadDiary() {
        diaryName = title.getValue();
        updateGraphics(EntryFromJSON.read(user, diaryName, Entry.parseCurrentTime()));
    }

    /**
     * Saves the current page context as a json entry.
     */
    @FXML
    public void saveDateEntry() {
        Entry entry = new Entry(textEntry.getText(), getDateInput());

        try {
            EntryToJSON.write(user, diaryName, entry);
            if (!title.getItems().contains(diaryName)) {
                title.getItems().add(diaryName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the page context using the values linked to the currently selected
     * date on dateInput.
     */
    @FXML
    public void getChosenDate() {
        String date = getDateInput();

        updateGraphicsByDate(date);
    }

    /**
     * Retrieves and displays the entry for the day before the currently
     * selected date.
     */
    @FXML
    public void getPreviousDate() {
        String date = incrementDate(getDateInput(), -1);

        updateGraphicsByDate(date);
    }

    /**
     * Retrieves and displays the entry for the day after the currently
     * selected date.
     */
    @FXML
    public void getNextDate() {
        String date = incrementDate(getDateInput(), 1);

        updateGraphicsByDate(date);
    }

    @FXML
    public void logout() throws IOException {
        DiaryApp.getDiaryApp().changeScene("Login.fxml");
    }

    public static void setUser(User user) {
        DiaryController.user = user;
    }

    public void setDiary(String diaryName) {
        this.diaryName = diaryName;
    }

    private void createDiaryList() {
        try {
            HashMap<String, List<Entry>> diaries = RetrieveDiaries.findDiaries(user);
            for (String name : diaries.keySet()) {
                title.getItems().add(name);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateGraphicsByDate(String date) {
        Entry entry = EntryFromJSON.read(user, diaryName, date);

        if (entry == null) {
            entry = new Entry("", date);
        }
        updateGraphics(entry);
    }

    /**
     * 
     * @param date 
     * @param increment
     * @return
     */
    private String incrementDate(String date, int increment) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(formatter.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.add(Calendar.DATE, increment);

        return formatter.format(calendar.getTime());
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
        setDatePickerValue(entry.getDate());
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

    /**
     * Sets the date value displayed on the datepicker
     * 
     * @param date The date to display. A String in the format dd-MM-yyyy
     */
    private void setDatePickerValue(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate localDate = LocalDate.parse(date, formatter);

        dateInput.setValue(localDate);
    }
}
