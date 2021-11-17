package diary.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import diary.core.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


public class DiaryControllerTest extends ApplicationTest{

    private DiaryController diaryController;
    private Parent diaryPane;
    private final static File testFilePath = new File("src/main/resources/DiaryEntries.json");
    private Stage stage;

    @Override
    public void start(final Stage stage) throws Exception{
        this.stage = stage;

        FXMLLoader diaryLoader = new FXMLLoader(
        this.getClass().getResource("Diary.fxml"));
        diaryPane = diaryLoader.load();
        Scene diaryScene = new Scene(diaryPane);

        diaryController = (DiaryController) diaryLoader.getController();
        User test = new User("TestUser", "1111");
        diaryController.openNewUser(test);
        stage.setTitle("Diary");
        stage.setScene(diaryScene);
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
        return diaryPane;

    }
    private String getText(){
        return ((TextArea)getRoot().lookup("#textEntry")).getText();
    }

    @Test
    public void testController() {
        assertNotNull(this.diaryController);
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

    // @AfterAll
    // public static void deleteIfStillExists(){
    //     if(testFilePath.exists()){
    //         testFilePath.delete();
    //     }
    // }
    @AfterAll
    public static void delteFilesAfter(){
        delteFiles();
    }

}


