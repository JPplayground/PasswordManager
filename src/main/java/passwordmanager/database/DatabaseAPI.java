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

    private PreparedStatementGenerator preparedStatementGenerator;

    // Singleton Instance
    private static DatabaseAPI instance;

    /**
     * Private constructor to initialize the database connection and create necessary tables.
     * This constructor is called internally to ensure only one instance of the {@code DatabaseAPI}
     * class exists throughout the application.
     */
    private DatabaseAPI() {
        try {
            this.preparedStatementGenerator = new PreparedStatementGenerator();

            try (PreparedStatement createEntryTable = preparedStatementGenerator.prepareEntryTableCreationStatement()) {
                createEntryTable.execute();
            }

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
     * @param entry the entry object to be added.
     */
    public void newEntry(Entry entry) {
        try {
            try (PreparedStatement stmt = preparedStatementGenerator.prepareInsertEntryStatement(entry)) {
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            try (PreparedStatement stmt = preparedStatementGenerator.prepareEntryUpdateStatement(title, email, password, username, link, category)) {
                stmt.execute();
            }
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
            try (PreparedStatement stmt = preparedStatementGenerator.prepareRemoveEntryStatement(title)) {
                stmt.execute();
            }
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
            ResultSet resultSet;
            try (PreparedStatement stmt = preparedStatementGenerator.prepareGetEntryStatement(titleKey)) {
                resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    // Retrieve entry data from the result set
                    String title = resultSet.getString(EntryTableColumns.TITLE.toString());
                    String email = resultSet.getString(EntryTableColumns.EMAIL.toString());
                    String secondaryEmail = resultSet.getString(EntryTableColumns.SECONDARY_EMAIL.toString());
                    String password = resultSet.getString(EntryTableColumns.PASSWORD.toString());
                    String username = resultSet.getString(EntryTableColumns.USERNAME.toString());
                    String phoneNumber = resultSet.getString(EntryTableColumns.PHONE_NUMBER.toString());
                    String link = resultSet.getString(EntryTableColumns.LINK.toString());
                    String category = resultSet.getString(EntryTableColumns.CATEGORY.toString());
                    Timestamp dateCreated = resultSet.getTimestamp(EntryTableColumns.DATE_CREATED.toString());
                    Timestamp dateModified = resultSet.getTimestamp(EntryTableColumns.DATE_MODIFIED.toString());

                    // Create and return the Entry object
                    return new Entry.EntryBuilder(title)
                            .email(email)
                            .password(password)
                            .secondaryEmail(secondaryEmail)
                            .username(username)
                            .link(link)
                            .phoneNumber(phoneNumber)
                            .category(category)
                            .dateCreated(dateCreated)
                            .dateModified(dateModified)
                            .build();

                } else {
                    throw new SQLException("Entry not found with title: " + titleKey);
                }
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
            ResultSet resultSet;
            try (PreparedStatement stmt = preparedStatementGenerator.prepareGetAllEntryTitlesStatement()) {
                resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    entries.add(resultSet.getString(1));
                }
            }
            return entries;
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
            ResultSet resultSet;
            try (PreparedStatement stmt = preparedStatementGenerator.prepareGetListOfGroupsStatement()) {
                resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    groups.add(resultSet.getString(1));
                }
            }
            return groups;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
