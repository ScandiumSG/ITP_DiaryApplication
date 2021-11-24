package diary.core;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    /**
     * Test that a User object can be created and that basic getters return
     * correct values.
     */
    @Test
    public void testUserCreation() {
        String username = "Ola";
        String userpin = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999+1));
        User user = new User(username, userpin);

        Assertions.assertEquals(username, user.getUserName());
        Assertions.assertEquals(userpin, user.getUserPin());
        Assertions.assertEquals(
            username+"+"+String.valueOf(userpin), user.getUserID());
    }

    /**
     * Test that user name sanitization functions as anticipated, swapping
     * " " for "_" in userIDs, but still returning " " for userName getter.
     */
    @Test
    public void testUsernameSanitization() {
        String username = "Ola Nordmann ";
        String userpin = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999+1));
        User user = new User(username, userpin);

        Assertions.assertTrue(user.getUserID().contains("_"));
        Assertions.assertFalse(user.getUserName().contains("_"));
        Assertions.assertTrue(user.getUserName().contains(" "));
        Assertions.assertNotEquals(username.trim(), user.getUserName());
    }

    /**
     * Minor test to check on the (very miniscule) user name validation. Make sure name
     * cannot be just whitespace (" ").
     */
    @Test
    public void testUsernameValidation() {
        String username = " ";
        String userpin = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999+1));

        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {new User(username, userpin);});
    }
    /**
     * Test that only valid 4-digit pins are accepted.
     */
    @Test
    public void testUserpinValidation() {
        String username = "Ola Nordmann";
        String userpin = String.valueOf(ThreadLocalRandom.current().nextInt(100, 999+1));

        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {new User(username, userpin);});

        String invalidPin1 = String.valueOf(ThreadLocalRandom.current().nextInt(10000, 99999+1));
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {new User(username, invalidPin1);});

        String invalidPin2 = "abcd";
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {new User(username, invalidPin2);});

        String invalidPin3 = "abcdefg";
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {new User(username, invalidPin3);});

        String invalidPin4 = "a2cd";
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {new User(username, invalidPin4);});
    }
}
