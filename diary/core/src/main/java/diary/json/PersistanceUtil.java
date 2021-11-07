package diary.json;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import diary.core.User;

public final class PersistanceUtil {

    public static String ResourcesFilePath() {
        return "src/main/resources/";
    }

    public static String curDirFilePath() {
        return "";
    }

    public static String makeResourcesPathString(final String fileName) {
        String filePath = PersistanceUtil.ResourcesFilePath()
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
        String filePath = PersistanceUtil.ResourcesFilePath()
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
        File chosenDir;
        if (relPath) {
            chosenDir = new File(ResourcesFilePath());
        } else {
            chosenDir = new File(curDirFilePath());
        }

        String[] files = chosenDir.list();
        for (String name : files) {
            if (fileName.startsWith(name)) {
                foundFiles.add(name);
            }
        }
        return foundFiles;
    }
}
