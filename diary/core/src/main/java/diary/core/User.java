package diary.core;

import diary.json.RetrieveDiaries;
import java.util.HashMap;


public class User {
    private String userName;
    private String userPin;
    private HashMap<String, HashMap<String, Entry>> userDiaries;

    /**
     * Constructor of the user obejct. Takes a user chosen name and pin, validates these, then
     * retrieves any found diary associated with the chosen name and pin.
     *
     * @param name A string of the selected username.
     * @param pin A 4-digit numerical pin number chosen by the user.
     */
    public User(final String name, final String pin) {
        validateUserName(name);
        validateUserPin(pin);

        // Catch potential IOExceptions, make new HashMap if exception.
        try {
            userDiaries = RetrieveDiaries.findDiaries(this);
        } catch (Exception e) {
            userDiaries = new HashMap<String, HashMap<String, Entry>>();
        }
    }

    /**
     * Validation method to allow for quick update of nameing requirements
     * for the diary users.
     * @param name A string with the users chosen username
     * @throws IllegalArgumentException If username has length 0 after
     * removal of whitepsace.
     */
    private void validateUserName(final String name) {
        if (name.trim().length() == 0) {
            throw new IllegalArgumentException(
                "Username must contain atleast 1 character.");
        } else {
            String sanitizedName = name.replace(" ", "_").trim();
            this.userName = sanitizedName;
        }
    }

    /**
     * Validation method for pin requirements for the diary users.
     * @param pin A 4-digit integer value
     * @throws IllegalArgumentException If pin has anything other than 4 digits.
     */
    private void validateUserPin(final String pin) {
        if (pin.length() != 4) {
            throw new IllegalArgumentException(
                "Pin number must contain 4 digits");
        } else if (!isNumeric(pin)) {
            throw new IllegalArgumentException(
                "Pin must only contain numbers");
        } else {
            this.userPin = pin;
        }
    }

    /**
     * Checks if the given string only contains digits
     *
     * @param str the string to check
     * @return True if the string only contains digits. False otherwise
     */
    private boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Getter method for the users chosen username.
     * @return A string of the users chosen username.
     */
    public String getUserName() {
        return this.userName.replace("_", " ");
    }

    /**
     * Getter method for the users chosen pin-code.
     * @return A integer of the users pin-code
     */
    public String getUserPin() {
        return this.userPin;
    }

    /**
     * A getter method for the full ID of the user. This method combines the
     * getters for username and user-pin. Any spaces in the username will be
     * replaced by "_".
     * @return A string of username and user-pin separated by a "+". Spaces
     * in username replaced by "_".
     */
    public String getUserID() {
        return this.userName + "+" + String.valueOf(getUserPin());
    }

    /**
     * @return HashMap With diary name as the key and a HashMap of all entries as value.
     * See {@link #getDiary() getDiary} for the format of the HashMap value contained in the
     * returned HashMap.
     */
    public HashMap<String, HashMap<String, Entry>> getAllDiaries() {
        return new HashMap<String, HashMap<String, Entry>>(this.userDiaries);
    }

    /**
     * @return HashMap With entry date as the key and corresponding Entry for each date av
     * the value.
     */
    public HashMap<String, Entry> getDiary(String diaryName) {
        return new HashMap<String, Entry>(this.userDiaries.get(diaryName));
    }

    /**
     * Return a specified Entry from the provided date, from the diary.
     * @param diaryName String name of the diary to find Entry in.
     * @param date String date to retrieve corresponding Entry.
     * @return
     */
    public Entry getEntryByDate(String diaryName, String date) {
        try {
            HashMap<String, Entry> selectedDiary = this.userDiaries.get(diaryName);
            return selectedDiary.get(date);
        } catch (NullPointerException e) {
            return null;
        }

    }

    /**
     * Update a specific Entry in a specified diary.
     * @param diaryName String name of the diary to place the Entry in.
     * @param entry The new Entry to override the previous date-specific Entry.
     */
    public void setEntryInDiary(String diaryName, Entry entry) {
        if (!this.userDiaries.keySet().contains(diaryName)) {
            this.userDiaries.put(diaryName, new HashMap<String, Entry>());
        }
        HashMap<String, Entry> selectedDairy = this.userDiaries.get(diaryName);
        selectedDairy.put(entry.getDate(), entry);
    }
}
