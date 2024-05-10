package passwordmanager.database.SQLStatementBulderTests;

import passwordmanager.database.DatabaseConnection;
import passwordmanager.database.DatabaseConstants;
import passwordmanager.database.SQLStatementBuilder;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateAndQueryTests {

    private static Connection connection;


    @BeforeEach
    public void createNewConnectionAndTables() throws SQLException {
        DatabaseConnection.setConnection(true);

        {
            // Create Table 'ENTRIES'
            var sqlStatementBuilder = new SQLStatementBuilder();
            PreparedStatement statement = sqlStatementBuilder.prepareEntryTableCreationStatement();
            statement.executeUpdate();
        }

        {
            // Create Table 'COMMON_EMAILS'
            var sqlStatementBuilder = new SQLStatementBuilder();
            PreparedStatement statement = sqlStatementBuilder.prepareCommonEmailsTableCreationStatement();
            statement.executeUpdate();
        }
    }

    @Test
    public void testInsertEntry() throws SQLException {
        // Insert an entry
        var sqlStatementBuilder = new SQLStatementBuilder();
        PreparedStatement statement = sqlStatementBuilder.prepareInsertEntryStatement(
                "insertTestTitle", "test@example.com", "password123", "testUser", "http://example.com", "General"
        );

        int affectedRows = statement.executeUpdate();
        assertEquals(1, affectedRows, "One row should have been inserted.");
    }

    @Test
    public void testGetEntry() throws SQLException {
        var sqlStatementBuilder = new SQLStatementBuilder();

        // Constant vars for this entry
        String title = "getTestTitle", email = "test@example.com", password = "password123";
        String username = "testUser", link = "http://example.com", category = "General";

        // Insert an entry
        PreparedStatement insertEntryStatement = sqlStatementBuilder.prepareInsertEntryStatement(title, email, password, username, link, category);
        int affectedRows = insertEntryStatement.executeUpdate();
        assertEquals(1, affectedRows, "One row should have been inserted.");

        // Get entry and verify data within it
        PreparedStatement getEntryStatement = sqlStatementBuilder.prepareGetEntryStatement(title);
        ResultSet resultSet = getEntryStatement.executeQuery();
        assertTrue(resultSet.next(), "Data should be retrieved for the existing entry.");

        // Assert that all values match what was inserted
        assertEquals(title, resultSet.getString("title"), "The title should match the queried title.");
        assertEquals(email, resultSet.getString("email"), "The email should match the queried email.");
        assertEquals(password, resultSet.getString("password"), "The password should match the queried password.");
        assertEquals(username, resultSet.getString("username"), "The username should match the queried username.");
        assertEquals(link, resultSet.getString("link"), "The link should match the queried link.");
        assertEquals(category, resultSet.getString("category"), "The category should match the queried category.");

    }

    @Test
    public void testUpdateEntry() throws SQLException {
        var sqlStatementBuilder = new SQLStatementBuilder();
        int affectedRows;

        // Constant vars for this entry
        String title = "updateTestTitle", password = "password123";
        String username = "testUser", link = "http://example.com", category = "General";

        // Var that we will change
        String originalEmail = "test@example.com";
        String newEmail = "newemail@example.com";

        // Insert an entry
        PreparedStatement insertEntryStatement = sqlStatementBuilder.prepareInsertEntryStatement(
                title, originalEmail, password, username, link, category
        );
        affectedRows = insertEntryStatement.executeUpdate();
        assertEquals(1, affectedRows, "One row should have been inserted.");


        // Update the entry with a new email
        PreparedStatement updateStatement = sqlStatementBuilder.prepareEntryUpdateStatement(
                title, newEmail, null, null, null, null);
        affectedRows = updateStatement.executeUpdate();
        assertEquals(1, affectedRows, "Only one row should be affected.");

        // Ensure new email matches what was passed in
        PreparedStatement getEntryStatement = sqlStatementBuilder.prepareGetEntryStatement(title);
        ResultSet resultSet = getEntryStatement.executeQuery();

        assertTrue(resultSet.next());
        String updatedEmail = resultSet.getString(DatabaseConstants.EntryColumns.EMAIL.toString());

        assertEquals(newEmail, updatedEmail);

        // Ensure other parameters remained unchanged
        assertEquals(title, resultSet.getString("title"), "The title should match the queried title.");
        assertEquals(password, resultSet.getString("password"), "The password should match the queried password.");
        assertEquals(username, resultSet.getString("username"), "The username should match the queried username.");
        assertEquals(link, resultSet.getString("link"), "The link should match the queried link.");
        assertEquals(category, resultSet.getString("category"), "The category should match the queried category.");
    }

    @Test
    public void testRemoveEntry() throws SQLException {
        var sqlStatementBuilder = new SQLStatementBuilder();
        int affectedRows;

        // Constant vars for this entry
        String title = "removeTestTitle";

        // Insert an entry
        PreparedStatement insertEntryStatement = sqlStatementBuilder.prepareInsertEntryStatement(
                title, "test@example.com", "password123", "testUser", "http://example.com", "General"
        );
        affectedRows = insertEntryStatement.executeUpdate();
        assertEquals(1, affectedRows, "One row should have been inserted.");

        // Delete the entry
        PreparedStatement removeEntryStatement = sqlStatementBuilder.prepareRemoveEntryStatement(title);
        affectedRows = removeEntryStatement.executeUpdate();
        assertEquals(1, affectedRows, "One row should have been deleted.");

        // Try to find the entry ensuring no results are returned
        PreparedStatement getEntryStatement = sqlStatementBuilder.prepareGetEntryStatement(title);
        ResultSet resultSet = getEntryStatement.executeQuery();
        assertFalse(resultSet.next());
    }



    @Test
    public void testGetAllEntryTitles() throws SQLException {
        var sqlStatementBuilder = new SQLStatementBuilder();

        String title1 = "getAllTestTitle1", email1 = "test@example.com1", password1 = "password1";
        String username1 = "testUser1", link1 = "http://example.com1", category1 = "General1";

        String title2 = "getAllTestTitle2", email2 = "test@example.com2", password2 = "password2";
        String username2 = "testUser2", link2 = "http://example.com2", category2 = "General2";

        String title3 = "getAllTestTitle3", email3 = "test@example.com3", password3 = "password3";
        String username3 = "testUser3", link3 = "http://example.com3", category3 = "General3";

        String title4 = "getAllTestTitle4", email4 = "test@example.com4", password4 = "password4";
        String username4 = "testUser4", link4 = "http://example.com4", category4 = "General4";

        String title5 = "getAllTestTitle5", email5 = "test@example.com5", password5 = "password5";
        String username5 = "testUser5", link5 = "http://example.com5", category5 = "General5";

        // Prepare statements for inserting entries
        PreparedStatement insert1 = sqlStatementBuilder.prepareInsertEntryStatement(title1, email1, password1, username1, link1, category1);
        PreparedStatement insert2 = sqlStatementBuilder.prepareInsertEntryStatement(title2, email2, password2, username2, link2, category2);
        PreparedStatement insert3 = sqlStatementBuilder.prepareInsertEntryStatement(title3, email3, password3, username3, link3, category3);
        PreparedStatement insert4 = sqlStatementBuilder.prepareInsertEntryStatement(title4, email4, password4, username4, link4, category4);
        PreparedStatement insert5 = sqlStatementBuilder.prepareInsertEntryStatement(title5, email5, password5, username5, link5, category5);

        // Execute inserts
        ArrayList<PreparedStatement> statements = new ArrayList<>(Arrays.asList(insert1, insert2, insert3, insert4, insert5));
        for (var statement : statements) {
            statement.execute();
        }

        PreparedStatement statement = sqlStatementBuilder.prepareGetAllEntryTitlesStatement();
        ResultSet resultSet = statement.executeQuery();
        
        assertNotNull(resultSet, "ResultSet should not be null.");

        // Verify all titles match
        resultSet.next();
        assertEquals(title1, resultSet.getString(1));
        resultSet.next();
        assertEquals(title2, resultSet.getString(1));
        resultSet.next();
        assertEquals(title3, resultSet.getString(1));
        resultSet.next();
        assertEquals(title4, resultSet.getString(1));
        resultSet.next();
        assertEquals(title5, resultSet.getString(1));
    }

    // TODO: Test date_created and date_modified parameters

    // TODO: Test adding common emails


}
