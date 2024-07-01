package passwordmanager.model;

import java.sql.Timestamp;

/**
 * Represents an entry in the password manager.
 * This class stores various attributes of an entry such as title, email, password, username, etc.
 * It is primarily intended to be used with the {@link EntryBuilder} class to facilitate construction.
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

    /**
     * Constructs an Entry with the specified title.
     *
     * @param title the title of the entry.
     */
    public Entry(String title) {
        this.title = title;
    }

    /**
     * @return the title of the entry.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the email of the entry.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the secondary email of the entry.
     */
    public String getSecondaryEmail() {
        return secondaryEmail;
    }

    /**
     * @return the password of the entry.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the username of the entry.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the phone number of the entry.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @return the link of the entry, or null if none.
     */
    public String getLink() {
        return link;
    }

    /**
     * @return the category of the entry.
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return the timestamp when the entry was created, or null if not set.
     */
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    /**
     * @return the timestamp when the entry was last modified, or null if not set.
     */
    public Timestamp getDateModified() {
        return dateModified;
    }

    /**
     * Sets the email of the entry.
     *
     * @param email the email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the secondary email of the entry.
     *
     * @param secondaryEmail the secondary email to set.
     */
    public void setSecondaryEmail(String secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
    }

    /**
     * Sets the password of the entry.
     *
     * @param password the password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the username of the entry.
     *
     * @param username the username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the phone number of the entry.
     *
     * @param phoneNumber the phone number to set.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets the link of the entry.
     *
     * @param link the link to set.
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Sets the category of the entry.
     *
     * @param category the category to set.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Sets the creation timestamp of the entry.
     *
     * @param dateCreated the creation timestamp to set.
     */
    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Sets the modification timestamp of the entry.
     *
     * @param dateModified the modification timestamp to set.
     */
    public void setDateModified(Timestamp dateModified) {
        this.dateModified = dateModified;
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
