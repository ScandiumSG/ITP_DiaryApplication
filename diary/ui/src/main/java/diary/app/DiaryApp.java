package diary.app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DiaryApp extends Application {

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
     * @param args
     */
    public static void main(final String[] args) {
        launch();
    }
}
