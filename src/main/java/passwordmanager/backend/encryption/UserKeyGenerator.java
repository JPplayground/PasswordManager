package passwordmanager.backend.encryption;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Utility class for generating cryptographic keys for user encryption.
 */
public class UserKeyGenerator {

    /**
     * Generates a new AES secret key with a key size of 256 bits.
     *
     * @return a SecretKey object containing the generated key, or null if key generation fails.
     */
    public static SecretKey generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            return keyGenerator.generateKey();
        } catch (Exception e) {
            System.out.println("Key generation failed: " + e);
            System.exit(1);
        }
        return null;
    }
}
