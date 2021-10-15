package diary.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;



public class DiaryControllerTest extends ApplicationTest{

    private DiaryController controller;
    private Parent root;

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Diary.fxml"));
        this.root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
  }

  public Parent getRoot()
{
    return root;
}

    private String getText(){
        return ((TextArea)getRoot().lookup("#textEntry")).getText();
}

    @Test
    public void testController() {
        assertNotNull(this.controller);
    }

    @Test
    public void testRobot(){
        clickOn("#textEntry").write("Test");
        assertEquals("Test", getText());
        clickOn("#entrySubmit");
    }

    @Test
    public void testDifferentDate(){
        clickOn(((DatePicker)getRoot().lookup("#dateInput")).getEditor()).write("10/11/2021"+"\n");
        clickOn("#textEntry").write("Test2");
        clickOn("#entrySubmit");
        assertEquals("Test2", getText());
    }

    @Test
    public void testMoreText(){
        clickOn(((DatePicker)getRoot().lookup("#dateInput")).getEditor()).write("10/11/2021"+"\n");
        clickOn("#textEntry").write(" Test3");
        assertEquals("Test2 Test3", getText());
        clickOn("#entrySubmit");
    }

    @Test
    public void testBackToCurrentDate(){
        assertNotNull(getText());
    }

    @Test
    public void testBackToDifferDate(){
        clickOn(((DatePicker)getRoot().lookup("#dateInput")).getEditor()).write("10/11/2021"+"\n");
        assertNotNull(getText());
    }

}


