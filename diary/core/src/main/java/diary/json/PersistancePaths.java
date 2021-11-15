package diary.json;

import diary.core.User;
import java.io.File;
import java.util.Arrays;

public class PersistancePaths {

    /**
     * Constructs a string directing towards the src/main/resources
     * path. Intended to be used to contruct File objects with correct
     * file directory.
     * @return String A string of the file directory path.
     */
    public static String resourcesFilePath() {
        String appendResources = "src/main/resources/";
        String rscPath = curDirFilePath() + appendResources;
        String[] modules = {"/api/", "/core/", "/ui/"};
        if (Arrays.stream(modules).anyMatch(rscPath::contains)) {
            return rscPath;
        } else if (curDirFilePath().contains("/diary/")) {
            rscPath = curDirFilePath() 
                + "core/" 
                + appendResources;
            return rscPath;
        } else {
            rscPath = curDirFilePath() 
                + "diary/" 
                + "core/" 
                + appendResources;
            return rscPath;
        }
    }

    private static String curDirFilePath() {
        File curDirFile = new File("");
        return curDirFile.getAbsolutePath() + "/";
    }

    /**
     * Constructs a string directing towards the root-directory
     * path. Intended to be used to contruct File objects with correct
     * file directory.
     * @return String A string of the file directory path.
     */
    public static String rootDirFilePath() {
        String curDirString = curDirFilePath();
        String fileDirSelect = "diary/";
        String rootDirString = curDirString.substring(
            0, curDirString.indexOf(fileDirSelect) + fileDirSelect.length());
        return rootDirString;
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
}
