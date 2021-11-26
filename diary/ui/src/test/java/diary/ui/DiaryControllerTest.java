package diary.ui;

import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobotInterface;
import org.testfx.framework.junit5.ApplicationTest;
import org.junit.jupiter.api.BeforeAll;

import diary.core.*;
import diary.json.PersistancePaths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

/**
 * Tests for the Diary fxml file and its accompanying controller.
 */
public class DiaryControllerTest extends ApplicationTest{

    private DiaryController diaryController;
    private Parent diaryPane;
    private User user;

    /**
     * Creates a javafx stage for the Diary.fxml file.
     * It then creates a testuser for the diaryController, 
     * and tells the controller that tests are being run.
     * Deletes testfiles if they exist.
     */
    @Override
    public void start(final Stage stage) throws Exception {
        FXMLLoader diaryLoader = new FXMLLoader(
        this.getClass().getResource("Diary.fxml"));
        diaryPane = diaryLoader.load();
        Scene diaryScene = new Scene(diaryPane);

        diaryController = (DiaryController) diaryLoader.getController();
        user = new User("TestUser", "3475");

        deleteTestFilesIfExists();

        diaryController.setTesting();
        diaryController.openNewUser(user);

        stage.setTitle("Diary");
        stage.setScene(diaryScene);
        stage.show();
    }

    /**
     * Deletes files created during testing
     */
    private void deleteTestFilesIfExists() {
        File file = new File(PersistancePaths.makeResourcesPathString(user, "TestUser's diary"));
        file.delete();
        File file2 = new File(PersistancePaths.makeResourcesPathString(user, "TestUser's diary2"));
        file2.delete();
    }
 
    /**
     * Enables headless testing for robot tests
     */
    @BeforeAll
    public static void supportHeadless() {
        DiaryApp.supportHeadless();
    }

    /**
     * Gets the button to load a new diary.
     * @return the loadDiaryButton ui element.
     */
    private Button getLoadButton() {
        return (Button) diaryPane.lookup("#loadDiaryButton");
    }

    /**
     * Gets the field for typing an entry.
     * @return the textEntry ui element.
     */
    private TextArea getTextArea() {
        return (TextArea) diaryPane.lookup("#textEntry");
    }

    /**
     * Gets the field for displaying and selecting the diary.
     * @return The title UI element.
     */
    @SuppressWarnings("unchecked")
    private ComboBox<String> getTitleField() {        
        return (ComboBox<String>) diaryPane.lookup("#title");
    }

    /**
     * Gets the button which saves the current entry.
     * @return The SaveButton ui element.
     */
    private Button getSaveButton() {
        return (Button) diaryPane.lookup("#entrySubmit");
    }
    
    /**
     * Gets the ui element responsible for selecting what date to show.
     * @return The DatePicker ui element.
     */
    private DatePicker getDatePicker() {
        return (DatePicker) diaryPane.lookup("#dateInput");
    }

    /**
     * Get the button for selecting the entry on day in the future 
     * relative to currently selected entry.
     * @return The rightButton ui element.
     */
    private Button getRightArrow() {
        return (Button) diaryPane.lookup("#rightButton");
    }

    /**
     * Get the button for selecting the entry on day in the past 
     * relative to currently selected entry.
     * @return The leftButton ui element.
     */
    private Button getLeftArrow() {
        return (Button) diaryPane.lookup("#leftButton");
    }

    /**
     * Gets the button for opening the searchTable.
     * @return The searchButton ui element.
     */
    private Button getSearchButton() {
        return (Button) diaryPane.lookup("#searchButton");
    }

    /**
     * Gets the button for updating the searchTable.
     * @return The entrySearchButton ui element.
     */
    private Button getEntrySearchButton() {
        return (Button) diaryPane.lookup("#entrySearchButton");
    }

    /**
     * Gets the textField containing searchTerms.
     * @return The Searchfield ui element.
     */
    private TextField getSearchField() {
        return (TextField) diaryPane.lookup("#searchField");
    }

    /**
     * Clicks on the selected node three times in quick succession.
     * @param node The node to click.
     * @return The clicked node.
     */
    private FxRobotInterface tripleClick(Node node) {
        return clickOn(node).clickOn(node).clickOn(node);
    }

    /**
     * Tests whether inital values are as expected.
     */
    @Test
    public void testInitialValues() {
        assertEquals("TestUser's diary", getTitleField().getValue());
        assertEquals("", getTextArea().getText());
    }

    /**
     * Tests whether the datepicker functions as expected.
     */
    @Test
    public void testSaveAndDatePicker() {
        clickOn(getTextArea()).write("todays date");
        clickOn(getSaveButton());

        tripleClick(getDatePicker().getEditor()).write("10-11-2021"+"\n");
        assertEquals("", getTextArea().getText());

        tripleClick(getDatePicker().getEditor()).write(Entry.parseCurrentTime() + "\n");
        assertEquals("todays date", getTextArea().getText());

        deleteTestFilesIfExists();
    }

    /**
     * Tests whether the arrow navigation buttons function as expected.
     */
    @Test
    public void testArrowButtons() {
        clickOn(getTextArea()).write("Todays date");
        clickOn(getSaveButton());

        clickOn(getRightArrow());
        assertEquals("", getTextArea().getText());
        clickOn(getTextArea()).write("Tomorrows date");
        clickOn(getSaveButton());

        clickOn(getLeftArrow());
        assertEquals("Todays date", getTextArea().getText());

        clickOn(getRightArrow());
        assertEquals("Tomorrows date", getTextArea().getText());

        deleteTestFilesIfExists();
    }

    /**
     * Tests creating two diaries, and makes sure the dropdown menu updates accordingly.
     */
    @Test
    public void testMultipleDiaries() {
        clickOn(getTextArea()).write("Diary 1");
        clickOn(getSaveButton());

        tripleClick(getTitleField()).write("TestUser's diary2");
        clickOn(getLoadButton());

        assertEquals("", getTextArea().getText());
        clickOn(getTextArea()).write("Diary 2");
        clickOn(getSaveButton());

        tripleClick(getTitleField()).write("TestUser's diary");
        clickOn(getLoadButton());

        assertEquals("Diary 1", getTextArea().getText());

        assertTrue(getTitleField().getItems().size() == 2);

        deleteTestFilesIfExists();
    }

    /**
     * Starts by creating four entries containing names of fruits.
     * It then opens the search field and makes sure the table updates
     * correctly according to the search terms.
     */
    @Test
    public void testEntrySearch() {
        clickOn(getTextArea()).write("eple");
        clickOn(getSaveButton());

        clickOn(getRightArrow());
        clickOn(getTextArea()).write("eple banan grape");
        clickOn(getSaveButton());

        clickOn(getRightArrow());
        clickOn(getTextArea()).write("banan tomat");
        clickOn(getSaveButton());

        clickOn(getRightArrow());
        clickOn(getTextArea()).write("eple tomat");
        clickOn(getSaveButton());

        clickOn(getSearchButton());

        clickOn(getEntrySearchButton());
        assertEquals(4, diaryController.getTableItemAmount());

        tripleClick(getSearchField()).write("eple");
        clickOn(getEntrySearchButton());
        assertEquals(3, diaryController.getTableItemAmount());

        tripleClick(getSearchField()).write("banan tomat eple");
        clickOn(getEntrySearchButton());
        assertEquals(3, diaryController.getTableItemAmount());

        tripleClick(getSearchField()).write("banan tomat eple grape");
        clickOn(getEntrySearchButton());
        assertEquals(1, diaryController.getTableItemAmount());

        tripleClick(getSearchField()).write("something else");
        clickOn(getEntrySearchButton());
        assertEquals(0, diaryController.getTableItemAmount());

        deleteTestFilesIfExists();
    }
}