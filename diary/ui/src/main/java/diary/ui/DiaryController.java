package diary.ui;

import diary.core.Entry;
import diary.core.EntrySearch;
import diary.core.User;
import diary.frontend.Client;
import diary.json.EntryToJSON;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class DiaryController {

    public Scene loginScene;

    public LoginController loginController;

    private User user;

    private boolean isTesting;

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

    @FXML
    private Button searchButton;

    @FXML
    private Pane searchBackground;

    @FXML
    private TableView<Entry> entryTable;

    @FXML
    private TableColumn<Entry, String> dateColumn;

    @FXML
    private TableColumn<Entry, String> entryColumn;

    @FXML
    private TextField searchField;

    @FXML
    private Button entrySearchButton;

    @FXML
    private Button closeSearchButton;

    @FXML
    private Button openEntryButton;

    /**
     * Sets the DatePickers date format.
     * Creates cellvalue facories for the TableView.
     */
    @FXML
    public void initialize() {
        setDateConverter();

        dateColumn.setCellValueFactory(new PropertyValueFactory<Entry, String>("Date"));
        entryColumn.setCellValueFactory(new PropertyValueFactory<Entry, String>("Content"));
    }

    /**
     * Turns the entrySearch window on and off.
     * Also clears the TableViews items.
     *
     * <p>Runs when:
     *  the searchButton is clicked,
     *  the user clicks outside the entrySearch window,
     *  the closeSearchButton is clicked.
     */
    @FXML
    public void toggleEntrySearch() {
        searchBackground.setVisible(!searchBackground.isVisible());
        searchBackground.setDisable(!searchBackground.isDisabled());
        entryTable.setItems(null);
    }

    /**
     * Opens the entry currently selected on the TableView.
     * Does nothing if no elements are selected.
     *
     * <p>Runs when:
     *  the openEntryButton is clicked.
     */
    @FXML
    public void openSelectedEntry() {
        if (entryTable.getSelectionModel().getSelectedItem() != null) {
            String date = entryTable.getSelectionModel().getSelectedItem().getDate();
            setDatePickerValue(date);
            updateGraphics();
            toggleEntrySearch();
        }
    }

    /**
     * Fills the TableView with entries belonging to the current diary
     * if it matches the search terms.
     *
     * <p>Runs when:
     *  the entrySearchButton is clicked.
     */
    @FXML
    public void searchEntries() {
        ObservableList<Entry> data = FXCollections.<Entry>observableArrayList();
        try {
            List<Entry> entries = EntrySearch.searchEntries(
                user, title.getValue(), Arrays.asList(searchField.getText().split(" ")));
            data.addAll(entries);
        } catch (IOException e) {
            e.printStackTrace();
        }

        entryTable.setItems(data);
    }

    /**
     * Runs openSelectedEntry if the event registers a double click.
     *
     * <p>Runs when:
     *  tableView is clicked.
     *
     * @param event the ui mouse event.
     */
    @FXML
    public void registerTableClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            openSelectedEntry();
        }
    }

    /**
     * Updates the diary context based on selected user, diary, and date.
     */
    @FXML
    private void updateGraphics() {
        if (title.getValue() == null) {
            return;
        }
        Entry entry = user.getEntryByDate(title.getValue(), getDateInput());
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
            // Pushing entry to both user and update the written file with EntryToJSON
            user.setEntryInDiary(title.getValue(), entry);
            EntryToJSON.write(user, title.getValue(), entry);
            if (!title.getItems().contains(title.getValue())) {
                title.getItems().add(title.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!isTesting) {
            Client.postDiary(user.getUserID() + "+" + title.getValue().replace(" ", "_"));
        }
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
     * Gets the amount of items in the TableViewer.
     * @return the amount of items in the table.
     */
    public int getTableItemAmount() {
        return entryTable.getItems().size();
    }

    /**
     * Tells the application know that tests are getting run.
     * Prevents the application from sending entries to the server.
     */
    public void setTesting() {
        this.isTesting = true;
    }

    /**
     * Stores the login scene to enable switching back to it
     *
     * @param scene the javafx scene to load
     */
    public void setLoginScene(Scene scene) {
        loginScene = scene;
    }

    /**
     * Setter for the loginController.
     * @param controller the controller to use.
     */
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

        if (!isTesting) {
            //SendGET to retrieve all user's diaries from server
            Client.getDiaries(user.getUserID());
        }
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
            title.setValue(null);
            HashMap<String, HashMap<String, Entry>> diaries = user.getAllDiaries();
            if (diaries.isEmpty()) {
                title.setValue(user.getUserName() + "'s diary");
                return;
            }
            for (String name : diaries.keySet()) {
                title.getItems().add(name);
            }
            title.getSelectionModel().selectFirst();

        } catch (NullPointerException f)  {
            f.printStackTrace();
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
