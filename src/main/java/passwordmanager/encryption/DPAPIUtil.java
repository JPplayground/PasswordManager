package passwordmanager.encryption;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.W32APIOptions;
import com.sun.jna.platform.win32.Crypt32;
import com.sun.jna.platform.win32.WinCrypt.DATA_BLOB;

/**
 * The DPAPIUtil class provides methods to securely encrypt and decrypt data using the Windows Data Protection API (DPAPI).
 * This class leverages the JNA (Java Native Access) library to interact with native Windows functions.
 */
public class DPAPIUtil {

    /**
     * The Crypt32Ext interface extends the Crypt32 interface from JNA to load the native Crypt32 library
     * and define the required DPAPI methods.
     */
    public interface Crypt32Ext extends Crypt32 {
        Crypt32Ext INSTANCE = Native.load("Crypt32", Crypt32Ext.class, W32APIOptions.DEFAULT_OPTIONS);

        /**
         * Encrypts the data in a DATA_BLOB structure.
         *
         * @param pDataIn The DATA_BLOB structure that contains the plaintext data to be encrypted.
         * @param szDataDescr A description string. This parameter can be null.
         * @param pOptionalEntropy A DATA_BLOB structure that contains a password or other additional entropy used to encrypt the data. This parameter can be null.
         * @param pvReserved Reserved for future use and must be null.
         * @param pPromptStruct A pointer to a CRYPTPROTECT_PROMPTSTRUCT structure that provides information about where and when prompts are to be displayed. This parameter can be null.
         * @param dwFlags A set of bit flags that indicate options for encryption.
         * @param pDataOut The DATA_BLOB structure that receives the encrypted data.
         * @return Returns true if the function succeeds, false otherwise.
         */
        boolean CryptProtectData(DATA_BLOB pDataIn, String szDataDescr, DATA_BLOB pOptionalEntropy, Pointer pvReserved, Pointer pPromptStruct, int dwFlags, DATA_BLOB pDataOut);

        /**
         * Decrypts the data in a DATA_BLOB structure.
         *
         * @param pDataIn The DATA_BLOB structure that contains the encrypted data.
         * @param szDataDescr A pointer to a string that receives a description of the data. This parameter can be null.
         * @param pOptionalEntropy A DATA_BLOB structure that contains a password or other additional entropy used to encrypt the data. This parameter can be null.
         * @param pvReserved Reserved for future use and must be null.
         * @param pPromptStruct A pointer to a CRYPTPROTECT_PROMPTSTRUCT structure that provides information about where and when prompts are to be displayed. This parameter can be null.
         * @param dwFlags A set of bit flags that indicate options for decryption.
         * @param pDataOut The DATA_BLOB structure that receives the decrypted data.
         * @return Returns true if the function succeeds, false otherwise.
         */
        boolean CryptUnprotectData(DATA_BLOB pDataIn, PointerByReference szDataDescr, DATA_BLOB pOptionalEntropy, Pointer pvReserved, Pointer pPromptStruct, int dwFlags, DATA_BLOB pDataOut);
    }

    /**
     * Encrypts the given data using the Windows Data Protection API (DPAPI).
     *
     * @param data The plaintext data to be encrypted.
     * @return The encrypted data.
     * @throws Exception If encryption fails.
     */
    public static byte[] encrypt(byte[] data) throws Exception {
        DATA_BLOB dataIn = new DATA_BLOB(data);
        DATA_BLOB dataOut = new DATA_BLOB();

        if (!((Crypt32) Crypt32Ext.INSTANCE).CryptProtectData(dataIn, null, null, null, null, 0, dataOut)) {
            throw new Exception("Encryption failed");
        }

        return dataOut.getData();
    }

    /**
     * Decrypts the given data using the Windows Data Protection API (DPAPI).
     *
     * @param encryptedData The encrypted data to be decrypted.
     * @return The decrypted data.
     * @throws Exception If decryption fails.
     */
    public static byte[] decrypt(byte[] encryptedData) throws Exception {
        DATA_BLOB dataIn = new DATA_BLOB(encryptedData);
        DATA_BLOB dataOut = new DATA_BLOB();

        if (!((Crypt32)Crypt32Ext.INSTANCE).CryptUnprotectData(dataIn, new PointerByReference(), null, null, null, 0, dataOut)) {
            throw new Exception("Decryption failed");
        }

        return dataOut.getData();
    }
}
