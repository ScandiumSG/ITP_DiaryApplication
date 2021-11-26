package diary.ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DiaryApp extends Application {

    private Scene loginScene;
    private Scene diaryScene;
    
    /**
     * Configuration of values that allows headless run of ui tests
     */

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
        FXMLLoader loginLoader = new FXMLLoader(
            this.getClass().getResource("Login.fxml"));
        Parent loginPane = loginLoader.load();
        loginScene = new Scene(loginPane);

        FXMLLoader diaryLoader = new FXMLLoader(
            this.getClass().getResource("Diary.fxml"));
        Parent diaryPane = diaryLoader.load();
        diaryScene = new Scene(diaryPane);

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

    public Scene getLoginScene() {
        return loginScene;
    }

    public Scene getDiaryScene() {
        return diaryScene;
    }

    /**
     * Lauch method for DiaryApp.
     * @param args No input parameters
     */
    public static void main(final String[] args) {
        launch();
    }
}