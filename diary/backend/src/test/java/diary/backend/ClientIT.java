package diary.backend;

import diary.frontend.Client;
import diary.json.EntryFromJSON;
import diary.json.EntryToJSON;
import diary.json.PersistancePaths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;

/**
 * Integration test for client/server
 * Located in the backend directory so it runs after server is started
 * Frontend directory is core/../resources while Backend directory is
 * just diary/
 */
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

    /**
     * Creates a file
     * @param frontEnd Wether to use frontend or backend directory
     * @return true if the file was successfully created
     */
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

    /**
     * Checks that a file exists with the correct content
     * @param frontEnd Wether to check frontend or backend directory
     * @return true if the file was found with correct content
     */
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

    /**
     * Deletes a file
     * @param frontEnd Wether to use frontend or backend directory
     * @return true if the file was successfully deleted
     */
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

    /**
     * Creates a file on backend directory, uses the api to retrieve it, and 
     * checks that the file appears with the same content in the frontend
     * directory
     */
    @Test
    public void getDiaries() {
        //Lag fila i backend(i diary/)
        Assertions.assertTrue(createFile(false));

        //Kjør controller.getDiaries() med starten på filnavnet
        Client.getDiaries(testFileName);

        //Sjekk at fila har dukka opp på frontent(core/../resources), identisk
        Assertions.assertTrue(checkFile(true));

        //Slett fila på frontend og backend
        Assertions.assertTrue(deleteFile(true));
        Assertions.assertTrue(deleteFile(false));
    }

    /**
     * Creates a file on frontend directory, uses the api to send it, checks 
     * that the file appears with the same content in the backend directory. 
     */
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
