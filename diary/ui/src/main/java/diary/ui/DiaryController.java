package diary.ui;

import diary.core.Entry;
import diary.core.User;
import diary.json.EntryFromJSON;
import diary.json.EntryToJSON;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

public class DiaryController {
    private static String diaryName = "diary1";
    private static User user;

    @FXML
    private TextArea textEntry;

    @FXML
    private Label dateId;

    @FXML
    private Text title;

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

    /**
     * Sets the DatePickers date format and initializes the diary to display todays
     * date.
     */
    @FXML
    public void initialize() {
        setTitleText();

        setDateConverter();
        setDatePickerValue(Entry.parseCurrentTime());
        updateGraphics(EntryFromJSON.read(user, diaryName, Entry.parseCurrentTime()));
    }

    public static void setUser(User user) {
        DiaryController.user = user;
    }

    /**
     * Saves the current page context as a json entry.
     */
    @FXML
    public void saveDateEntry() {
        Entry entry = new Entry(textEntry.getText(), getDateInput());

        try {
            EntryToJSON.write(user, diaryName, entry);
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


    @FXML
    public void getPreviousDate() {
        String date = incrementDate(getDateInput(), -1);

        setDatePickerValue(date);
        updateGraphicsByDate(date);
    }

    @FXML
    public void getNextDate() {
        String date = incrementDate(getDateInput(), 1);

        setDatePickerValue(date);
        updateGraphicsByDate(date);
    }

    @FXML
    public void logout() throws IOException {
        DiaryApp.getDiaryApp().changeScene("Login.fxml");
    }

    private void setTitleText() {
        title.setText(user.getUserName() + "'s diary");
    }

    private void updateGraphicsByDate(String date) {
        Entry entry = EntryFromJSON.read(user, diaryName, date);

        if (entry == null) {
            entry = new Entry("", date);
        }
        updateGraphics(entry);
    }

    private String incrementDate(String date, int increment) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        Calendar c = Calendar.getInstance();

        try {
            c.setTime(formatter.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        c.add(Calendar.DATE, increment);

        return formatter.format(c.getTime());
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

    private void setDatePickerValue(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      
        LocalDate localDate = LocalDate.parse(date, formatter);

        dateInput.setValue(localDate);
    }
}
