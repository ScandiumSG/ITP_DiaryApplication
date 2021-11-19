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
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;


public class DiaryControllerTest extends ApplicationTest{

    private DiaryController diaryController;
    private Parent diaryPane;
    private User user;

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

    private void deleteTestFilesIfExists() {
        File file = new File(PersistancePaths.makeResourcesPathString(user, "TestUser's diary"));
        file.delete();
        File file2 = new File(PersistancePaths.makeResourcesPathString(user, "TestUser's diary2"));
        file2.delete();
    }
 
    @BeforeAll
    public static void supportHeadless() {
        DiaryApp.supportHeadless();
    }

    private Button getLoadButton() {
        return (Button) diaryPane.lookup("#loadDiaryButton");
    }

    private TextArea getTextArea() {
        return (TextArea) diaryPane.lookup("#textEntry");
    }

    @SuppressWarnings("unchecked")
    private ComboBox<String> getTitleField() {        
        return (ComboBox<String>) diaryPane.lookup("#title");
    }

    private Button getSaveButton() {
        return (Button) diaryPane.lookup("#entrySubmit");
    }

    private DatePicker getDatePicker() {
        return (DatePicker) diaryPane.lookup("#dateInput");
    }

    private Button getRightArrow() {
        return (Button) diaryPane.lookup("#rightButton");
    }

    private Button getLeftArrow() {
        return (Button) diaryPane.lookup("#leftButton");
    }

    private FxRobotInterface tripleClick(Node node) {
        return clickOn(node).clickOn(node).clickOn(node);
    }

    @Test
    public void testController() {
        assertNotNull(this.diaryController);
    }

    @Test
    public void testInitialValues() {
        assertEquals("TestUser's diary", getTitleField().getValue());
        assertEquals("", getTextArea().getText());
    }

    
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
}