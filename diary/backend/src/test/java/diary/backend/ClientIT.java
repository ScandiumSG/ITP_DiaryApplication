package diary.backend;

import diary.frontend.Client;
import diary.json.EntryFromJSON;
import diary.json.EntryToJSON;
import diary.json.PersistancePaths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;

public class ClientIT {
    public static final String testFileName = "Bernt+1324+Dagbok";
    public static final String testFileContent = "[{\"entryDate\": " + 
    "\"01-01-2011\", \"entryContent\": \"potato tomato tornado\"}, " + 
    "{\"entryDate\": \"02-01-2011\", \"entryContent\": " + 
    "\"posghsdhsdhsdrnado\"}}]";
    public static final File backEndPath = new File(
        PersistancePaths.makeCurrentDirectoryPathString(testFileName));
    public static final File frontEndPath = new File(
        PersistancePaths.makeResourcesPathString(testFileName));

    public boolean createFile(boolean frontEnd) {
        try {
            EntryToJSON.write(testFileName, testFileContent, frontEnd);
            return true;
        } catch (Exception e) {
            System.out.println("Couldn't write file in ClientIT");
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkFile(boolean frontEnd) {
        if (frontEnd && !frontEndPath.exists()) {
            System.out.println("File not on frontEnd in ClientIT");
            return false;
        } else if (!frontEnd && !backEndPath.exists()) {
            System.out.println("File not on backEnd in ClientIT");
            return false;
        }
        try {
            String fileContent = EntryFromJSON.readToString(testFileName, frontEnd);
            return testFileContent.equals(fileContent);
        } catch (Exception e) {
            System.out.println("Couldn't read file in ClientIT");
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteFile(boolean frontEnd) {
        if (frontEnd) {
            if (frontEndPath.exists()) {
                return frontEndPath.delete();
            }
        } else {
            if (backEndPath.exists()) {
                return backEndPath.delete();
            }
        }
        System.out.println("Couldn't delete file in ClientIT");
        return false;
    }

    @Test
    public void getDiaries() {
        //Lag fila i backend(i diary/)
        Assertions.assertTrue(createFile(false));

        //Kjør controller.getDiaries() med starten på filnavnet
        Client.getDiaries(testFileName);

        //Sjekk at fila har dukka opp på frontent(core/../resources), med identisk innhold
        Assertions.assertTrue(checkFile(true));

        //Slett fila på frontend og backend
        Assertions.assertTrue(deleteFile(true));
        Assertions.assertTrue(deleteFile(false));
    }

    @Test
    public void postDiary() {
        //Lag fila i frontent(core/../resources)
        Assertions.assertTrue(createFile(true));

        //Kjør controller.postDiary() med starten på filnavnet
        Client.postDiary(testFileName);

        //Sjekk at fila har dukka opp på backend(i diary/), med identisk innhold
        Assertions.assertTrue(checkFile(false));

        //Slett fila på frontend og backend
        Assertions.assertTrue(deleteFile(true));
        Assertions.assertTrue(deleteFile(false));
    }
}
