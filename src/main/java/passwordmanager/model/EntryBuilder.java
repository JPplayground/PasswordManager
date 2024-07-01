package passwordmanager.model;

import java.sql.Timestamp;

/**
 * Builder class for creating {@link Entry} instances.
 * This class follows the Builder pattern to facilitate the construction of {@link Entry} objects.
 */
public class EntryBuilder {

    private Entry entry;

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

    /**
     * Constructs an {@link EntryBuilder} with the required title.
     *
     * @param title the title of the entry.
     */
    public EntryBuilder(String title) {
        this.title = title;
    }

    /**
     * Sets the email of the entry.
     *
     * @param email the email of the entry.
     * @return the updated {@link EntryBuilder} instance.
     */
    public EntryBuilder email(String email) {
        this.email = email;
        return this;
    }

    /**
     * Sets the secondary email of the entry.
     *
     * @param secondaryEmail the secondary email of the entry.
     * @return the updated {@link EntryBuilder} instance.
     */
    public EntryBuilder secondaryEmail(String secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
        return this;
    }

    /**
     * Sets the password of the entry.
     *
     * @param password the password of the entry.
     * @return the updated {@link EntryBuilder} instance.
     */
    public EntryBuilder password(String password) {
        this.password = password;
        return this;
    }

    /**
     * Sets the username of the entry.
     *
     * @param username the username of the entry.
     * @return the updated {@link EntryBuilder} instance.
     */
    public EntryBuilder username(String username) {
        this.username = username;
        return this;
    }

    /**
     * Sets the phone number of the entry.
     *
     * @param phoneNumber the phone number of the entry.
     * @return the updated {@link EntryBuilder} instance.
     */
    public EntryBuilder phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    /**
     * Sets the link of the entry.
     *
     * @param link the link of the entry.
     * @return the updated {@link EntryBuilder} instance.
     */
    public EntryBuilder link(String link) {
        this.link = link;
        return this;
    }

    /**
     * Sets the category of the entry.
     *
     * @param category the category of the entry.
     * @return the updated {@link EntryBuilder} instance.
     */
    public EntryBuilder category(String category) {
        this.category = category;
        return this;
    }

    /**
     * Sets the creation timestamp of the entry.
     *
     * @param dateCreated the creation timestamp of the entry.
     * @return the updated {@link EntryBuilder} instance.
     */
    public EntryBuilder dateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    /**
     * Sets the modification timestamp of the entry.
     *
     * @param dateModified the modification timestamp of the entry.
     * @return the updated {@link EntryBuilder} instance.
     */
    public EntryBuilder dateModified(Timestamp dateModified) {
        this.dateModified = dateModified;
        return this;
    }

    /**
     * Builds and returns an {@link Entry} instance with the specified properties.
     *
     * @return the constructed {@link Entry} instance.
     */
    public Entry build() {
        Entry entry = new Entry(title);
        entry.setEmail(email);
        entry.setSecondaryEmail(secondaryEmail);
        entry.setPassword(password);
        entry.setUsername(username);
        entry.setPhoneNumber(phoneNumber);
        entry.setLink(link);
        entry.setCategory(category);
        entry.setDateCreated(dateCreated);
        entry.setDateModified(dateModified);

        return entry;
    }
}
