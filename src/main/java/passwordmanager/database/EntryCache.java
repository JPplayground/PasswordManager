package passwordmanager.database;

import org.h2.store.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class EntryCache {

    private static EntryCache instance = null;

    private static DatabaseAPI databaseAPI = null;

    private ArrayList<Entry> entries = null;

    private EntryCache() {

        databaseAPI = DatabaseAPI.getInstance();

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

    public static EntryCache getInstance() {
        if (instance == null) {
            instance = new EntryCache();
        }
        return instance;
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }
}
