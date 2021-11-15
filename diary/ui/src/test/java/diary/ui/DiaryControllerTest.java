package diary.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class DiaryControllerTest extends ApplicationTest{

    private DiaryController controller;
    private Parent root;
    private final static File testFilePath = new File("src/main/resources/DiaryEntries.json");

    @Override
    public void start(final Stage stage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Diary.fxml"));
        this.root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

 
    @BeforeAll
    public static void supportHeadless(){
        delteFiles();
        DiaryApp.supportHeadless(); 
        
    }

    public static void delteFiles(){
        if(testFilePath.exists()){
            testFilePath.delete();
        }
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

    // @Test
    // public void testRobot(){
    //     clickOn("#textEntry").write("Test");
    //     assertEquals("Test", getText());
    //     clickOn("#entrySubmit");
    // }

    // @Test
    // public void testDifferentDate(){
    //     clickOn(((DatePicker)getRoot().lookup("#dateInput")).getEditor()).write("10-11-2021"+"\n");
    //     clickOn("#textEntry").write("Test2");
    //     clickOn("#entrySubmit");
    //     assertEquals("Test2", getText());
    //     clickOn("#textEntry").write(" Test3");
    //     assertEquals("Test2 Test3", getText());
    //     clickOn("#entrySubmit");
    // }

    // @Test
    // public void testBackToCurrentDate(){
    //     assertNotNull(getText());
    //     testFilePath.delete();
    // }

    // @Test
    // public void testBackToDifferDate(){
    //     clickOn(((DatePicker)getRoot().lookup("#dateInput")).getEditor()).write("10-11-2021"+"\n");
    //     assertNotNull(getText());
    //     testFilePath.delete();
    // }

    @AfterAll
    public static void deleteIfStillExists(){
        if(testFilePath.exists()){
            testFilePath.delete();
        }
    }
}


