package passwordmanager.database;

import passwordmanager.model.Entry;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@code DatabaseAPI} class provides methods for interacting with a database
 * to perform various operations related to managing entries and common email addresses
 * within a password manager application.
 *
 * <p>Instances of this class are obtained using the {@link #getInstance()} method.
 * This class ensures safe database operations and encapsulates SQL statements for
 * adding, modifying, removing, and retrieving data related to entries and common emails.
 *
 * <p>Note: This class follows the singleton design pattern to ensure only one instance
 * is created throughout the application.
 *
 * <p>Usage example:
 * <pre>
 * {@code
 * DatabaseAPI dbAPI = DatabaseAPI.getInstance();
 * dbAPI.newEntry("example title", "email@example.com", "password123", "username", "http://example.com", "example category");
 * }
 * </pre>
 *
 * @see Entry
 */
public class DatabaseAPI {

    private SQLQueryBuilder sqlQueryBuilder;

    // Singleton Instance
    private static DatabaseAPI instance;

    /**
     * Private constructor to initialize the database connection and create necessary tables.
     * This constructor is called internally to ensure only one instance of the {@code DatabaseAPI}
     * class exists throughout the application.
     */
    private DatabaseAPI() {
        try {
            this.sqlQueryBuilder = new SQLQueryBuilder();

            PreparedStatement createEntryTable = sqlQueryBuilder.prepareEntryTableCreationStatement();
            createEntryTable.execute();

            PreparedStatement createCommonEmailsTable = sqlQueryBuilder.prepareCommonEmailsTableCreationStatement();
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
    public static DatabaseAPI getInstance() {
        if (instance == null) {
            instance = new DatabaseAPI();
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
            PreparedStatement stmt = sqlQueryBuilder.prepareInsertEntryStatement(title, email, password, username, link, category);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new entry to the database.
     *
     * @param entry the entry object to be added.
     */
    public void newEntry(Entry entry) {
        this.newEntry(entry.getTitle(), entry.getEmail(), entry.getPassword(), entry.getUsername(), entry.getLink(), entry.getCategory());
    }

    /**
     * Modifies an existing entry in the database.
     * Parameters that do not need to be changed should be passed in as null.
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
            PreparedStatement stmt = sqlQueryBuilder.prepareEntryUpdateStatement(title, email, password, username, link, category);
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
            PreparedStatement stmt = sqlQueryBuilder.prepareRemoveEntryStatement(title);
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
            PreparedStatement stmt = sqlQueryBuilder.prepareGetEntryStatement(titleKey);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                // Retrieve entry data from the result set
                String title = resultSet.getString(DatabaseConstants.EntryColumns.TITLE.toString());
                String email = resultSet.getString(DatabaseConstants.EntryColumns.EMAIL.toString());
                String password = resultSet.getString(DatabaseConstants.EntryColumns.PASSWORD.toString());
                String username = resultSet.getString(DatabaseConstants.EntryColumns.USERNAME.toString());
                String link = resultSet.getString(DatabaseConstants.EntryColumns.LINK.toString());
                String category = resultSet.getString(DatabaseConstants.EntryColumns.CATEGORY.toString());
                Timestamp dateCreated = resultSet.getTimestamp(DatabaseConstants.EntryColumns.DATE_CREATED.toString());
                Timestamp dateModified = resultSet.getTimestamp(DatabaseConstants.EntryColumns.DATE_MODIFIED.toString());

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
     * Retrieves all entries from the database.
     *
     * @return an {@code ArrayList} containing all entries, or {@code null} if an error occurs.
     */
    public ArrayList<Entry> getAllEntries() {
        ArrayList<String> entryTitles = getListOfEntryTitles();
        ArrayList<Entry> entries = new ArrayList<>();

        for (String title : entryTitles) {
            entries.add(getEntry(title));
        }

        return entries;
    }

    /**
     * Retrieves a list of all entry titles from the database.
     *
     * @return an {@code ArrayList} containing all entry titles, or {@code null} if an error occurs.
     */
    public ArrayList<String> getListOfEntryTitles() {
        ArrayList<String> entries = new ArrayList<>();
        try {
            PreparedStatement stmt = sqlQueryBuilder.prepareGetAllEntryTitlesStatement();
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
            PreparedStatement stmt = sqlQueryBuilder.prepareAddCommonEmailStatement(email);
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
            PreparedStatement stmt = sqlQueryBuilder.prepareGetListOfCommonEmailsStatement();
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

    /**
     * Retrieves a list of all groups from the database.
     * This method executes a SQL query prepared by {@code prepareGetListOfGroupsStatement}
     * from the {@code sqlStatementBuilder} to fetch all groups stored in the database.
     * The groups are read from the result set and added to a HashSet.
     *
     * <p>This method handles any SQL exceptions by printing the stack trace and returns {@code null}
     * if an exception occurs, indicating that the operation failed.
     *
     * @return A {@code Set<String>} containing all the groups retrieved from the database,
     *         or {@code null} if an SQL exception occurs.
     */
    public Set<String> getListOfGroups() {
        Set<String> groups = new HashSet<>();
        try {
            PreparedStatement stmt = sqlQueryBuilder.prepareGetListOfGroupsStatement();
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                groups.add(resultSet.getString(1));
            }

            return groups;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
