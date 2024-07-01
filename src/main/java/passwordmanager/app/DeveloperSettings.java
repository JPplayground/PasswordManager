package passwordmanager.app;

public class DeveloperSettings {

    // IMPORTANT: Hard coded settings
    // TODO: Going to have conflicts with Junit tests here, not sure how to fix yet
    private static final ApplicationMode APPLICATION_MODE = ApplicationMode.TESTING;

    public enum ApplicationMode {
        TESTING,
        PRODUCTION
    }

    public static ApplicationMode getApplicationMode() {
        return APPLICATION_MODE;
    }
}
