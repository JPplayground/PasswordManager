package passwordmanager.backend.livetesting;

import passwordmanager.backend.local.database.LocalAPI;
import passwordmanager.model.Entry;

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

        LocalAPI localAPI = LocalAPI.getInstance();

        // Add 25 randomly generated entries to the test database
        List<Entry> entries = EntryGenerator.generateEntries(50);
        for (Entry entry : entries) {
            localAPI.newEntry(entry);
        }

        System.out.println("Live test setup finished.");


    }
}