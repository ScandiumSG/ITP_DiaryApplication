package diary.ui;

import diary.core.User;
import diary.json.RetrieveDiaries;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {

    public Scene diaryScene;
    public DiaryController diaryController;

    private boolean isTesting;

    @FXML
    private Pane pane;

    @FXML
    private ComboBox<String> usernameField;

    @FXML
    private PasswordField pinField;

    @FXML
    private Button loginButton;

    @FXML
    private Text title;

    /**
     * Setter for the diary scene
     * @param scene the scene to use
     */
    public void setDiaryScene(Scene scene) {
        diaryScene = scene;
    }

    /**
     * Setter for the diary controller
     * @param controller the controller to use
     */
    public void setDiaryController(DiaryController controller) {
        diaryController = controller;
    }

    /**
     * Runs whenever the scene is opened
     *
     * <p>Updates the list of registered usernames
     * @throws IOException If local directory is not found
     */
    @FXML
    public void initialize() throws IOException {
        updateUserList();
    }

    /**
     * Handles the actions after the user press the login button.
     *
     * <p>Takes the current userName field and pin field values and makes a
     * new user, the user is then set as current user and the scene is swapped
     * to the "Diary.fxml" scene.
     * @throws IOException If an error occur during loading of the new scene.
     */
    @FXML
    public void logIn() throws IOException, IllegalArgumentException {
        try {
            String name = usernameField.getValue();
            String pin = pinField.getText();
            User user = new User(name, pin);
            diaryController.openNewUser(user);
            usernameField.setValue("");
            pinField.clear();
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.setScene(diaryScene);
        } catch (Exception e) {
            if (!isTesting) {
                System.out.println("invalid username or password");
            }
        }
    }

    /**
     * Looks for users with regisered diaries and adds their names to the login dropdown menu
     * Only adds names not already registered
     * @throws IOException If local directory is not found
     */
    public void updateUserList() throws IOException {
        String[] diaryNames = RetrieveDiaries.getAllLocalDiaries();

        for (String name : diaryNames) {
            if (!name.contains("+")) {
                continue;
            }
            name = name.substring(0, name.indexOf("+")).replace("_", " ");
            if (usernameField.getItems().contains(name)) {
                continue;
            }
            usernameField.getItems().add(name);
        }
    }

    /**
     * Tells the application that tests are being run.
     * Only affects whether the application prints to console when login fails.
     */
    public void setTesting() {
        isTesting = true;
    }
}
