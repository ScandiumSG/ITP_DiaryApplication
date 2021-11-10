package diary.ui;

import diary.core.User;
import diary.json.RetrieveDiaries;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController {

    @FXML
    private TextField usernameField;

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
        String name = usernameField.getText();
        String pin = pinField.getText();

        User user = new User(name, pin);
        
        if (userExists(name) && !userMatchesPin(user.getUserID())) {
            return;
        }
    
        DiaryController.setUser(user);
        DiaryApp.getDiaryApp().changeScene("Diary.fxml");
    }

    @FXML
    public void createUser() {
        return;
    }

    private boolean userExists(String userName) {
        String[] diaryNames = RetrieveDiaries.getDiaryNames();

        if (diaryNames == null) {
            return false;
        }

        for (String name : diaryNames) {
            if (!name.contains("+")) {
                continue;
            }
            name = name.substring(0, name.indexOf("+"));
            if (name.replace("_", " ").equals(userName)) {
                return true;
            }
        }
        return false;
    }

    private boolean userMatchesPin(String userID) {
        String[] diaryNames = RetrieveDiaries.getDiaryNames();

        if (diaryNames == null) {
            return false;
        }

        for (String diary : diaryNames) {
            if (!diary.contains("+")) {
                continue;
            }
            if (diary.substring(0, diary.lastIndexOf("+")).equals(userID)) {
                return true;
            }
        }

        return false;
    }
}