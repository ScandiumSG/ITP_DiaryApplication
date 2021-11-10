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
    private Button createUserButton;

    @FXML
    private Text title;

    @FXML
    public void logIn() throws IOException {
        String name = (String) usernameField.getValue();
        String pin = pinField.getText();

        User user = new User(name, pin);
    
        DiaryController.setUser(user);
        DiaryApp.getDiaryApp().changeScene("Diary.fxml");
    }

    @FXML
    public void createUser() {
        return;
    }

    @FXML
    public void initialize() {
        updateUserList();
    }

    private void updateUserList() {
        String[] diaryNames = RetrieveDiaries.getDiaryNames();

        for (String name : diaryNames) {
            if (!name.contains("+")) {
                continue;
            }
            name = name.substring(0, name.indexOf("+"));
            name = name.replace("_", " ");
            if (usernameField.getItems().contains(name)) {
                continue;
            }
            usernameField.getItems().add(name);
        }
    }

    private boolean userExists(String userName) {
        if (usernameField.getItems() == null || usernameField.getItems().isEmpty()) {
            return false;
        }

        for (String name : usernameField.getItems()) {
            if (name.equals(userName)) {
                return true;
            }
        }
        return false;
    }
}