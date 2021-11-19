package diary.json;

import diary.core.Entry;
import diary.core.User;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public final class RetrieveDiaries {

    /**
     * A method to return all diary files, in the form of List of Entry, that
     * is associated with the provided User object.
     * @param user The user object that is used to name associated files
     * @return HashMap Hashmap with diary name as key and a list of all entry's
     * within the diary as the mapped object
     * @throws IOException If filePath is non-existant while loading entries
     * with EntryFromJSON.read().
     */
    public static HashMap<String, List<Entry>> findDiaries(final User user)
        throws IOException {
        HashMap<String, List<Entry>> foundDiaries = new HashMap<>();

        String[] allLocalDiaries = getAllLocalDiaries();

        String desiredFileString = user.getUserID();

        for (String file : allLocalDiaries) {
            if (!file.contains("+")) {
                continue;
            } else if (
                file.subSequence(0, file.lastIndexOf("+"))
                .equals(desiredFileString)) {
                foundDiaries.put(
                    getDiaryName(file),
                    EntryFromJSON.read(user, getDiaryName(file)));
            }
        }
        return foundDiaries;
    }

    /**
     * Method to retrieve a String array with every fileName found locally.
     * @return String[] A String[] with the full name of all locally stored
     * .json files in the default storage path.
     * @throws IOException
     */
    public static String[] getAllLocalDiaries() throws IOException {
        File fileDir = new File(PersistancePaths.resourcesFilePath());
        PersistanceUtil.checkDirExistance(fileDir.getAbsolutePath());
        // File.list() returns a String array of all filenames within a dir.
        String[] allLocalDiaries = fileDir.list();
        if (allLocalDiaries == null) {
            return new String[0];
        }

        return allLocalDiaries;
    }


    private static String getDiaryName(final String fileName) {
        int signOccurance = 0;
        char tempChar;
        for (int i = 0; i < fileName.length(); i++) {
            tempChar = fileName.charAt(i);
            if (tempChar == '+') {
                signOccurance++;
            }
        }

        if (signOccurance != 2) {
            throw new IllegalArgumentException(
                "Unsuspected filename detected. Exiting to prevent errors.");
        } else {
            String diaryName = fileName.substring(
                fileName.lastIndexOf("+") + 1, fileName.indexOf(".json"));
            return diaryName.replace("_", " ");
        }
    }
}
