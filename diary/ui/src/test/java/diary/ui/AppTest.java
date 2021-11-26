package diary.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;

/**
 * This test should run and start the main app class. 
 * The intention with the test is to make sure the app actually runs and not stays freezed when launcing.
 * Even though this practically is tested in both diaryController test and loginController test.
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

    @BeforeAll
    public static void supportHeadless() {
        DiaryApp.supportHeadless();
    }

    @Test
    public void testLoginScene() {
        assertEquals(app.getLoginScene(), stage.getScene());
    }

    @Test
    public void testDiaryScene() {
        assertNotNull(stage);
    }

}