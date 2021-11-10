package diary.ui;

import java.io.IOException;

import diary.core.User;
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
        try{
            String username = usernameField.getText();
            String pin = pinField.getText();

            User user = new User("lars", "1234");
            System.out.println(user.getUserID());
            /*
            System.out.println("Laget user");
            DiaryController.setUser(user);
            System.out.println("satt user");
            DiaryApp.getDiaryApp().changeScene("Diary.fxml");
            System.out.println("byttet scene");
            */
    
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }
}