package passwordmanager.database;

import org.junit.jupiter.api.*;
import passwordmanager.backend.encryption.windows.DPAPIUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for verifying the encryption and decryption functionality of DPAPIUtil.
 */
public class DPAPIUtilTest {

    /**
     * Tests the encryption and decryption of a string using DPAPIUtil.
     */
    @Test
    public void EncryptionAndDecryptionTest() {

        String testString = "test";
        byte[] encrypted;
        byte[] decrypted = null;

        // Encrypt test
        try {
            encrypted = DPAPIUtil.encrypt(testString.getBytes());
        } catch (Exception e) {
            // TODO: Robust logging needed
            // e.printStackTrace();
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
            // TODO: Robust logging needed
            // e.printStackTrace();
            fail("Failed to decrypt data");
        }

        // Compare
        assertEquals(testString, new String(decrypted));
    }

}
