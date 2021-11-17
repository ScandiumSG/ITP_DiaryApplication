package diary.ui;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.junit.jupiter.api.BeforeAll;

import diary.core.User;
import diary.json.PersistancePaths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;


public class DiaryControllerTest extends ApplicationTest{

    private DiaryController diaryController;
    private Parent diaryPane;
    private Stage stage;
    private User user;

    @Override
    public void start(final Stage stage) throws Exception {
        this.stage = stage;

        FXMLLoader diaryLoader = new FXMLLoader(
        this.getClass().getResource("Diary.fxml"));
        diaryPane = diaryLoader.load();
        Scene diaryScene = new Scene(diaryPane);

        diaryController = (DiaryController) diaryLoader.getController();
        user = new User("TestUser", "1111");
        diaryController.openNewUser(user);

        stage.setTitle("Diary");
        stage.setScene(diaryScene);
        stage.show();
    }
 
    @BeforeAll
    public static void supportHeadless() {
        DiaryApp.supportHeadless();
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

    @Test
    public void testController() {
        assertNotNull(this.diaryController);
    }

    @Test
    public void testInitialValues() {
        assertEquals("TestUser's diary", getTitleField().getValue());
        assertEquals("", getTextArea().getText());
    }

    /*
    @Test
    public void testSaveAndDatePicker() {
        clickOn(getTextArea()).write("todays date");
        clickOn(getSaveButton());

        clickOn(getDatePicker().getEditor()).write("10-11-2021"+"\n");
        assertEquals("", getTextArea().getText());

        clickOn(getDatePicker().getEditor()).write(Entry.parseCurrentTime() + "\n");
        assertEquals("todays date", getTextArea().getText());

        File file = new File(PersistancePaths.makeResourcesPathString(user, getTitleField().getValue()));
        file.delete();
    }
    */
}