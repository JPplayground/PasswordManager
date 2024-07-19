package passwordmanager.database.PreparedStatementGeneratorTests;

import passwordmanager.backend.local.database.DatabaseConnection;
import passwordmanager.backend.local.database.DatabaseConstants;
import passwordmanager.backend.local.database.PreparedStatementGenerator;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for verifying the creation of the 'ENTRIES' table in the database.
 */
public class TableCreationTest {

    private static Connection connection;

    /**
     * Opens a new database connection before each test.
     *
     * @throws SQLException if a database access error occurs.
     */
    @BeforeEach
    public void openConnection() throws SQLException {
        DatabaseConnection.setConnection();
        connection = DatabaseConnection.getConnection();
    }

    /**
     * Closes the database connection after each test.
     *
     * @throws SQLException if a database access error occurs.
     */
    @AfterEach
    public void closeConnection() throws SQLException {
        connection.close();
    }

    /**
     * Tests the creation of the 'ENTRIES' table in the database.
     *
     * @throws SQLException if a database access error occurs.
     */
    @Test
    public void testEntryTableCreation() throws SQLException {
        PreparedStatementGenerator preparedStatementGenerator = new PreparedStatementGenerator();
        PreparedStatement createTable = preparedStatementGenerator.prepareEntryTableCreationStatement();

        // Execute the table creation statement and ensure no exceptions are thrown
        assertDoesNotThrow(() -> createTable.executeUpdate());

        // Verify the table was created successfully
        String sql = "SELECT COUNT(*) FROM sqlite_master WHERE type='table' AND name=?;";
        PreparedStatement checkTable = connection.prepareStatement(sql);
        checkTable.setString(1, DatabaseConstants.ENTRIES_TABLE_NAME);

        ResultSet resultSet = checkTable.executeQuery();
        assertTrue(resultSet.next());

        int tableCount = resultSet.getInt(1);
        assertEquals(1, tableCount);
    }
}
