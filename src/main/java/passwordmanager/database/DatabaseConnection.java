package passwordmanager.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The {@code DatabaseConnection} class provides a static method to set the connection to the database.
 * This class is used to set the connection to the database and retrieve the connection when needed.
 * The connection is set to the database URL specified in the {@link DatabaseConstants} class.
 *
 * <p>Instances of this class are not needed as all methods are static.
 * The connection to the database is set using the {@link #setConnection(boolean)} method.
 * The connection to the database is retrieved using the {@link #getConnection()} method.
 *
 * @see DatabaseConstants
 */
public class DatabaseConnection {

    private static Connection connection;

    /**
     * Sets the connection to the database.
     *
     * @param isTesting whether the connection is for testing purposes.
     */
    public static void setConnection(boolean isTesting) {

        try {
            if (isTesting) {
                connection = DriverManager.getConnection(DatabaseConstants.TEST_CONNECTION_URL);
            } else {
                connection = DriverManager.getConnection(DatabaseConstants.APP_CONNECTION_URL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Closes the connection to the database.
     */
    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the connection to the database.
     *
     * @return the connection to the database.
     */
    public static Connection getConnection() {

        if (connection == null) {
            System.out.println("You forgot to call DatabaseConnection.setConnection() first!");
            System.exit(0);
        }

        return connection;
    }

}
