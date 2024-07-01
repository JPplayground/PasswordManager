package passwordmanager.app;

public class DeveloperSettings {

    // IMPORTANT: Hard coded settings
    private static final ApplicationMode APPLICATION_MODE = ApplicationMode.TESTING;

    public enum ApplicationMode {
        TESTING,
        PRODUCTION
    }

    public static ApplicationMode getApplicationMode() {
        return APPLICATION_MODE;
    }
}
