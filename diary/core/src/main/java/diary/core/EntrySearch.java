package diary.core;

import diary.json.EntryFromJSON;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public final class EntrySearch {
    private static double matchCriteria = 0.6;

    /**
     * A method to search for a set of keywords and retrive a List of all
     * Entry's that contain the provided keywords.
     * @param diaryName A string identifying the diary to search in.
     * @param keywords String varargs that each Entry is compared against.
     * @return  List of all Entry's in the searched diary that match
     * at least 60% of provided keywords .
     * @throws IOException If EntryFromJSON cannot access storage device.
     */
    public static List<Entry> searchEntries(final String diaryName,
        String... keywords) throws IOException {
        List<Entry> matchingEntries = new ArrayList<Entry>();
        // Add all searchWords into a separate list
        List<String> searchWords = new ArrayList<String>();
        for (String word : keywords) {
            searchWords.add(word);
        }
        // Retrieve every entry from the specified diary
        List<Entry> fullDiary = EntryFromJSON.read(diaryName);

        double matchFit = 0;
        // Check each entry in the retrieved diary
        for (Entry entry : fullDiary) {
            matchFit = 0;
            // Check how many of the search words match the entry content.
            for (String word : searchWords) {
                if (entry.getContent()
                    .toLowerCase().contains(word.toLowerCase())) {
                    matchFit++;
                }
            }
            matchFit = matchFit / searchWords.size();
            if (matchFit > matchCriteria) {
                matchingEntries.add(entry);
            }
        }
        return matchingEntries;
    }
}
