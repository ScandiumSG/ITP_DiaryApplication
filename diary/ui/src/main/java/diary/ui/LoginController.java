package diary.ui;

import diary.core.User;
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
        try {
            User user = new User(usernameField.getText(), pinField.getText());
            System.out.println(user.getUserID());
            DiaryController.setUser(user);
            DiaryApp.getDiaryApp().changeScene("Diary.fxml");
            
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}