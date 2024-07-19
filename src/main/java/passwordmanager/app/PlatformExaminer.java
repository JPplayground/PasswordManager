package passwordmanager.app;

/**
 * Utility class for examining the current operating platform.
 */
public class PlatformExaminer {

    private final Platform CURRENT_PLATFORM;

    /**
     * Enum representing the supported platforms.
     */
    enum Platform {
        WINDOWS,
        LINUX,
        INVALID
    }

    /**
     * Constructs a PlatformExaminer object and determines the current operating platform.
     */
    public PlatformExaminer() {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("win")) {
            CURRENT_PLATFORM = Platform.WINDOWS;
        } else if (osName.contains("lin")) {
            CURRENT_PLATFORM = Platform.LINUX;
        } else {
            CURRENT_PLATFORM = Platform.INVALID;
        }

        if (CURRENT_PLATFORM == Platform.INVALID) {
            System.err.println("Invalid platform detected!");
            System.exit(-1);
        }
    }

    /**
     * Retrieves the current operating platform.
     *
     * @return the current platform
     */
    public Platform getPlatform() {
        return CURRENT_PLATFORM;
    }
}
