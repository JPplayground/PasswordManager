package com.jp.passwordmanager.database;

import java.sql.*;
import java.util.ArrayList;

/**
 * The {@code DatabaseAPI} class provides methods for interacting with a database
 * to perform various operations related to managing entries and common email addresses
 * within a password manager application.
 *
 * <p>Instances of this class are obtained using the {@link #getInstance(boolean)} method.
 * This class ensures safe database operations and encapsulates SQL statements for
 * adding, modifying, removing, and retrieving data related to entries and common emails.
 *
 * @author Josh Patterson
 * @see Entry
 */
public class DatabaseAPI {

    private SQLStatementBuilder sqlStatementBuilder;
    private static DatabaseAPI instance;

    /**
     * Private constructor to initialize the database connection and create necessary tables.
     * This constructor is called internally to ensure only one instance of the {@code DatabaseAPI}
     * class exists throughout the application.
     */
    private DatabaseAPI(boolean usingTestingDatabase) {
        try {

            Connection connection;

            if (usingTestingDatabase) { // Using a test database in Junit tests
                connection = DriverManager.getConnection(DatabaseConfig.TEST_CONNECTION_URL_WITH_PROXY);
            } else {
                connection = DriverManager.getConnection(DatabaseConfig.APP_CONNECTION_URL);
            }

            this.sqlStatementBuilder = new SQLStatementBuilder(connection);

            PreparedStatement createEntryTable = sqlStatementBuilder.prepareEntryTableCreationStatement();
            createEntryTable.execute();

            PreparedStatement createCommonEmailsTable = sqlStatementBuilder.prepareCommonEmailsTableCreationStatement();
            createCommonEmailsTable.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the singleton instance of the {@code DatabaseAPI} class.
     *
     * @return the singleton instance of the {@code DatabaseAPI}.
     */
    public static DatabaseAPI getInstance(boolean usingTestingDatabase) {
        if (instance == null) {
            instance = new DatabaseAPI(usingTestingDatabase);
        }
        return instance;
    }

    /**
     * Adds a new entry to the database.
     *
     * @param title    the title of the entry.
     * @param email    the email associated with the entry.
     * @param password the password of the entry.
     * @param username the username associated with the entry.
     * @param link     a hyperlink associated with the entry, if any.
     * @param category the category to which the entry belongs.
     */
    public void newEntry(String title, String email, String password, String username, String link, String category) {
        try {
            PreparedStatement stmt = sqlStatementBuilder.prepareInsertEntryStatement(title, email, password, username, link, category);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifies an existing entry in the database.
     *
     * @param title    the title of the entry to modify.
     * @param email    the new email to update.
     * @param password the new password to update.
     * @param username the new username to update.
     * @param link     the new link to update.
     * @param category the new category to update.
     */
    public void modifyEntry(String title, String email, String password, String username, String link, String category) {
        try {
            PreparedStatement stmt = sqlStatementBuilder.prepareEntryUpdateStatement(title, email, password, username, link, category);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes an entry from the database.
     *
     * @param title the title of the entry to remove.
     */
    public void removeEntry(String title) {
        try {
            PreparedStatement stmt = sqlStatementBuilder.prepareRemoveEntryStatement(title);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves an entry from the database based on its title.
     *
     * @param titleKey the title of the entry to retrieve.
     * @return the {@code Entry} object corresponding to the title, or {@code null} if not found.
     */
    public Entry getEntry(String titleKey) {
        try {
            PreparedStatement stmt = sqlStatementBuilder.prepareGetEntryStatement(titleKey);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {

                // Retrieve entry data from the result set
                String title, email, password, username, link, category;
                title = resultSet.getString(DatabaseConfig.EntryColumns.TITLE.toString());
                email = resultSet.getString(DatabaseConfig.EntryColumns.EMAIL.toString());
                password = resultSet.getString(DatabaseConfig.EntryColumns.PASSWORD.toString());
                username = resultSet.getString(DatabaseConfig.EntryColumns.USERNAME.toString());
                link = resultSet.getString(DatabaseConfig.EntryColumns.LINK.toString());
                category = resultSet.getString(DatabaseConfig.EntryColumns.CATEGORY.toString());

                Timestamp dateCreated, dateModified;
                dateCreated = resultSet.getTimestamp(DatabaseConfig.EntryColumns.DATE_CREATED.toString());
                dateModified = resultSet.getTimestamp(DatabaseConfig.EntryColumns.DATE_MODIFIED.toString());

                // Create and return the Entry object
                return new Entry(title, email, password, username, link, category, dateCreated, dateModified);
            } else {
                throw new SQLException("Entry not found with title: " + titleKey);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves a list of all entry titles from the database.
     *
     * @return an {@code ArrayList} containing all entry titles, or {@code null} if an error occurs.
     */
    public ArrayList<String> getListOfEntryTitles() {
        ArrayList<String> entries = new ArrayList<>();
        try {
            PreparedStatement stmt = sqlStatementBuilder.prepareGetAllEntryTitlesStatement();
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                entries.add(resultSet.getString(1));
            }
            return entries;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Adds a common email address to the database.
     *
     * @param email the email address to add to the common emails list.
     */
    public void addCommonEmail(String email) {
        try {
            PreparedStatement stmt = sqlStatementBuilder.prepareAddCommonEmailStatement(email);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a list of common emails from the database.
     * This method executes a SQL query prepared by {@code prepareGetListOfCommonEmailsStatement}
     * from the {@code sqlStatementBuilder} to fetch all common emails stored in the database.
     * The emails are read from the result set and added to an ArrayList.
     *
     * <p>This method handles any SQL exceptions by printing the stack trace and returns {@code null}
     * if an exception occurs, indicating that the operation failed.
     *
     * @return An {@code ArrayList<String>} containing all the common emails retrieved from the database,
     *         or {@code null} if an SQL exception occurs.
     */
    public ArrayList<String> getListOfCommonEmails() {

        ArrayList<String> emails = new ArrayList<>();

        try {
            PreparedStatement stmt = sqlStatementBuilder.prepareGetListOfCommonEmailsStatement();
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                emails.add(resultSet.getString(1));
            }

            return emails;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
