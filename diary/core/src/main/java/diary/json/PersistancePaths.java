package diary.json;

import diary.core.User;
import java.io.File;

public class PersistancePaths {

    public static String resourcesFilePath() {
        return curDirFilePath() + "src/main/resources/";
    }

    public static String curDirFilePath() {
        File curDirFile = new File("");
        return curDirFile.getAbsolutePath() + "/";
    }

    /**
     * Return a filepath string to be used when make a File object to
     * the provided filename.
     * @param fileName The full name of the desired file.
     * @return A string with a directory path to the file
     * in src/main/resources.
     */
    public static String makeResourcesPathString(final String fileName) {
        String filePath = PersistancePaths.resourcesFilePath()
            + sanitizeFilename(fileName)
            + ".json";
        return filePath;
    }

    /**
     * Return a filepath string to be used when make a File object to
     * the provided user and diaryname.
     * @param user The user associated with the diary.
     * @param diaryName Name of the diary file.
     * @return A string with a directory path to the file
     * in src/main/resources.
     */
    public static String makeResourcesPathString(final User user, final String diaryName) {
        String filePath = PersistancePaths.resourcesFilePath()
            + user.getUserID()
            + "+"
            + sanitizeFilename(diaryName)
            + ".json";
        return filePath;
    }

    /**
     * Return a filepath string to be used when make a File object to
     * the provided filename.
     * @param fileName The full name of the desired file.
     * @return A string with a directory path to the file
     * in the root-directory
     */
    public static String makeCurrentDirectoryPathString(final String fileName) {
        String filePath = PersistancePaths.curDirFilePath()
            + sanitizeFilename(fileName)
            + ".json";
        return filePath;
    }

    /**
     * Return a filepath string to be used when make a File object to
     * the provided user and diaryname.
     * @param user The user associated with the diary.
     * @param diaryName Name of the diary file.
     * @return A string with a directory path to the file
     * in the root-directory
     */
    public static String makeCurrentDirectoryPathString(final User user, final String diaryName) {
        String filePath = PersistancePaths.curDirFilePath()
            + user.getUserID()
            + "+"
            + sanitizeFilename(diaryName)
            + ".json";
        return filePath;
    }

    private static String sanitizeFilename(final String fileName) {
        String sanString = fileName.replace(" ", "_");
        return sanString;
    }
}
