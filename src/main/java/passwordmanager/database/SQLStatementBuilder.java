package passwordmanager.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The {@code SQLStatementBuilder} class is responsible for creating and preparing SQL {@link PreparedStatement}s
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
 * @author Josh Patterson
 * @see PreparedStatement
 * @see Connection
 */
public class SQLStatementBuilder {

    private final Connection connection;

    /**
     * Constructs an instance of {@code SQLStatementBuilder}.
     *
     */
    public SQLStatementBuilder() {
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
                DatabaseConstants.EntryColumns.TITLE + " VARCHAR(255) PRIMARY KEY, " +
                DatabaseConstants.EntryColumns.EMAIL + " VARCHAR(255), " +
                DatabaseConstants.EntryColumns.PASSWORD + " VARCHAR(255), " +
                DatabaseConstants.EntryColumns.USERNAME + " VARCHAR(255), " +
                DatabaseConstants.EntryColumns.LINK + " VARCHAR(255), " +
                DatabaseConstants.EntryColumns.CATEGORY + " VARCHAR(255), " +
                DatabaseConstants.EntryColumns.DATE_CREATED + " DATE, " +
                DatabaseConstants.EntryColumns.DATE_MODIFIED + " DATE);";

        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        return pstmt;
    }

    /**
     * Prepares a {@code PreparedStatement} for creating the common emails table if it does not already exist.
     * This method constructs an SQL statement to create a table specifically for storing common email addresses,
     * with an auto-incrementing integer ID as the primary key and a VARCHAR field for the email.
     *
     * @return a {@code PreparedStatement} that, when executed, will ensure the common emails table exists with the correct schema.
     * @throws SQLException if there is an error during the database access or query preparation.
     */
    public PreparedStatement prepareCommonEmailsTableCreationStatement() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + DatabaseConstants.COMMON_EMAILS_TABLE_NAME + "(" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " + DatabaseConstants.COMMON_EMAILS_COLUMN_NAME + " VARCHAR(255));";

        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        return pstmt;

    }

    /**
     * Prepares a {@code PreparedStatement} for inserting a new entry into the entries table.
     * This method constructs the SQL query necessary to perform the insertion, setting up placeholders
     * for the values to prevent SQL injection. The statement includes setting the current time for
     * both {@code DATE_CREATED} and {@code DATE_MODIFIED} fields.
     *
     * @param title the title of the entry.
     * @param email the email associated with the entry.
     * @param password the password of the entry.
     * @param username the username associated with the entry.
     * @param link a hyperlink associated with the entry, if any.
     * @param category the category to which the entry belongs.
     * @return a {@code PreparedStatement} object containing the SQL query with placeholders
     *         ready to be set with actual values.
     * @throws SQLException if there is an error during the database access or query preparation.
     */
    public PreparedStatement prepareInsertEntryStatement(String title, String email, String password, String username, String link, String category) throws SQLException {

        String sql = "INSERT INTO " + DatabaseConstants.ENTRIES_TABLE_NAME + " (" +
                DatabaseConstants.EntryColumns.TITLE + ", " +
                DatabaseConstants.EntryColumns.EMAIL + ", " +
                DatabaseConstants.EntryColumns.PASSWORD + ", " +
                DatabaseConstants.EntryColumns.USERNAME + ", " +
                DatabaseConstants.EntryColumns.LINK + ", " +
                DatabaseConstants.EntryColumns.CATEGORY + ", " +
                DatabaseConstants.EntryColumns.DATE_CREATED + ", " +
                DatabaseConstants.EntryColumns.DATE_MODIFIED + ") VALUES (?, ?, ?, ?, ?, ?, NOW(), NOW())";

        PreparedStatement stmt = this.connection.prepareStatement(sql);
        stmt.setString(1, title);
        stmt.setString(2, email);
        stmt.setString(3, password);
        stmt.setString(4, username);
        stmt.setString(5, link);
        stmt.setString(6, category);
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
            sql.append(DatabaseConstants.EntryColumns.EMAIL + " = ?, ");
        }
        if (password != null) {
            sql.append(DatabaseConstants.EntryColumns.PASSWORD + " = ?, ");
        }
        if (username != null) {
            sql.append(DatabaseConstants.EntryColumns.USERNAME + " = ?, ");
        }
        if (link != null) {
            sql.append(DatabaseConstants.EntryColumns.LINK + " = ?, ");
        }
        if (category != null) {
            sql.append(DatabaseConstants.EntryColumns.CATEGORY + " = ?, ");
        }

        sql.append(DatabaseConstants.EntryColumns.DATE_MODIFIED + " = NOW()");

        sql.append(" WHERE " + DatabaseConstants.EntryColumns.TITLE + " = ?");


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
                " WHERE " + DatabaseConstants.EntryColumns.TITLE + " = ?";

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
        String sql = "SELECT * FROM " + DatabaseConstants.ENTRIES_TABLE_NAME + " WHERE " + DatabaseConstants.EntryColumns.TITLE
                + " = ?";

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
        String sql = "SELECT " + DatabaseConstants.EntryColumns.TITLE + " FROM " + DatabaseConstants.ENTRIES_TABLE_NAME;

        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        return pstmt;
    }

    /**
     * Prepares a {@code PreparedStatement} to insert a new email into the common emails table.
     * This method constructs an SQL command to add a new email to a designated common emails table.
     *
     * @param email the email address to be added to the common emails table.
     * @return a {@code PreparedStatement} that can be executed to insert the email into the database.
     *         The statement includes a placeholder for the email value.
     * @throws SQLException if there is an error during database access or query preparation.
     */
    public PreparedStatement prepareAddCommonEmailStatement(String email) throws SQLException {
        String sql = "INSERT INTO " + DatabaseConstants.COMMON_EMAILS_TABLE_NAME + " (" + DatabaseConstants.COMMON_EMAILS_COLUMN_NAME + ") VALUES (?)";

        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, email);
        return pstmt;
    }

    /**
     * Prepares a {@code PreparedStatement} to retrieve a list of common emails from the database.
     * This method constructs an SQL query that selects all emails from the specified common emails table.
     * The SQL query is constructed using constants from the {@code DatabaseConfig} class to ensure
     * consistency and to facilitate changes in the database schema configuration.
     *
     * @return A {@code PreparedStatement} object that, when executed, will return a {@code ResultSet}
     *         containing all the emails from the common emails table.
     * @throws SQLException if there is an error during database access or query preparation.
     */
    public PreparedStatement prepareGetListOfCommonEmailsStatement() throws SQLException {
        String sql = "SELECT " + DatabaseConstants.COMMON_EMAILS_COLUMN_NAME + " FROM " + DatabaseConstants.COMMON_EMAILS_TABLE_NAME;

        PreparedStatement stmt = this.connection.prepareStatement(sql);
        return stmt;
    }

}
