package diary.ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DiaryApp extends Application {

    private static DiaryApp instance;

    private Stage stage;

    @Override
    public final void start(final Stage stage) throws IOException {
        setStage(stage);
        setDiaryApp(this);

        changeScene("Login.fxml");

        // Image credit:
        // Photo by Annie Spratt - Unsplash
        // See src/main/resources/img/thumb2_credit.txt for link
        stage.getIcons().add(
            new Image(getClass().getResourceAsStream("img/thumb2.jpg")));
        stage.show();
    }

    /**
     * Lauch method for DiaryApp.
     * @param args No input parameters
     */
    public static void main(final String[] args) {
        launch();
    }

    /**
     * Method that loads in a new scene in the current stage based on provided
     * scene name.
     * @param sceneName A string of the scene that is to be displayed.
     * @throws IOException If an error occur during loading of the new scene.
     */
    public void changeScene(String sceneName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
            this.getClass().getResource(sceneName));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));

        String title = sceneName.equals("Login.fxml") ? "Diary - Login" : "Diary";
        stage.setTitle(title);
    }

    public static DiaryApp getDiaryApp() {
        return cheatMethodWillRemove();
    }

    private static DiaryApp cheatMethodWillRemove() {
        return DiaryApp.instance;
    }

    //Setter for the diaryApp
    private static void setDiaryApp(final DiaryApp app) {
        instance = app;
    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }
}
