package com.jp.passwordmanager.data.SQLStatementBulderTests;

import com.jp.passwordmanager.data.DatabaseConfig;
import com.jp.passwordmanager.data.SQLStatementBuilder;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;


public class TableCreationTest {

    private static Connection connection;

    @BeforeAll
    public static void setup() {
        // TODO: Consider removing logging, doesn't seem to be very useful at the moment
        // Reset log file
        String pathToLogFile = System.getProperty("user.dir") + "\\spy.log";
        File file = new File(pathToLogFile);
        try (PrintWriter writer = new PrintWriter(file)) {
            // The file content is cleared
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void openConnection() throws SQLException {
        connection = DriverManager.getConnection(DatabaseConfig.TEST_CONNECTION_URL_WITH_PROXY);
    }

    @AfterEach
    public void closeConnection() throws SQLException {
        connection.close();
    }


    @AfterAll
    public static void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    public void testEntryTableCreation() throws SQLException {

        SQLStatementBuilder sqlStatementBuilder;
        PreparedStatement createTable;

        sqlStatementBuilder = new SQLStatementBuilder(connection);

        createTable = sqlStatementBuilder.prepareEntryTableCreationStatement();
        assertDoesNotThrow(() -> createTable.executeUpdate());

        String sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC' AND TABLE_NAME = ?;";
        PreparedStatement checkTable = this.connection.prepareStatement(sql);
        checkTable.setString(1, DatabaseConfig.ENTRIES_TABLE_NAME);

        ResultSet resultSet = checkTable.executeQuery();
        assertTrue(resultSet.next());

        int tableCount = resultSet.getInt(1);
        assertEquals(1, tableCount);

    }

    @Test
    public void testCommonEmailsTableCreation() throws SQLException {

        SQLStatementBuilder sqlStatementBuilder;
        PreparedStatement createTable;

        sqlStatementBuilder = new SQLStatementBuilder(connection);

        createTable = sqlStatementBuilder.prepareCommonEmailsTableCreationStatement();
        assertDoesNotThrow(() -> createTable.executeUpdate());

        String sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC' AND TABLE_NAME = ?;";
        PreparedStatement checkTable = this.connection.prepareStatement(sql);
        checkTable.setString(1, DatabaseConfig.COMMON_EMAILS_TABLE_NAME);

        ResultSet resultSet = checkTable.executeQuery();
        assertTrue(resultSet.next());

        int tableCount = resultSet.getInt(1);
        assertEquals(1, tableCount);

    }
}
