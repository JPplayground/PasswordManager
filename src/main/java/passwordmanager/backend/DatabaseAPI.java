package passwordmanager.backend;

import passwordmanager.model.Entry;

import java.util.List;
import java.util.Set;

/**
 * The {@code DatabaseAPI} interface defines the methods required for interacting with the database
 * to perform CRUD (Create, Read, Update, Delete) operations on {@link Entry} objects. Implementations
 * of this interface will handle the actual database interactions.
 *
 * <p>Usage example:
 * <pre>
 * {@code
 * DatabaseAPI database = new DatabaseImplementation();
 * database.newEntry(new Entry("Example Title", "username", "password"));
 * Entry entry = database.getEntry("Example Title");
 * database.modifyEntry("Example Title", EntryFields.USERNAME, "newUsername");
 * database.removeEntry("Example Title");
 * }
 * </pre>
 *
 * @see Entry
 * @see EntryFields
 */
public interface DatabaseAPI {

    /**
     * Adds a new entry to the database.
     *
     * @param entry the {@code Entry} object to be added.
     */
    void newEntry(Entry entry);

    /**
     * Modifies an existing entry in the database.
     *
     * @param title the title of the entry to be modified.
     * @param field the {@code EntryFields} enum value indicating which field to modify.
     * @param newValue the new value to set for the specified field.
     */
    void modifyEntry(String title, EntryFields field, String newValue);

    /**
     * Removes an entry from the database based on its title.
     *
     * @param title the title of the entry to remove.
     */
    void removeEntry(String title);

    /**
     * Removes an entry from the database.
     *
     * @param entry the {@code Entry} object to be removed.
     */
    void removeEntry(Entry entry);

    /**
     * Retrieves an entry from the database based on its title.
     *
     * @param titleKey the title of the entry to retrieve.
     * @return the {@code Entry} object corresponding to the title, or {@code null} if not found.
     */
    Entry getEntry(String titleKey);

    /**
     * Retrieves all entries from the database.
     *
     * @return a {@code List} containing all {@code Entry} objects, or {@code null} if an error occurs.
     */
    List<Entry> getAllEntries();

    /**
     * Retrieves a list of all entry titles from the database.
     *
     * @return a {@code List<String>} containing all entry titles, or {@code null} if an error occurs.
     */
    List<String> getEntryTitles();

    /**
     * Retrieves a list of all groups from the database.
     *
     * @return a {@code Set<String>} containing all the groups retrieved from the database,
     *         or {@code null} if an SQL exception occurs.
     */
    Set<String> getGroups();
}
