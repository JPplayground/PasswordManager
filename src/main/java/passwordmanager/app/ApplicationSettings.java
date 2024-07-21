package passwordmanager.app;

/**
 * Configuration class for managing development settings such as application mode, GUI mode, and database mode.
 */
public class ApplicationSettings {

    /**
     * Enum representing the mode of the application.
     */
    public enum ApplicationMode {
        TESTING,
        PRODUCTION
    }

    /**
     * Enum representing the mode of the GUI.
     */
    public enum GUIMode {
        FXML,
        PROGRAMMATIC
    }

    /**
     * Enum representing the mode of the database.
     */
    public enum DBMode {
        LOCAL,
        DYNAMO_DB,
        MICROSOFT_SQL,
        ORACLE_SQL
    }

    // Application mode setting
    private static final ApplicationMode APPLICATION_MODE = ApplicationMode.TESTING;

    // GUI mode setting
    private static final GUIMode GUI_MODE = GUIMode.FXML;

    // Database mode setting
    private static final DBMode DB_MODE = DBMode.LOCAL;

    /**
     * Retrieves the current application mode.
     *
     * @return the current application mode
     */
    public static ApplicationMode getApplicationMode() {
        return APPLICATION_MODE;
    }

    /**
     * Retrieves the current GUI mode.
     *
     * @return the current GUI mode
     */
    public static GUIMode getGuiMode() {
        return GUI_MODE;
    }

    /**
     * Retrieves the current database mode.
     *
     * @return the current database mode
     */
    public static DBMode getDbMode() {
        return DB_MODE;
    }
}
