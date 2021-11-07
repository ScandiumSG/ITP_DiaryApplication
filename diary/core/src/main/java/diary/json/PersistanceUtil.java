package diary.json;

import diary.core.User;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public final class PersistanceUtil {

    public static String resourcesFilePath() {
        return "src/main/resources/";
    }

    public static String curDirFilePath() {
        File curDirFile = new File("");
        return curDirFile.getAbsolutePath();
    }

    public static String makeResourcesPathString(final String fileName) {
        String filePath = PersistanceUtil.resourcesFilePath()
            + sanitizeFilename(fileName)
            + ".json";
        return filePath;
    }

    public static String makeCurrentDirectoryPathString(final String fileName) {
        String filePath = PersistanceUtil.curDirFilePath()
            + sanitizeFilename(fileName)
            + ".json";
        return filePath;
    }

    public static String makeResourcesPathString(final User user, final String fileName) {
        String filePath = PersistanceUtil.resourcesFilePath()
            + user.getUserID()
            + "+"
            + sanitizeFilename(fileName)
            + ".json";
        return filePath;
    }

    public static String makeCurrentDirectoryPathString(final User user, final String fileName) {
        String filePath = PersistanceUtil.curDirFilePath()
            + user.getUserID()
            + "+"
            + sanitizeFilename(fileName)
            + ".json";
        return filePath;
    }

    private static String sanitizeFilename(final String fileName) {
        String sanString = fileName.replace(" ", "_");
        return sanString;
    }

    public static List<String> getFilesStartingWith(final String fileName, boolean relPath) {
        List<String> foundFiles = new ArrayList<>();

        File chosenDir = new File(resourcesFilePath());
        if (!relPath) {
            chosenDir = new File(curDirFilePath());
        }

        String[] files = chosenDir.list();
        if (files == null) {
            return null;
        }

        for (String name : files) {
            if (name.startsWith(fileName)) {
                foundFiles.add(name);
            }
        }
        return foundFiles;
    }
}
