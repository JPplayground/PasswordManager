package passwordmanager.backend;

import passwordmanager.model.Entry;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Interface for generating prepared statements for database operations in a password manager application.
 * This interface is designed to be adaptable to various SQL dialects and database engines.
 */
public interface PreparedStatementGenerator {

    /**
     * Prepares a statement to create the entry table in the database.
     *
     * @return a PreparedStatement to create the entry table.
     * @throws SQLException if a database access error occurs.
     */
    PreparedStatement prepareEntryTableCreationStatement() throws SQLException;

    /**
     * Prepares a statement to insert a new entry into the entry table.
     *
     * @param entry the Entry object containing the data to be inserted.
     * @return a PreparedStatement to insert a new entry.
     * @throws SQLException if a database access error occurs.
     */
    PreparedStatement prepareInsertEntryStatement(Entry entry) throws SQLException;

    /**
     * Prepares a statement to update an existing entry in the entry table.
     *
     * @param title the title of the entry to be updated.
     * @param email the new email for the entry.
     * @param secondaryEmail the new secondary email for the entry.
     * @param password the new password for the entry.
     * @param username the new username for the entry.
     * @param phoneNumber the new phone number for the entry.
     * @param link the new link for the entry.
     * @param category the new category for the entry.
     * @return a PreparedStatement to update an existing entry.
     * @throws SQLException if a database access error occurs.
     */
    PreparedStatement prepareEntryUpdateStatement(String title, String email, String secondaryEmail, String password, String username, String phoneNumber, String link, String category) throws SQLException;

    /**
     * Prepares a statement to remove an entry from the entry table.
     *
     * @param title the title of the entry to be removed.
     * @return a PreparedStatement to remove an entry.
     * @throws SQLException if a database access error occurs.
     */
    PreparedStatement prepareRemoveEntryStatement(String title) throws SQLException;

    /**
     * Prepares a statement to retrieve an entry from the entry table.
     *
     * @param title the title of the entry to be retrieved.
     * @return a PreparedStatement to retrieve an entry.
     * @throws SQLException if a database access error occurs.
     */
    PreparedStatement prepareGetEntryStatement(String title) throws SQLException;

    /**
     * Prepares a statement to retrieve all entry titles from the entry table.
     *
     * @return a PreparedStatement to retrieve all entry titles.
     * @throws SQLException if a database access error occurs.
     */
    PreparedStatement prepareGetAllEntryTitlesStatement() throws SQLException;

    /**
     * Prepares a statement to retrieve a list of groups from the database.
     *
     * @return a PreparedStatement to retrieve a list of groups.
     * @throws SQLException if a database access error occurs.
     */
    PreparedStatement prepareGetListOfGroupsStatement() throws SQLException;
}
