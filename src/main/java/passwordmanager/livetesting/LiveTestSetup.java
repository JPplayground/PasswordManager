package passwordmanager.livetesting;

import passwordmanager.database.DatabaseAPI;
import passwordmanager.database.Entry;

import java.util.List;

/**
 * Helper class for setting up live testing scenarios.
 * This class provides a method to populate a test database with randomly generated entries for live testing.
 *
 * @author Josh Patterson
 */
public class LiveTestSetup {

    /**
     * Populates the test database with randomly generated entries for live testing.
     * This method generates 25 random Entry objects using EntryGenerator class and adds them to the database.
     */
    public static void setup() {

        System.out.println("Live test setup activated.");

        DatabaseAPI databaseAPI = DatabaseAPI.getInstance();

        // Add 25 randomly generated entries to the test database
        List<Entry> entries = EntryGenerator.generateEntries(25);
        for (Entry entry : entries) {
            databaseAPI.newEntry(entry);
        }

        System.out.println("Live test setup finished.");


    }
}