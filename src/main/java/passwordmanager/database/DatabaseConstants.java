package passwordmanager.database;

/**
 * The {@code DatabaseConstants} class contains constants used for database configuration and table/column names.
 * This class includes the database driver URL, connection URLs, table names, and column names.
 *
 * <p>This class is designed to provide a single source of truth for all database-related constants,
 * making it easier to manage and update these values in one place.
 *
 * <p>Usage example:
 * <pre>
 * {@code
 * String driverUrl = DatabaseConstants.DRIVER_URL;
 * String appConnectionUrl = DatabaseConstants.APP_CONNECTION_URL;
 * }
 * </pre>
 */
public class DatabaseConstants {

    /**
     * The URL of the database driver.
     */
//    public static final String DRIVER_URL = "org.h2.Driver";

    /**
     * The connection URL for the application database.
     */
    public static final String APP_CONNECTION_URL = "jdbc:sqlite:./password_manager.db";

    /**
     * The connection URL for the test database.
     */
    public static final String TEST_CONNECTION_URL = "jdbc:sqlite::memory:";

    /**
     * The name of the entries table.
     */
    public static final String ENTRIES_TABLE_NAME = "ENTRIES";

    /**
     * The name of the common emails table.
     */
    public static final String COMMON_EMAILS_TABLE_NAME = "COMMON_EMAILS";

    /**
     * The name of the email column in the common emails table.
     */
    public static final String COMMON_EMAILS_COLUMN_NAME = "email";

    /**
     * Enum representing the columns in the entries table.
     */
    public enum EntryColumns {
        TITLE("title"),
        EMAIL("email"),
        PASSWORD("password"),
        USERNAME("username"),
        LINK("link"),
        CATEGORY("category"),
        DATE_CREATED("date_created"),
        DATE_MODIFIED("date_modified");

        private final String columnName;

        EntryColumns(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }
}
