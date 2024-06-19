package passwordmanager.model;

import java.sql.Timestamp;

/**
 * Represents an entry in a password manager application.
 * This class encapsulates all relevant information for an entry including title, email,
 * password, username, link, and category, along with timestamps for creation and modification.
 * This class provides flexibility with multiple constructors to accommodate different use cases
 * where creation and modification dates might or might not be available at the time of instantiation.
 *
 * <p>Instances of this class are immutable, disallowing changes to the entry's details
 * after instantiation. This class provides getters for all properties. If creation or
 * modification dates are not set during object construction, accessing these fields
 * will throw an {@link IllegalAccessException}.
 *
 * <p>Usage example:
 * <pre>
 * {@code
 * Entry entry = new Entry("example title", "email@example.com", "password123", "username", "http://example.com", "example category");
 * System.out.println(entry.getTitle());
 * }
 * </pre>
 *
 * <p>If creation and modification dates are available:
 * <pre>
 * {@code
 * Timestamp now = new Timestamp(System.currentTimeMillis());
 * Entry entry = new Entry("example title", "email@example.com", "password123", "username", "http://example.com", "example category", now, now);
 * System.out.println(entry.getDateCreated());
 * }
 * </pre>
 *
 * @see IllegalAccessException
 */
public class Entry {

    // Only truly required parameter to construct an Entry
    private final String title;

    private String email;
    private String secondaryEmail;
    private String password;
    private String username;
    private String phoneNumber;
    private String link;
    private String category;
    private Timestamp dateCreated;
    private Timestamp dateModified;

    private Entry(String title) {
        this.title = title;
    }

    public static class EntryBuilder {
        // Although implicit, all non-required fields will be null if not set
        private final String title;
        private String email = null;
        private String secondaryEmail = null;
        private String password = null;
        private String username = null;
        private String phoneNumber = null;
        private String link = null;
        private String category = null;
        private Timestamp dateCreated = null;
        private Timestamp dateModified = null;

        public EntryBuilder(String title) {
            this.title = title;
        }

        public EntryBuilder email(String email) {
            this.email = email;
            return this;
        }

        public EntryBuilder secondaryEmail(String secondaryEmail) {
            this.secondaryEmail = secondaryEmail;
            return this;
        }

        public EntryBuilder password(String password) {
            this.password = password;
            return this;
        }

        public EntryBuilder username(String username) {
            this.username = username;
            return this;
        }

        public EntryBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public EntryBuilder link(String link) {
            this.link = link;
            return this;
        }

        public EntryBuilder category(String group) {
            this.category = group;
            return this;
        }

        public EntryBuilder dateCreated(Timestamp dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        public EntryBuilder dateModified(Timestamp dateModified) {
            this.dateModified = dateModified;
            return this;
        }

        public Entry build() {
            Entry entry = new Entry(title);
            entry.email = email;
            entry.secondaryEmail = secondaryEmail;
            entry.password = password;
            entry.username = username;
            entry.phoneNumber = phoneNumber;
            entry.link = link;
            entry.category = category;
            entry.dateCreated = dateCreated;
            entry.dateModified = dateModified;
            return entry;
        }
    }

    /**
     * Returns the title of this entry.
     *
     * @return the title of the entry.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the email associated with this entry.
     *
     * @return the email of the entry.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the secondary email associated with this entry.
     *
     * @return the secondary email of the entry.
     */
    public String getSecondaryEmail() {
        return secondaryEmail;
    }

    /**
     * Returns the password for this entry.
     *
     * @return the password of the entry.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username associated with this entry.
     *
     * @return the username of the entry.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the phone number associated with this entry.
     *
     * @return the phone number of the entry.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the hyperlink associated with this entry, if any.
     *
     * @return the link of the entry, or null if none.
     */
    public String getLink() {
        return link;
    }

    /**
     * Returns the category to which this entry belongs.
     *
     * @return the category of the entry.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns the creation timestamp of this entry.
     *
     * @return the timestamp when the entry was created, or null if not set.
     */
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    /**
     * Returns the modification timestamp of this entry.
     *
     * @return the timestamp when the entry was last modified, or null if not set.
     */
    public Timestamp getDateModified() {
        return dateModified;
    }

    /**
     * Returns a string representation of this Entry.
     * The string representation includes the title, email, username, link, category, and timestamps
     * for creation and modification, if available.
     *
     * @return a string representation of this Entry.
     */
    @Override
    public String toString() {
        StringBuilder entryAsString = new StringBuilder();

        entryAsString.append("Title: ").append(title).append("\n");
        entryAsString.append("\tEmail: ").append(email).append("\n");
        entryAsString.append("\tSecondary Email: ").append(secondaryEmail).append("\n");
        entryAsString.append("\tPassword: ").append(password).append("\n");
        entryAsString.append("\tUsername: ").append(username).append("\n");
        entryAsString.append("\tPhone Number: ").append(phoneNumber).append("\n");
        entryAsString.append("\tLink: ").append(link).append("\n");
        entryAsString.append("\tCategory: ").append(category).append("\n");

        if (dateCreated != null) {
            entryAsString.append("\tDate Created: ").append(dateCreated).append("\n");
        }
        if (dateModified != null) {
            entryAsString.append("\tDate Modified: ").append(dateModified).append("\n");
        }

        return entryAsString.toString();
    }

    /**
     * Returns true if the title of this entry contains the search string, ignoring case.
     *
     * @param search the search string to check for in the title.
     * @return true if the title contains the search string, false otherwise.
     */
    public boolean titleContains(String search) {
        return title.toLowerCase().contains(search.toLowerCase());
    }
}
