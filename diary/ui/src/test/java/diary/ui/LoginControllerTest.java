package diary.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import diary.core.Entry;
import diary.core.User;
import diary.json.EntryToJSON;
import diary.json.PersistancePaths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;


public class LoginControllerTest extends ApplicationTest{
    private LoginController loginController;
    private DiaryController diaryController;

    private Stage stage;

    private Parent loginPane;

    @Override
    public void start(final Stage stage) throws Exception{
        this.stage = stage;

        FXMLLoader loginLoader = new FXMLLoader(
            this.getClass().getResource("Login.fxml"));
        loginPane = loginLoader.load();
        Scene loginScene = new Scene(loginPane);

        FXMLLoader diaryLoader = new FXMLLoader(
            this.getClass().getResource("Diary.fxml"));
        Parent diaryPane = diaryLoader.load();
        Scene diaryScene = new Scene(diaryPane);

        loginController = (LoginController) loginLoader.getController();
        loginController.setDiaryScene(diaryScene);

        diaryController = (DiaryController) diaryLoader.getController();
        diaryController.setLoginScene(loginScene);

        loginController.setDiaryController(diaryController);
        diaryController.setLoginController(loginController);

        stage.setTitle("Diary - Login");
        stage.setScene(loginScene);
        stage.show();
    }

    @Test
    public void testController() {
        assertNotNull(this.loginController);
        assertNotNull(this.diaryController);
    }


    @Test
    public void testEmpty() {
        @SuppressWarnings("unchecked")
        ComboBox<String> usernameField = (ComboBox<String>) loginPane.lookup("#usernameField");
        assertTrue(usernameField.getValue() == null);
        assertTrue(usernameField.getItems().isEmpty());
    }

    @Test
    public void testFilled() {
        User testUser1 = new User("test user 1", "1111");
        Entry testEntry1 = new Entry("Aliquam ligula tortor, viverra a.", Entry.parseCurrentTime());
        Entry testEntry2 = new Entry("Sed vel scelerisque neque. Sed.", Entry.parseCurrentTime());

        User testUser2 = new User("test user 2", "1111");
        Entry testEntry3 = new Entry("Lorem ipsum dolor sit amet.", Entry.parseCurrentTime());

        User testUser3 = new User("test user 3", "1111");
        Entry testEntry4 = new Entry("Ut at ligula nec est.", Entry.parseCurrentTime());
        
        try {
            EntryToJSON.write(testUser1, "testUser1's diary", testEntry1);
            EntryToJSON.write(testUser1, "testUser1's diary2", testEntry2);
            EntryToJSON.write(testUser2, "testUser2's diary", testEntry3);
            EntryToJSON.write(testUser3, "testUser3's diary", testEntry4);
        } catch (IOException e) {
            e.printStackTrace();
        }

        loginController.updateUserList();

        @SuppressWarnings("unchecked")
        ComboBox<String> usernameField = (ComboBox<String>) loginPane.lookup("#usernameField");
        assertTrue(usernameField.getItems().size() == 3);

        File file1 = new File(PersistancePaths.makeResourcesPathString(testUser1, "testUser1's diary"));
        file1.delete();
        File file2 = new File(PersistancePaths.makeResourcesPathString(testUser1, "testUser1's diary2"));
        file2.delete();
        File file3 = new File(PersistancePaths.makeResourcesPathString(testUser2, "testUser2's diary"));
        file3.delete();
        File file4 = new File(PersistancePaths.makeResourcesPathString(testUser3, "testUser3's diary"));
        file4.delete();
    }

    @Test
    public void testEmptyName() {
        PasswordField pinField = (PasswordField) loginPane.lookup("#pinField");
        Button loginButton = (Button) loginPane.lookup("#loginButton");

        clickOn(pinField).write("1111");
        clickOn(loginButton);
    }
}