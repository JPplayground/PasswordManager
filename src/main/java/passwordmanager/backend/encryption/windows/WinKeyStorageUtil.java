package passwordmanager.backend.encryption.windows;

import passwordmanager.app.DevelopmentSettings;
import passwordmanager.backend.encryption.UserKeyGenerator;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * The {@code KeyStorageUtil} class provides functionality to securely generate, store, and retrieve a User Key
 * using the Windows Data Protection API (DPAPI). The User Key is encrypted and stored in a secure location
 * (AppData directory) and can be retrieved and decrypted when needed.
 *
 * <p>Depending on the application mode (production or development), the key file name and storage path
 * are determined and initialized statically.
 */
public class WinKeyStorageUtil {

    private static final String APP_NAME = "JP-PasswordManager";

    private static final String USER_KEY_FILE;

    private static final String APP_DATA_PATH;

    private static final Path USER_KEY_FILEPATH;

    static {
        if (DevelopmentSettings.getApplicationMode() == DevelopmentSettings.ApplicationMode.PRODUCTION) {
            USER_KEY_FILE = "userKey.dat";
            APP_DATA_PATH = System.getenv("LOCALAPPDATA");
        } else {
            // If testing we are not going to create an AppData directory
            USER_KEY_FILE = "testKey.dat";
            APP_DATA_PATH = "./test_app_data";
        }
        USER_KEY_FILEPATH = Path.of(APP_DATA_PATH, APP_NAME, USER_KEY_FILE);

        // TODO: Remove this!!!!
        System.out.println("USER Key FILE PATH:" + USER_KEY_FILEPATH);
        System.out.println("App data path:" + APP_DATA_PATH);
        System.out.println("User key file:" + USER_KEY_FILE);
    }

    /**
     * Checks if the User Key file is present in the specified directory.
     *
     * @return {@code true} if the User Key file exists, {@code false} otherwise
     */
    public static boolean isKeyPresent() {
        return Files.exists(USER_KEY_FILEPATH);
    }

    /**
     * Creates a new User Key, encrypts it using DPAPI, and stores it in the specified directory.
     *
     * @param args Command line arguments (not used in this method)
     * @throws Exception if an error occurs during key generation, encryption, or storage
     * @throws IllegalStateException if a User Key already exists
     */
    public static void createUserKey(String[] args) throws Exception {

        if (isKeyPresent()) {
            throw new IllegalStateException("A key already exists!");
        }

        // Generate a new User Key
        SecretKey userKey = UserKeyGenerator.generateKey();

        // Encrypt the User Key using DPAPI
        byte[] encryptedUserKey = DPAPIUtil.encrypt(userKey.getEncoded());

        // Store the encrypted User Key in the AppData directory
        Files.createDirectories(Paths.get(APP_DATA_PATH, APP_NAME));
        Files.write(Paths.get(USER_KEY_FILEPATH.toUri()), encryptedUserKey, StandardOpenOption.CREATE);

    }

    /**
     * Retrieves and decrypts the User Key from the specified directory.
     *
     * @return the decrypted {@code SecretKey}
     * @throws Exception if an error occurs during key retrieval or decryption
     */
    public static SecretKey getUserKey() throws Exception {

        // Example of how to retrieve and decrypt the User Key
        byte[] retrievedEncryptedUserKey = Files.readAllBytes(Paths.get(USER_KEY_FILEPATH.toUri()));
        byte[] decryptedUserKeyBytes = DPAPIUtil.decrypt(retrievedEncryptedUserKey);
        SecretKey decryptedUserKey = new SecretKeySpec(decryptedUserKeyBytes, "AES");

        return decryptedUserKey;
    }

    /**
     * Call this method if testing to clean up created directory/key on each application start
     */
    public void testingCleanUp() {
        // TODO: Cleanup directory created on test runs
    }

}
