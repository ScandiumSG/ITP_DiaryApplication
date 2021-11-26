package diary.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.junit.jupiter.api.BeforeAll;
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


/**
 * Tests for the Login fxml and its accompanying controller.
 */
public class LoginControllerTest extends ApplicationTest{

    private LoginController loginController;
    private DiaryController diaryController;

    private Stage stage;

    private Parent loginPane;
    private Scene loginScene;

    private Parent diaryPane;
    private Scene diaryScene;

    // Test users for the dropdown menu.
    private static final User testUser1 = new User("test user 1", "1111");
    private static final User testUser2 = new User("test user 2", "1111");
    private static final User testUser3 = new User("test user 3", "1111");

    /**
     * Creates scenes for both fxml files and stores each controller in the other.
     * Deletes all testfiles if they exist.
     * Tells both controllers that tests are getting run.
     */
    @Override
    public void start(final Stage stage) throws Exception{
        this.stage = stage;

        deleteTestFilesIfExists();

        FXMLLoader loginLoader = new FXMLLoader(
            this.getClass().getResource("Login.fxml"));
        loginPane = loginLoader.load();
        loginScene = new Scene(loginPane);

        FXMLLoader diaryLoader = new FXMLLoader(
            this.getClass().getResource("Diary.fxml"));
        diaryPane = diaryLoader.load();
        diaryScene = new Scene(diaryPane);

        loginController = (LoginController) loginLoader.getController();
        loginController.setDiaryScene(diaryScene);

        diaryController = (DiaryController) diaryLoader.getController();
        diaryController.setLoginScene(loginScene);

        loginController.setDiaryController(diaryController);
        diaryController.setLoginController(loginController);

        diaryController.setTesting();
        loginController.setTesting();

        stage.setTitle("Diary - Login");
        stage.setScene(loginScene);
        stage.show();
    }

    /**
     * Enables the option for headless testing.
     * Deletes testfiles if they exist.
     */
    @BeforeAll
    public static void prepareTests(){
        DiaryApp.supportHeadless();
        deleteTestFilesIfExists();
    }

    /**
     * Gets the field for entering a username.
     * @return the usernameField ui element.
     */
    @SuppressWarnings("unchecked")
    private ComboBox<String> getUsernameField() {
        return (ComboBox<String>) loginPane.lookup("#usernameField");
    }

    /**
     * Get the field for entering a pincode.
     * @return the pinField ui element.
     */
    private PasswordField getPinField() {
        return (PasswordField) loginPane.lookup("#pinField");
    }

    /**
     * Get the button for logging in to the application.
     * @return the loginButton ui element.
     */
    private Button getLoginButton() {
        return (Button) loginPane.lookup("#loginButton");
    }

    /**
     * Deletes the testfiles created by the LoginControllerTest
     */
    private static void deleteTestFilesIfExists() {
        File file1 = new File(PersistancePaths.makeResourcesPathString(testUser1, testUser1.getUserName() +"'s diary"));
        file1.delete();
        File file2 = new File(PersistancePaths.makeResourcesPathString(testUser1, testUser1.getUserName() +"'s diary2"));
        file2.delete();
        File file3 = new File(PersistancePaths.makeResourcesPathString(testUser2, testUser2.getUserName() +"'s diary"));
        file3.delete();
        File file4 = new File(PersistancePaths.makeResourcesPathString(testUser3, testUser3.getUserName() +"'s diary"));
        file4.delete();
    }

    /**
     * Makes sure the username field is empty
     * and that the dropdown menu doesn't contain any of the testusers.
     */
    @Test
    public void testEmpty() {
        assertTrue(getUsernameField().getValue() == null);
        assertFalse(getUsernameField().getItems().contains(testUser1.getUserName()));
        assertFalse(getUsernameField().getItems().contains(testUser2.getUserName()));
        assertFalse(getUsernameField().getItems().contains(testUser3.getUserName()));
    }

    /**
     * Creates four diary entries belonging to three testusers.
     * It then makes sure the dropdown menu contains them.
     */
    @Test
    public void testFilled() {
        Entry testEntry1 = new Entry("Aliquam ligula tortor, viverra a.", Entry.parseCurrentTime());
        Entry testEntry2 = new Entry("Sed vel scelerisque neque. Sed.", Entry.parseCurrentTime());
        Entry testEntry3 = new Entry("Lorem ipsum dolor sit amet.", Entry.parseCurrentTime());
        Entry testEntry4 = new Entry("Ut at ligula nec est.", Entry.parseCurrentTime());

        try {
            EntryToJSON.write(testUser1, testUser1.getUserName() +"'s diary", testEntry1);
            EntryToJSON.write(testUser1, testUser1.getUserName() +"'s diary2", testEntry2);
            EntryToJSON.write(testUser2, testUser2.getUserName() +"'s diary", testEntry3);
            EntryToJSON.write(testUser3, testUser3.getUserName() +"'s diary", testEntry4);

            loginController.updateUserList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(getUsernameField().getItems().contains(testUser1.getUserName()));
        assertTrue(getUsernameField().getItems().contains(testUser2.getUserName()));
        assertTrue(getUsernameField().getItems().contains(testUser3.getUserName()));
    }

    /**
     * Makes sure login doesn't work without a username.
     */
    @Test
    public void testEmptyName() {

        clickOn(getPinField()).write("1111");
        clickOn(getLoginButton());

        assertEquals(loginScene, stage.getScene());
    }

    /**
     * Makes sure login doesn't work without a pin.
     */
    @Test
    public void testEmptyPin() {

        clickOn(getUsernameField()).write("testuser1");
        clickOn(getLoginButton());

        assertEquals(loginScene, stage.getScene());
    }

    /**
     * Makes sure login doesn't work with letters in pin.
     */
    @Test
    public void testLettersInPin() {
        clickOn(getUsernameField()).write("testuser2");
        clickOn(getPinField()).write("invalid pin");
        clickOn(getLoginButton());
        assertEquals(loginScene, stage.getScene());
    }

    /**
     * Makes sure login doesn't work with pin length under four.
     */
    @Test
    public void testShortPin() {
        clickOn(getUsernameField()).write("testuser3");
        clickOn(getPinField()).write("111");
        clickOn(getLoginButton());
        assertEquals(loginScene, stage.getScene());
    }

    /**
     * Makes sure login doesn't work with pin length over four.
     */
    @Test
    public void testLongPin() {
        clickOn(getUsernameField()).write("testuser4");
        clickOn(getPinField()).write("11111");
        clickOn(getLoginButton());
        assertEquals(loginScene, stage.getScene());
    }

    /**
     * Makes sure login button works.
     * Also makes sure the logout button in the main application works.
     */
    @Test
    public void testLoginAndLogout() {

        clickOn(getUsernameField()).write("testuser5");
        clickOn(getPinField()).write("1111");

        clickOn(getLoginButton());

        assertEquals(diaryScene, stage.getScene());

        Button logoutButton = (Button) diaryPane.lookup("#logoutButton");

        clickOn(logoutButton);

        assertEquals(loginScene, stage.getScene());
    }
}
