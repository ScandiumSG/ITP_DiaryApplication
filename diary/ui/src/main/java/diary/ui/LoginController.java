package diary.ui;

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
    private Text title;

    @FXML
    void logIn() throws IOException {
        DiaryController.setUsername(usernameField.getText());
        DiaryApp.diaryApp.changeScene("Diary.fxml");
    }
}