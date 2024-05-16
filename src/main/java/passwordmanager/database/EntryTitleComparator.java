package passwordmanager.database;

import passwordmanager.model.Entry;

import java.util.Comparator;

/**
 * The {@code EntryTitleComparator} class provides a comparison function for {@link Entry} objects
 * based on their titles. This class implements the {@link Comparator} interface to allow sorting
 * of entries by their titles in alphabetical order.
 *
 * <p>Usage example:
 * <pre>
 * {@code
 * List<Entry> entries = ...;
 * Collections.sort(entries, new EntryTitleComparator());
 * }
 * </pre>
 *
 * @see Entry
 */
public class EntryTitleComparator implements Comparator<Entry> {

    /**
     * Compares two {@link Entry} objects based on their titles.
     *
     * @param entry1 the first {@code Entry} to be compared.
     * @param entry2 the second {@code Entry} to be compared.
     * @return a negative integer, zero, or a positive integer as the first argument is less than,
     *         equal to, or greater than the second.
     */
    @Override
    public int compare(Entry entry1, Entry entry2) {
        return entry1.getTitle().compareTo(entry2.getTitle());
    }
}
