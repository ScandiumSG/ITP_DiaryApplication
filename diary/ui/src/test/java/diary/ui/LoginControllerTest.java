package diary.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;


public class LoginControllerTest extends ApplicationTest{
    private LoginController controller;
    private Parent root;
    private final static File testFilePath = new File("src/main/resources/DiaryEntries.json");

    @Override
    public void start(final Stage stage) throws Exception{
        DiaryApp app = new DiaryApp();

        app.start(stage);

        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        this.root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testController() {
        System.out.println("hei");
        assertNotNull(this.controller);
    }
}
