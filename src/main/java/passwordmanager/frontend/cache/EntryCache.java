package passwordmanager.frontend.cache;

import passwordmanager.backend.DatabaseAPI;
import passwordmanager.backend.local.database.LocalAPI;
import passwordmanager.model.Entry;
import passwordmanager.model.EntryTitleComparator;

import java.util.ArrayList;
import java.util.List;

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
 * @see LocalAPI
 * @see Entry
 * @see EntryTitleComparator
 */
public class EntryCache {

    // TODO: This class is not going to work whenever syncing between a local / remote database is needed
    // Changing from a singleton messes with some UI logic right now so for now we wait to change

    private static DatabaseAPI dbapi = null;

    private static EntryCache instance = null;

    private List<Entry> entries = null;

    /**
     * Private constructor to initialize the EntryCache.
     */
    private EntryCache() {
        dbapi = LocalAPI.getInstance();
        updateEntries();
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
     * Loads all entries from the database and sorts them by title in the class's containing data structure.
     */
    public void updateEntries() {
        if (entries != null) {
            entries.clear();
        }
        entries = dbapi.getAllEntries();
        entries.sort(new EntryTitleComparator());
    }

    /**
     * Returns the list of cached entries.
     *
     * @return a {@code List} containing the cached entries.
     */
    public List<Entry> getEntries() {
        return entries;
    }

    /**
     * Returns a boolean value indicating if an Entry with the supplied title already exists.
     *
     * @param title the title of the entry to check.
     * @return {@code true} if the entry already exists, {@code false} otherwise.
     */
    public boolean contains(String title) {
        for (Entry entry : entries) {
            if (entry.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the list of unique emails in the cached entries.
     *
     * @return a {@code List} of unique emails in the cached entries.
     */
    public List<String> getUniqueEmails() {

        // TODO: I feel like this is not an efficient place to do this

        List<String> emails = new ArrayList<>();
        for (Entry entry : entries) {
            if (!emails.contains(entry.getEmail())) {
                emails.add(entry.getEmail());
            }
        }
        return emails;
    }
}
