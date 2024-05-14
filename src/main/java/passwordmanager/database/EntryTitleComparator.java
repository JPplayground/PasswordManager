package passwordmanager.database;

import java.util.Comparator;

/**
 * Compares two {@link Entry} objects based on their titles.
 */
public class EntryTitleComparator implements Comparator<Entry> {

    @Override
    public int compare(Entry entry1, Entry entry2) {
        return entry1.getTitle().compareTo(entry2.getTitle());
    }

}
