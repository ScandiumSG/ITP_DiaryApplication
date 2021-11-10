package diary.ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DiaryApp extends Application {

    private static DiaryApp diaryApp;

    private Stage stage;

    @Override
    public final void start(final Stage stage) throws IOException {
        setStage(stage);
        setDiaryApp(this);

        changeScene("Login.fxml");
    }

    /**
     * Lauch method for DiaryApp.
     * @param args No input parameters
     */
    public static void main(final String[] args) {
        launch();
    }

    public void changeScene(String sceneName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
            this.getClass().getResource(sceneName));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }

        public static DiaryApp getDiaryApp() {
        return cheatMethodWillRemove();
    }

    private static DiaryApp cheatMethodWillRemove() {
        return DiaryApp.diaryApp;
    }

    private static void setDiaryApp(final DiaryApp app) {
        diaryApp = app;
    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }
}