package passwordmanager.database.SQLStatementBulderTests;

import passwordmanager.backend.local.database.DatabaseConnection;
import passwordmanager.backend.local.database.DatabaseConstants;
import passwordmanager.backend.local.database.PreparedStatementGenerator;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;


public class TableCreationTest {

    private static Connection connection;

    @BeforeEach
    public void openConnection() throws SQLException {
        DatabaseConnection.setConnection();
        connection = DatabaseConnection.getConnection();
    }

    @AfterEach
    public void closeConnection() throws SQLException {
        connection.close();
    }

    @Test
    public void testEntryTableCreation() throws SQLException {

        PreparedStatementGenerator preparedStatementGenerator;
        PreparedStatement createTable;

        preparedStatementGenerator = new PreparedStatementGenerator();

        createTable = preparedStatementGenerator.prepareEntryTableCreationStatement();
        assertDoesNotThrow(() -> createTable.executeUpdate());

        String sql = "SELECT COUNT(*) FROM sqlite_master WHERE type='table' AND name=?;";
        PreparedStatement checkTable = connection.prepareStatement(sql);
        checkTable.setString(1, DatabaseConstants.ENTRIES_TABLE_NAME);

        ResultSet resultSet = checkTable.executeQuery();
        assertTrue(resultSet.next());

        int tableCount = resultSet.getInt(1);
        assertEquals(1, tableCount);
    }


}
