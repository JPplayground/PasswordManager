package passwordmanager.app;

public class PlatformExaminer {

    private final Platform CURRENT_PLATFORM;

    enum Platform {
        WINDOWS,
        LINUX,
        INVALID
    }

    public PlatformExaminer() {
        String osName = System.getProperty("os.name");

        if (osName.toLowerCase().contains("win")) {
            CURRENT_PLATFORM = Platform.WINDOWS;
        }
        else if (osName.toLowerCase().contains("lin")) {
            CURRENT_PLATFORM = Platform.LINUX;
        }
        else {
            CURRENT_PLATFORM = Platform.INVALID;
        }

        if (CURRENT_PLATFORM == Platform.INVALID) {
            System.err.println("Invalid platform detected!");
            System.exit(-1);
        }
    }

    public Platform getPlatform() {
        return CURRENT_PLATFORM;
    }

}
