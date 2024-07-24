package passwordmanager.backend.local.SQLLite;

/**
 * The {@code LocalDatabaseConstants} class contains constants used for database configuration and table/column names.
 * This class includes the database driver URL, connection URLs, table names, and column names.
 *
 * <p>This class is designed to provide a single source of truth for all database-related constants,
 * making it easier to manage and update these values in one place.
 *
 * <p>Usage example:
 * <pre>
 * {@code
 * String appConnectionUrl = LocalDatabaseConstants.APP_CONNECTION_URL;
 * String testConnectionUrl = LocalDatabaseConstants.TEST_CONNECTION_URL;
 * String entriesTableName = LocalDatabaseConstants.ENTRIES_TABLE_NAME;
 * }
 * </pre>
 */
public class LocalDatabaseConstants {

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

}
