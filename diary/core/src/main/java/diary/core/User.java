package diary.core;

public class User {
    private String userName;
    private Integer userPin;

    public User(final String name, final Integer pin) {
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
    private void validateUserPin(final Integer pin) {
        if (String.valueOf(pin).length() != 4) {
            throw new IllegalArgumentException(
                "Pin number must contain 4 digits");
        } else {
            this.userPin = pin;
        }
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
    public Integer getUserPin() {
        return this.userPin;
    }

    /**
     * A getter method for the full ID of the user. This method combines the
     * getters for username and user-pin.
     * @return A string of username and user-pin separated by a "+".
     */
    public String getUserID() {
        return this.userName + "+" + String.valueOf(getUserPin());
    }
}
