package passwordmanager.app;

public class DeveloperSettings {

    // --------------------------------------------------------------------------------------- //

    // TODO: Going to have conflicts with Junit tests here, not sure how to fix yet

    private static final ApplicationMode APPLICATION_MODE = ApplicationMode.TESTING;

    public enum ApplicationMode {
        TESTING,
        PRODUCTION
    }

    public static ApplicationMode getApplicationMode() {
        return APPLICATION_MODE;
    }

    // --------------------------------------------------------------------------------------- //

    // Plans to build a programmatic JavaFX UI as well

    private static final GUIMode GUI_MODE = GUIMode.FXML;

    public enum GUIMode {
        FXML,
        PROGRAMMATIC
    }

    public static GUIMode getGuiMode() { return GUI_MODE; };

    // --------------------------------------------------------------------------------------- //

    // Integration with AWS dynamo / potentially other non-local databases

    private static final DBMode DB_MODE = DBMode.LOCAL;

    public enum DBMode {
        LOCAL,
        REMOTE
    }

    public static DBMode getDbMode() { return DB_MODE; }
}
