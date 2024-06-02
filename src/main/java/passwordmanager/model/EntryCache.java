package passwordmanager.model;

import passwordmanager.database.DatabaseAPI;
import passwordmanager.database.EntryTitleComparator;

import java.util.ArrayList;

/**
 * The {@code EntryCache} class is a singleton that provides a cache for entries in the password manager application.
 * This class retrieves all entries from the database upon initialization and sorts them by title.
 * It also provides methods to access the cached entries.
 *
 * <p>This class ensures that the entries are fetched and sorted efficiently, and provides timing information for these operations.
 *
 * <p>Usage example:
 * <pre>
 * {@code
 * EntryCache cache = EntryCache.getInstance();
 * ArrayList<Entry> entries = cache.getEntries();
 * }
 * </pre>
 *
 * @see DatabaseAPI
 * @see Entry
 * @see EntryTitleComparator
 */
public class EntryCache {

    private static EntryCache instance = null;

    private static DatabaseAPI databaseAPI = null;

    private ArrayList<Entry> entries = null;

    /**
     * Private constructor to initialize the EntryCache.
     */
    private EntryCache() {
        databaseAPI = DatabaseAPI.getInstance();
        updateEntries();
    }

    /**
     * Loads all entries from the database and sorts them by title.
     */
    public void updateEntries() {
        entries = databaseAPI.getAllEntries();
        entries.sort(new EntryTitleComparator());
    }

    /**
     * Returns the singleton instance of the {@code EntryCache} class.
     *
     * @return the singleton instance of the {@code EntryCache}.
     */
    public static EntryCache getInstance() {
        if (instance == null) {
            instance = new EntryCache();
        }
        return instance;
    }

    /**
     * Returns the list of cached entries.
     *
     * @return an {@code ArrayList} containing the cached entries.
     */
    public ArrayList<Entry> getEntries() {
        return entries;
    }
}
