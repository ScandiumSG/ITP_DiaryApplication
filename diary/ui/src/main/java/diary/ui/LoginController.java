package diary.ui;

import diary.core.User;
import diary.json.RetrieveDiaries;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;

public class LoginController {

    @FXML
    private ComboBox<String> usernameField;

    @FXML
    private PasswordField pinField;

    @FXML
    private Button loginButton;

    @FXML
    private Text title;


    /**
     * Runs whenever the scene is opened
     * 
     * <p>Updates the list of registered usernames
     */
    @FXML
    public void initialize() {
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
    public void logIn() throws IOException {
        String name = usernameField.getValue();
        String pin = pinField.getText();

        User user = new User(name, pin);

        DiaryController.setUser(user);
        DiaryApp.getDiaryApp().changeScene("Diary.fxml");
    }

    /**
     * Looks for users with regisered diaries and adds their names to the login dropdown menu
     * Only adds names not already registered
     */
    private void updateUserList() {
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
}