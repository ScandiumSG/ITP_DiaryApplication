package diary.ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DiaryApp extends Application {

     public static void supportHeadless() {
        if (Boolean.getBoolean("headless")) {
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "true");   
        }
    }

    @Override
    public final void start(final Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
            this.getClass().getResource("Diary.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    /**
     * Lauch method for DiaryApp.
     * @param args No input parameters
     */
    public static void main(final String[] args) {
        launch();
    }
}
