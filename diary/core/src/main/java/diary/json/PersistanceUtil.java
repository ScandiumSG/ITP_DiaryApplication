package diary.json;

import diary.core.User;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class PersistanceUtil {

    /**
     * A utility method to get all filesnames that STARTS with the provided string.
     * @param fileName String we wish all retrieved files to start with.
     * @param relPath A boolean that switch between resources storage and root-dir storage
     * @return List A list of strings, each string is a filename that starts with
     * provided fileName param.
     */
    public static List<String> getFilesStartingWith(final String fileName, boolean relPath) {
        List<String> foundFiles = new ArrayList<>();

        File chosenDir = new File(PersistancePaths.resourcesFilePath());
        if (!relPath) {
            chosenDir = new File(PersistancePaths.rootDirFilePath());
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

    /**
     * A static method that retrieves a file object pointing to the provided
     * user and filename in the src/main/resources storage path.
     * @param user The user the file belongs to.
     * @param diaryName The name given to the specific diary to load.
     * @return File A file object pointing at the specified json file.
     */
    public static File getJsonFile(final User user, final String diaryName) {
        return new File(
            PersistancePaths.makeResourcesPathString(user, diaryName));
    }

    /**
     * A static method that retrieves a file object pointing to the provided
     * filename in the src/main/resources storage path.
     * @param fileName The full name given to the specific diary to load.
     * @return File A file object pointing at the specified json file.
     */
    public static File getJsonFile(final String fileName) {
        return new File(
            PersistancePaths.makeResourcesPathString(fileName));
    }

    /**
     * Checks if the provided filePath has existing parent directories, 
     * if not the required folders/directories to make the filePath valid is
     * attempted to be created.
     * @throws IOException Can't create the required directories.
     */
    public static void checkDirExistance(String filePath) throws IOException {
        File writeDir = new File(filePath.substring(
            0, filePath.lastIndexOf(File.separator)));
        if (!writeDir.exists()) {
            if (!writeDir.mkdirs()) {
                throw new IOException(
                    "Could not create directory " + writeDir.getAbsolutePath());
            }
        }
    }
}
