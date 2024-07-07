package passwordmanager.database;

import org.junit.Test;
import passwordmanager.backend.encryption.windows.DPAPIUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DPAPIUtilTest {

    @Test
    public void EncryptionAndDecryptionTest() {

        String testString = "test";
        byte[] encrypted = null;
        byte[] decrypted = null;

        // Encrypt test
        try {
            encrypted = DPAPIUtil.encrypt(testString.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to encrypt data");
            return;
        }

        // Decrypt test
        try {
            if (encrypted == null) {
                fail("Encrypted data is null");
            }
            decrypted = DPAPIUtil.decrypt(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to decrypt data");
        }

        // Compare
        assertEquals(testString, new String(decrypted));
    }

}
