package passwordmanager.database;

import passwordmanager.model.Entry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The {@code PreparedStatementGenerator} class is responsible for creating and preparing SQL {@link PreparedStatement}s
 * for various database operations related to managing entries and common email addresses within a password manager application.
 * This class ensures SQL statements are safely prepared to avoid SQL injection vulnerabilities and manages SQL commands
 * for inserting, updating, deleting, and querying data.
 *
 * <p>Instances of this class require an active {@link Connection} to the database, which must be provided at the time
 * of construction. This connection is used to prepare all SQL statements. It is assumed that management of the
 * database connection (such as opening and closing) is handled externally, allowing this class to focus solely
 * on preparing statements based on provided parameters.
 *
 * <p>Each method in this class prepares a specific type of SQL statement, such as inserting new entries, updating
 * existing entries, deleting entries, or querying the database for specific data. Prepared statements are returned
 * to the caller, ready for execution, which allows for flexibility in transaction management and further processing.
 *
 * <p>Usage of this class helps centralize SQL statement preparation, contributing to cleaner code and separating
 * database access concerns from business logic.
 *
 * <p>Usage example:
 * <pre>
 * {@code
 * PreparedStatementGenerator generator = new PreparedStatementGenerator();
 * PreparedStatement stmt = generator.prepareInsertEntryStatement(entry);
 * stmt.execute();
 * }
 * </pre>
 *
 * @see PreparedStatement
 * @see Connection
 */
public class PreparedStatementGenerator {

    private final Connection connection;

    /**
     * Constructs an instance of {@code PreparedStatementGenerator}.
     * Retrieves the database connection from {@link DatabaseConnection}.
     */
    public PreparedStatementGenerator() {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Prepares a {@code PreparedStatement} for creating the entries table if it does not already exist.
     * This method constructs an SQL statement to create a table with various fields necessary for storing entry data,
     * including title, email, password, username, link, category, date created, and date modified.
     * The title field is set as the primary key.
     *
     * @return a {@code PreparedStatement} that, when executed, will ensure the entries table exists with the correct schema.
     * @throws SQLException if there is an error during the database access or query preparation.
     */
    public PreparedStatement prepareEntryTableCreationStatement() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + DatabaseConstants.ENTRIES_TABLE_NAME + "(" +
                EntryTableColumns.TITLE + " VARCHAR(255) PRIMARY KEY, " +
                EntryTableColumns.EMAIL + " VARCHAR(255), " +
                EntryTableColumns.SECONDARY_EMAIL + " VARCHAR(255), " +
                EntryTableColumns.PASSWORD + " VARCHAR(255), " +
                EntryTableColumns.USERNAME + " VARCHAR(255), " +
                EntryTableColumns.PHONE_NUMBER + " VARCHAR(255), " +
                EntryTableColumns.LINK + " VARCHAR(255), " +
                EntryTableColumns.CATEGORY + " VARCHAR(255), " +
                EntryTableColumns.DATE_CREATED + " DATE, " +
                EntryTableColumns.DATE_MODIFIED + " DATE);";

        return this.connection.prepareStatement(sql);
    }

    /**
     * Prepares a {@code PreparedStatement} for inserting a new entry into the entries table.
     * This method constructs the SQL query necessary to perform the insertion, setting up placeholders
     * for the values to prevent SQL injection. The statement includes setting the current time for
     * both {@code DATE_CREATED} and {@code DATE_MODIFIED} fields.
     *
     * @param entry the entry object containing the data to be inserted into the database.
     * @return a {@code PreparedStatement} object ready for execution to insert the entry data.
     * @throws SQLException if there is an error during the database access or query preparation.
     */
    public PreparedStatement prepareInsertEntryStatement(Entry entry) throws SQLException {
        String sql = "INSERT INTO " + DatabaseConstants.ENTRIES_TABLE_NAME + " (" +
                EntryTableColumns.TITLE + ", " +
                EntryTableColumns.EMAIL + ", " +
                EntryTableColumns.SECONDARY_EMAIL + ", " +
                EntryTableColumns.PASSWORD + ", " +
                EntryTableColumns.USERNAME + ", " +
                EntryTableColumns.PHONE_NUMBER + ", " +
                EntryTableColumns.LINK + ", " +
                EntryTableColumns.CATEGORY + ", " +
                EntryTableColumns.DATE_CREATED + ", " +
                EntryTableColumns.DATE_MODIFIED + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, datetime('now'), datetime('now'))";

        PreparedStatement stmt = this.connection.prepareStatement(sql);
        stmt.setString(1, entry.getTitle());
        stmt.setString(2, entry.getEmail());
        stmt.setString(3, entry.getSecondaryEmail());
        stmt.setString(4, entry.getPassword());
        stmt.setString(5, entry.getUsername());
        stmt.setString(6, entry.getPhoneNumber());
        stmt.setString(7, entry.getLink());
        stmt.setString(8, entry.getCategory());
        return stmt;
    }

    /**
     * Prepares a {@code PreparedStatement} for updating an existing entry in the entries table based on the title.
     * This method allows selective updates where only specified fields (non-null parameters) are updated.
     * The {@code DATE_MODIFIED} field is automatically set to the current timestamp to reflect the update time.
     *
     * <p>Parameters that do not require updating should be passed as {@code null}.
     *
     * @param title     the title of the entry to identify the record to update; must not be null.
     * @param email     the new email to update, or null if no update is needed.
     * @param password  the new password to update, or null if no update is needed.
     * @param username  the new username to update, or null if no update is needed.
     * @param link      the new link to update, or null if no update is needed.
     * @param category  the new category to update, or null if no update is needed.
     * @return a {@code PreparedStatement} object that contains the SQL query with placeholders.
     *         This prepared statement can then be executed after setting the appropriate parameter values.
     * @throws SQLException if there is an error during the database access or query preparation.
     */
    public PreparedStatement prepareEntryUpdateStatement(String title, String email, String password, String username, String link, String category) throws SQLException {
        StringBuilder sql = new StringBuilder("UPDATE " + DatabaseConstants.ENTRIES_TABLE_NAME + " SET ");

        if (email != null) {
            sql.append(EntryTableColumns.EMAIL + " = ?, ");
        }
        if (password != null) {
            sql.append(EntryTableColumns.PASSWORD + " = ?, ");
        }
        if (username != null) {
            sql.append(EntryTableColumns.USERNAME + " = ?, ");
        }
        if (link != null) {
            sql.append(EntryTableColumns.LINK + " = ?, ");
        }
        if (category != null) {
            sql.append(EntryTableColumns.CATEGORY + " = ?, ");
        }

        sql.append(EntryTableColumns.DATE_MODIFIED + " = datetime('now')");
        sql.append(" WHERE " + EntryTableColumns.TITLE + " = ?");

        PreparedStatement pstmt = this.connection.prepareStatement(sql.toString());
        int paramIndex = 1;

        if (email != null) {
            pstmt.setString(paramIndex++, email);
        }
        if (password != null) {
            pstmt.setString(paramIndex++, password);
        }
        if (username != null) {
            pstmt.setString(paramIndex++, username);
        }
        if (link != null) {
            pstmt.setString(paramIndex++, link);
        }
        if (category != null) {
            pstmt.setString(paramIndex++, category);
        }

        pstmt.setString(paramIndex, title);

        return pstmt;
    }

    /**
     * Prepares a {@code PreparedStatement} for deleting an entry from the entries table based on the title.
     * This method constructs an SQL command to remove a specific record where the title matches the given parameter.
     *
     * @param title the title of the entry that identifies the record to be deleted.
     * @return a {@code PreparedStatement} object that can be executed to delete the specified entry from the database.
     *         The statement includes a single placeholder for the title of the entry to be deleted.
     * @throws SQLException if there is an error during the database access or the query preparation process.
     */
    public PreparedStatement prepareRemoveEntryStatement(String title) throws SQLException {
        String sql = "DELETE FROM " + DatabaseConstants.ENTRIES_TABLE_NAME +
                " WHERE " + EntryTableColumns.TITLE + " = ?";

        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, title);
        return pstmt;
    }

    /**
     * Prepares a {@code PreparedStatement} to retrieve a specific entry from the entries table based on the title.
     * This method constructs an SQL query to select all columns from the entries table where the title matches
     * the specified parameter.
     *
     * @param title the title of the entry to retrieve.
     * @return a {@code PreparedStatement} that can be executed to fetch the entry data from the database.
     *         The statement includes a placeholder for the title of the entry.
     * @throws SQLException if there is an error during database access or query preparation.
     */
    public PreparedStatement prepareGetEntryStatement(String title) throws SQLException {
        String sql = "SELECT * FROM " + DatabaseConstants.ENTRIES_TABLE_NAME + " WHERE " + EntryTableColumns.TITLE + " = ?";

        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, title);
        return pstmt;
    }

    /**
     * Prepares a {@code PreparedStatement} to retrieve the titles of all entries from the entries table.
     * This method constructs an SQL query to select only the title column from the entries table.
     *
     * @return a {@code PreparedStatement} that can be executed to fetch all entry titles from the database.
     * @throws SQLException if there is an error during database access or query preparation.
     */
    public PreparedStatement prepareGetAllEntryTitlesStatement() throws SQLException {
        String sql = "SELECT " + EntryTableColumns.TITLE + " FROM " + DatabaseConstants.ENTRIES_TABLE_NAME;

        return this.connection.prepareStatement(sql);
    }

    /**
     * Prepares a {@code PreparedStatement} to retrieve a list of groups from the database.
     * This method constructs an SQL query that selects all distinct categories from the entries table.
     *
     * @return A {@code PreparedStatement} object that, when executed, will return a {@code ResultSet}
     *         containing all distinct categories from the entries table.
     * @throws SQLException if there is an error during database access or query preparation.
     */
    public PreparedStatement prepareGetListOfGroupsStatement() throws SQLException {
        String sql = "SELECT DISTINCT " + EntryTableColumns.CATEGORY + " FROM " + DatabaseConstants.ENTRIES_TABLE_NAME;

        return this.connection.prepareStatement(sql);
    }
}
