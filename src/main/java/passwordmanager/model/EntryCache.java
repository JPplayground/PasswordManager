package passwordmanager.model;

import passwordmanager.database.DatabaseAPI;
import passwordmanager.database.EntryTitleComparator;

import java.time.Duration;
import java.time.Instant;
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
        loadAllEntries();
    }

    /**
     * Loads all entries from the database and sorts them by title.
     */
    public void loadAllEntries() {
        // Timing get all entries
        Instant start = Instant.now();
        entries = databaseAPI.getAllEntries();
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Get all entries operation took " + timeElapsed.toMillis() + " milliseconds");

        // Timing sort entries
        start = Instant.now();
        entries.sort(new EntryTitleComparator());
        end = Instant.now();
        timeElapsed = Duration.between(start, end);
        System.out.println("Sort entries operation took " + timeElapsed.toMillis() + " milliseconds");
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
