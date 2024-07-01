package passwordmanager.encryption;

import javax.crypto.KeyGenerator;

public class UserKeyGenerator {
    public static void generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            keyGenerator.generateKey();
        } catch (Exception e) {
            System.out.println("Key generation failed: " + e);
            System.exit(1);
        }
    }
}
