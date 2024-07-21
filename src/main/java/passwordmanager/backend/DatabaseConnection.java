package passwordmanager.backend;

import passwordmanager.app.ApplicationSettings;
import passwordmanager.backend.local.database.LocalDatabaseConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The {@code DatabaseConnection} class provides static methods to manage the connection to the database.
 * This class is used to set the connection to the database and retrieve the connection when needed.
 * The connection is set to the database URL specified in the {@link LocalDatabaseConstants} class based on
 * the database mode and application mode specified in the {@link ApplicationSettings} class.
 *
 * <p>Instances of this class are not needed as all methods are static.
 * The connection to the database is set using the {@link #setConnection()} method.
 * The connection to the database is retrieved using the {@link #getConnection()} method.
 * The connection to the database can be closed using the {@link #closeConnection()} method.
 *
 * <p>Usage example:
 * <pre>
 * {@code
 * DatabaseConnection.setConnection();
 * Connection conn = DatabaseConnection.getConnection();
 * // Use the connection
 * DatabaseConnection.closeConnection();
 * }
 * </pre>
 *
 * @see LocalDatabaseConstants
 * @see ApplicationSettings
 */
public class DatabaseConnection {

    private static Connection connection;

    /**
     * Automatically sets the connection to the database based on the database mode and application mode specified
     * in the {@link ApplicationSettings} class. It currently supports local database connections for
     * testing and production modes. Other database modes are not yet implemented.
     */
    public static void setConnection() {
        try {
            // Determine the database mode and application mode
            ApplicationSettings.DBMode dbMode = ApplicationSettings.getDbMode();
            ApplicationSettings.ApplicationMode appMode = ApplicationSettings.getApplicationMode();

            switch (dbMode) {
                case LOCAL -> {
                    if (appMode == ApplicationSettings.ApplicationMode.TESTING) {
                        connection = DriverManager.getConnection(LocalDatabaseConstants.TEST_CONNECTION_URL);
                    } else {
                        connection = DriverManager.getConnection(LocalDatabaseConstants.APP_CONNECTION_URL);
                    }
                }
                case DYNAMO_DB, MICROSOFT_SQL, ORACLE_SQL -> {
                    System.out.printf("Support for %s is not yet implemented.%n", dbMode);
                    System.exit(1);
                }
                default -> throw new UnsupportedOperationException("Unsupported DB mode: " + dbMode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the current connection to the database. If the connection has not been set, it prints an error
     * message and terminates the program.
     *
     * @return the connection to the database
     */
    public static Connection getConnection() {
        if (connection == null) {
            System.err.println("You forgot to call DatabaseConnection.setConnection() first!");
            System.exit(0);
        }
        return connection;
    }



    /**
     * Closes the connection to the database if it is open.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
