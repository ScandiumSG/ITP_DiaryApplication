package diary.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;


public class DiaryControllerTest extends ApplicationTest{

    private DiaryController controller;
    private Parent root;

    @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("Diary.fxml"));
    final Parent root = loader.load();
    controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }

  public Parent getText()
{
    return root;
}

private String root(){
    return ((TextArea) getText().lookup("#textArea")).getText();
}

    @Test
    public void testRobot(){
        clickOn("#textEntry").write("Test");
        assertEquals("test", (TextArea) getText().lookup("#textArea"));
        clickOn("#entrySubmit");

       /*
Bytte til en dato som ikke er i dag
Sjekke at tekstfelt er tom
Sjekke at dato er den valgte dagen
Skrive noe annet i tekstfeltet
Submitte
Skrive en tredje ting i tekstfeltet
Submitte
Bytte tilbake til dagens dato
Sjekke at teksten har blitt det du lagret først
Sjekke at datoen er riktig
Bytte tilbake til den andre datoen
Sjekke at teksten er det du lagret andre gang på denne dagen*/
    }


}


