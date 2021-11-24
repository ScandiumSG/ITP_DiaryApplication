package diary.json;

import diary.core.User;
import java.io.File;

public class PersistancePaths {

    /**
     * Constructs a string directing towards the src/main/resources
     * path. Intended to be used to contruct File objects with correct
     * file directory.
     * @return String A string of the file directory path.
     */
    public static String resourcesFilePath() {
        String appendResources = String.join(File.separator, "core",
            "src", "main", "resources", "");
        return rootDirFilePath() + appendResources;
    }

    private static String curDirFilePath() {
        File curDirFile = new File("");

        String sep = File.separator;
        String curDirPath = curDirFile.getAbsolutePath();

        // If installed use user.home instead, check each OS
        String userHomeDir = System.getProperty("user.home") + sep;
        // Windows
        if (curDirPath.toLowerCase().contains(
            sep + "program files" + sep + "diary")) {
            return userHomeDir;
        // Linux
        } else if (curDirPath.toLowerCase().contains(
            sep + "opt" + sep + "diary")) {
            return userHomeDir;
        // macOs
        } else if (curDirPath.toLowerCase().contains(
            sep + "application" + sep + "diaryapplication")) {
            return userHomeDir;
        // No OS specific, user is using direct java code.
        } else {
            return curDirPath + sep;
        }
    }

    /**
     * Constructs a string directing towards the root-directory
     * path(diary/). Intended to be used to contruct File objects with correct
     * file directory.
     * @return String A string of the file directory path.
     */
    public static String rootDirFilePath() {
        String curDirString = curDirFilePath();
        String fileDirSelect = "diary" + File.separator;
        if (curDirString.contains(fileDirSelect)) {
            String rootDirString = curDirString.substring(
                0, curDirString.indexOf(fileDirSelect) + fileDirSelect.length());
            return rootDirString;
        } else {
            return curDirString + fileDirSelect;
        }
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
        String filePath = PersistancePaths.rootDirFilePath()
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
        String filePath = PersistancePaths.rootDirFilePath()
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

    public static void main(String[] args) {
        resourcesFilePath();
    }
}
