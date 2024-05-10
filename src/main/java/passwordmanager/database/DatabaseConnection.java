package passwordmanager.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection;

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

    public static Connection getConnection() {

        if (connection == null) {
            System.out.println("You forgot to call DatabaseConnection.setConnection() first!");
            System.exit(0);
        }

        return connection;
    }
}
