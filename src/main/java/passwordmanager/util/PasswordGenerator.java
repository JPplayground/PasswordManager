package passwordmanager.util;

import java.util.Random;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * The {@code PasswordGenerator} class provides a method to generate secure random passwords.
 * The generated password includes a mix of lowercase letters, uppercase letters, numbers, and special characters.
 * This ensures a strong and secure password suitable for use in a password manager application.
 *
 * <p>The generated password is 15 characters long, consisting of 13 random letters or numbers
 * and 2 special characters.
 *
 * <p>Usage example:
 * <pre>
 * {@code
 * String password = PasswordGenerator.getPassword();
 * System.out.println("Generated password: " + password);
 * }
 * </pre>
 */
public class PasswordGenerator {

    private static final String lowCase = "abcdefghijklmnopqrstuvxyz";
    private static final String upCase = "ABCDEFGHIJKLMNOPQRSTUVXYZ";
    private static final String numbers = "0123456789";
    private static final String specialChar = "$&@!?";

    /**
     * Generates a secure random password consisting of 13 random letters or numbers and 2 special characters.
     *
     * @return a randomly generated password string.
     */
    public static String getPassword() {
        Random rand = new Random();

        List<Character> passwordChars = new ArrayList<>();

        // 13 characters from letters and numbers to leave space for 2 special characters
        for (int i = 0; i < 13; i++) {
            int typeChoice = rand.nextInt(3);
            switch (typeChoice) {
                case 0:
                    passwordChars.add(lowCase.charAt(rand.nextInt(lowCase.length())));
                    break;
                case 1:
                    passwordChars.add(upCase.charAt(rand.nextInt(upCase.length())));
                    break;
                case 2:
                    passwordChars.add(numbers.charAt(rand.nextInt(numbers.length())));
                    break;
            }
        }

        // Add two random special characters
        passwordChars.add(specialChar.charAt(rand.nextInt(specialChar.length())));
        passwordChars.add(specialChar.charAt(rand.nextInt(specialChar.length())));

        // Shuffle to randomize positions of characters
        Collections.shuffle(passwordChars);

        // Build final password string
        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }
}
