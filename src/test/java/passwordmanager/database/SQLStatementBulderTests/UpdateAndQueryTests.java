package passwordmanager.database.SQLStatementBulderTests;

import passwordmanager.database.DatabaseConnection;
import passwordmanager.database.DatabaseConstants;
import passwordmanager.database.PreparedStatementGenerator;
import passwordmanager.database.EntryTableColumns;
import org.junit.jupiter.api.*;
import passwordmanager.model.Entry;
import passwordmanager.model.EntryBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateAndQueryTests {

    private static Connection connection;


    @BeforeEach
    public void createNewConnectionAndTables() throws SQLException {
        DatabaseConnection.setConnection();

        {
            // Create Table 'ENTRIES'
            var psg = new PreparedStatementGenerator();
            PreparedStatement statement = psg.prepareEntryTableCreationStatement();
            statement.executeUpdate();
        }
        
    }

    @AfterEach
    public void closeConnection() throws SQLException {
        DatabaseConnection.closeConnection();
    }

    @Test
    public void testInsertEntry() throws SQLException {
        
        // Create an entry
        String title = "insertTestTitle";
        String email = "test@example.com";
        String password = "password123";
        String username = "testUser";
        String link = "https://example.com";
        String category = "General";
        
        Entry entry = new EntryBuilder(title)
                .email(email)
                .password(password)
                .username(username)
                .link(link)
                .category(category)
                .build();
        
        // Insert an entry
        var psg = new PreparedStatementGenerator();
        PreparedStatement statement = psg.prepareInsertEntryStatement(entry);

        int affectedRows = statement.executeUpdate();
        assertEquals(1, affectedRows, "One row should have been inserted.");
    }

    @Test
    public void testGetEntry() throws SQLException {
        var psg = new PreparedStatementGenerator();

        // Constant vars for this entry
        String title = "getTestTitle", email = "test@example.com", password = "password123";
        String username = "testUser", link = "https://example.com", category = "General";

        Entry entry = new EntryBuilder(title)
                .email(email)
                .password(password)
                .username(username)
                .link(link)
                .category(category)
                .build();

        // Insert an entry
        PreparedStatement insertEntryStatement = psg.prepareInsertEntryStatement(entry);
        int affectedRows = insertEntryStatement.executeUpdate();
        assertEquals(1, affectedRows, "One row should have been inserted.");

        // Get entry and verify data within it
        PreparedStatement getEntryStatement = psg.prepareGetEntryStatement(title);
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
        var psg = new PreparedStatementGenerator();
        int affectedRows;

        // Constant vars for this entry
        String title = "updateTestTitle", password = "password123";
        String username = "testUser", link = "http://example.com", category = "General";

        // Var that we will change
        String originalEmail = "test@example.com";
        String newEmail = "newemail@example.com";

        Entry entry = new EntryBuilder(title)
                .email(originalEmail)
                .password(password)
                .username(username)
                .link(link)
                .category(category)
                .build();

        // Insert an entry
        PreparedStatement insertEntryStatement = psg.prepareInsertEntryStatement(entry);
        affectedRows = insertEntryStatement.executeUpdate();
        assertEquals(1, affectedRows, "One row should have been inserted.");

        // Update the entry with a new email
        PreparedStatement updateStatement = psg.prepareEntryUpdateStatement(
                title, newEmail, null, null, null, null);
        affectedRows = updateStatement.executeUpdate();
        assertEquals(1, affectedRows, "Only one row should be affected.");

        // Ensure new email matches what was passed in
        PreparedStatement getEntryStatement = psg.prepareGetEntryStatement(title);
        ResultSet resultSet = getEntryStatement.executeQuery();

        assertTrue(resultSet.next());
        String updatedEmail = resultSet.getString(EntryTableColumns.EMAIL.toString());

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
        var psg = new PreparedStatementGenerator();
        int affectedRows;

        // Constant vars for this entry
        String title = "removeTestTitle";

        Entry entry = new EntryBuilder(title)
                .build();

        // Insert an entry
        PreparedStatement insertEntryStatement = psg.prepareInsertEntryStatement(entry);
        affectedRows = insertEntryStatement.executeUpdate();
        assertEquals(1, affectedRows, "One row should have been inserted.");

        // Delete the entry
        PreparedStatement removeEntryStatement = psg.prepareRemoveEntryStatement(title);
        affectedRows = removeEntryStatement.executeUpdate();
        assertEquals(1, affectedRows, "One row should have been deleted.");

        // Try to find the entry ensuring no results are returned
        PreparedStatement getEntryStatement = psg.prepareGetEntryStatement(title);
        ResultSet resultSet = getEntryStatement.executeQuery();
        assertFalse(resultSet.next());
    }



    @Test
    public void testGetAllEntryTitles() throws SQLException {
        var psg = new PreparedStatementGenerator();

        String title1 = "getAllTestTitle1", email1 = "test@example.com1", password1 = "password1";
        String username1 = "testUser1", link1 = "http://example.com1", category1 = "General1";

        Entry entry1 = new EntryBuilder(title1)
                .email(email1)
                .password(password1)
                .username(username1)
                .link(link1)
                .category(category1)
                .build();

        String title2 = "getAllTestTitle2", email2 = "test@example.com2", password2 = "password2";
        String username2 = "testUser2", link2 = "http://example.com2", category2 = "General2";

        Entry entry2 = new EntryBuilder(title2)
                .email(email2)
                .password(password2)
                .username(username2)
                .link(link2)
                .category(category2)
                .build();

        String title3 = "getAllTestTitle3", email3 = "test@example.com3", password3 = "password3";
        String username3 = "testUser3", link3 = "http://example.com3", category3 = "General3";

        Entry entry3 = new EntryBuilder(title3)
                .email(email3)
                .password(password3)
                .username(username3)
                .link(link3)
                .category(category3)
                .build();

        String title4 = "getAllTestTitle4", email4 = "test@example.com4", password4 = "password4";
        String username4 = "testUser4", link4 = "http://example.com4", category4 = "General4";

        Entry entry4 = new EntryBuilder(title4)
                .email(email4)
                .password(password4)
                .username(username4)
                .link(link4)
                .category(category4)
                .build();

        String title5 = "getAllTestTitle5", email5 = "test@example.com5", password5 = "password5";
        String username5 = "testUser5", link5 = "http://example.com5", category5 = "General5";

        Entry entry5 = new EntryBuilder(title5)
                .email(email5)
                .password(password5)
                .username(username5)
                .link(link5)
                .category(category5)
                .build();

        // Prepare statements for inserting entries
        PreparedStatement insert1 = psg.prepareInsertEntryStatement(entry1);
        PreparedStatement insert2 = psg.prepareInsertEntryStatement(entry2);
        PreparedStatement insert3 = psg.prepareInsertEntryStatement(entry3);
        PreparedStatement insert4 = psg.prepareInsertEntryStatement(entry4);
        PreparedStatement insert5 = psg.prepareInsertEntryStatement(entry5);

        // Execute inserts
        ArrayList<PreparedStatement> statements = new ArrayList<>(Arrays.asList(insert1, insert2, insert3, insert4, insert5));
        for (var statement : statements) {
            statement.execute();
        }

        PreparedStatement statement = psg.prepareGetAllEntryTitlesStatement();
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

}
