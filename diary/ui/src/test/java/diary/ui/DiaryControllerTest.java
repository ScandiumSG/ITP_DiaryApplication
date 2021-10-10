package diary.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.testfx.framework.junit5.ApplicationTest;

import diary.core.Entry;
import diary.json.EntryFromJSON;
import diary.json.EntryToJSON;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class DiaryControllerTest extends ApplicationTest{

    private DiaryController controller;

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Diary.fxml"));
        final Parent root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }
    private List<Entry> diaryList= new ArrayList<>();
    private Entry i1, i2, i3;


    @BeforeEach
    public void setupItems() {
        Iterator<Entry> entry = diaryList.iterator();
        i1 = new Entry("name1").as(entry.next());
        i2 = new Entry("name2").as(entry.next());
        i3 = new Entry("name3").as(entry.next());
        diaryList.add(i1);
        diaryList.add(i2);
        diaryList.add(i3);
  }
    @Test
    public void testControllerDiary() {
        assertNotNull(this.controller);
        assertNotNull(this.diaryList);
        // initial todo items
    }
    @Test
    public void testInitialize() {
    // initial diary items

    }
    @Test
    public void testSaveDateEntry()
    {
        for(int i=0; i<diaryList.size();i++)
        {
            Entry temp =i(getText(), getDate());
            assertNotNull(temp);

        }

    }

}


