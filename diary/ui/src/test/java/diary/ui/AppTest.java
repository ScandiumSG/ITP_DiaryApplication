package diary.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;

/**
 * This test should run and start the main app class. 
 * The intention with the test is to make sure the 
 * DairyApp.start method functions as intended.
*/

public class AppTest extends ApplicationTest {
    private static DiaryApp app;
    private Stage stage;

    @Override
    public void start(final Stage stage) throws Exception {
        app= new DiaryApp();
        this.stage=stage;
        app.start(stage);

    }

    /**
     * Enables the possibility for headless testing in AppTest
     */
    @BeforeAll
    public static void supportHeadless() {
        DiaryApp.supportHeadless();
    }

    /**
     * Makes sure the app starts on the loginScene
     */
    @Test
    public void testLoginScene() {
        assertEquals(app.getLoginScene(), stage.getScene());
    }

    /**
     * Makes sure the app lets you log in
     */
    @Test
    public void testDiaryScene() {
        clickOn(stage.getScene().getRoot().lookup("#usernameField")).write("testUser");
        clickOn(stage.getScene().getRoot().lookup("#pinField")).write("1111");

        clickOn(stage.getScene().getRoot().lookup("#loginButton"));

        assertEquals(app.getDiaryScene(), stage.getScene());
    }
}