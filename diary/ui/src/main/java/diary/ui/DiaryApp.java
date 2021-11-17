package diary.ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DiaryApp extends Application {


    @Override
    public final void start(final Stage stage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader(
            this.getClass().getResource("Login.fxml"));
        Parent loginPane = loginLoader.load();
        Scene loginScene = new Scene(loginPane);

        FXMLLoader diaryLoader = new FXMLLoader(
            this.getClass().getResource("Diary.fxml"));
        Parent diaryPane = diaryLoader.load();
        Scene diaryScene = new Scene(diaryPane);

        LoginController loginController = (LoginController) loginLoader.getController();
        loginController.setDiaryScene(diaryScene);

        DiaryController diaryController = (DiaryController) diaryLoader.getController();
        diaryController.setLoginScene(loginScene);

        loginController.setDiaryController(diaryController);
        diaryController.setLoginController(loginController);

        stage.setTitle("Diary - Login");
        stage.setScene(loginScene);

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
}