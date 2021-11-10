package diary.core;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

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

    @Test
    public void testUsernameValidation() {
        String username = " ";
        String userpin = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999+1));

        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {new User(username, userpin);});
    }

    @Test
    public void testUserpinValidation() {
        String username = "Ola Nordmann";
        String userpin = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 999+1));

        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {new User(username, userpin);});

        String userpin2 = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999+1));
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {new User(username, userpin2);});
    }
}
