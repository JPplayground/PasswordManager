package passwordmanager.backend.local.encryption;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class UserKeyGenerator {
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
