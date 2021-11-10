package diary.core;

public class User {
    private String userName;
    private String userPin;

    public User(final String name, final String pin) {
        validateUserName(name);
        validateUserPin(pin);
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
        } else if (!isNumeric(pin)){
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
    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
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
}
