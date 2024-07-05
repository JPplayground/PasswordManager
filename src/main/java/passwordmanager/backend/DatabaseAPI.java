package passwordmanager.backend;

import passwordmanager.backend.local.database.EntryFields;
import passwordmanager.model.Entry;

import java.util.List;
import java.util.Set;

public interface DatabaseAPI {

    /**
     * Adds a new entry to the database.
     *
     * @param entry the entry object to be added.
     */
    void newEntry(Entry entry);

    /**
     *
     * @param title
     * @param field
     * @param newValue
     */
    void modifyEntry(String title, EntryFields field, String newValue);

    /**
     * Removes an entry from the database.
     *
     * @param title the title of the entry to remove.
     */
    void removeEntry(String title);

    /**
     * Removes an entry from the database.
     *
     * @param entry the entry to be removed.
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
     * @return an {@code ArrayList} containing all entries, or {@code null} if an error occurs.
     */
    List<Entry> getAllEntries();

    /**
     * Retrieves a list of all entry titles from the database.
     *
     * @return an {@code ArrayList} containing all entry titles, or {@code null} if an error occurs.
     */
    List<String> getEntryTitles();

    /**
     * Retrieves a list of all groups from the database.
     *
     * @return A {@code Set<String>} containing all the groups retrieved from the database,
     *         or {@code null} if an SQL exception occurs.
     */
    Set<String> getGroups();

}
