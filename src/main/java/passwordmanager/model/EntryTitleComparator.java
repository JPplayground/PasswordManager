package passwordmanager.model;

import java.util.Comparator;

/**
 * The {@code EntryTitleComparator} class provides a comparison function for {@link Entry} objects
 * based on their titles.
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
     * Lowercase letters are considered smaller than uppercase letters.
     *
     * @param entry1 the first {@code Entry} to be compared.
     * @param entry2 the second {@code Entry} to be compared.
     * @return a negative integer, zero, or a positive integer as the first argument is less than,
     *         equal to, or greater than the second.
     */
    @Override
    public int compare(Entry entry1, Entry entry2) {
        String title1 = entry1.getTitle();
        String title2 = entry2.getTitle();

        int len1 = title1.length();
        int len2 = title2.length();
        int lim = Math.min(len1, len2);

        for (int k = 0; k < lim; k++) {
            char c1 = title1.charAt(k);
            char c2 = title2.charAt(k);

            if (c1 != c2) {
                // Compare case-sensitive characters
                if (Character.toLowerCase(c1) == Character.toLowerCase(c2)) {
                    return c1 - c2; // Different case but same letter
                } else {
                    return Character.toLowerCase(c1) - Character.toLowerCase(c2);
                }
            }
        }
        return len1 - len2;
    }
}
