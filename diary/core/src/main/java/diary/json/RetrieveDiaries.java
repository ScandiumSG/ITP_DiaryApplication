package diary.json;

import diary.core.Entry;
import diary.core.User;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public final class RetrieveDiaries {

    public static HashMap<String, List<Entry>> findDiaries(final User user)
        throws IOException {
        HashMap<String, List<Entry>> foundDiaries = new HashMap<>();

        File fileDir = new File(PersistanceUtil.ResourcesFilePath());

        // File.list() returns a String array of all filenames within a dir.
        String[] allLocalDiaries = fileDir.list();
        if (allLocalDiaries == null) {
            return null;
        }

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
