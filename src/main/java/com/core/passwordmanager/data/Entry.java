package com.core.passwordmanager.data;

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
 * @author Josh Patterson
 */
public class Entry {
    private String title, email, password, username, link, category;
    private Timestamp dateCreated, dateModified;

    /**
     * Constructs a new Entry without date information. This constructor is typically used when
     * the dates are not yet known or not needed.
     *
     * @param title    The title of the entry.
     * @param email    The email associated with the entry.
     * @param password The password of the entry.
     * @param username The username associated with the entry.
     * @param link     A hyperlink associated with the entry, if any.
     * @param category The category to which the entry belongs.
     */
    public Entry(String title, String email, String password, String username, String link, String category) {
        this.title = title;
        this.email = email;
        this.password = password;
        this.username = username;
        this.link = link;
        this.category = category;
    }

    /**
     * Constructs a new Entry with specified date information for creation and modification.
     * This constructor is used when both the creation and modification dates are known at the time
     * of entry creation.
     *
     * @param title        The title of the entry.
     * @param email        The email associated with the entry.
     * @param password     The password of the entry.
     * @param username     The username associated with the entry.
     * @param link         A hyperlink associated with the entry, if any.
     * @param category     The category to which the entry belongs.
     * @param dateCreated  The timestamp representing the date and time when the entry was created.
     * @param dateModified The timestamp representing the date and time when the entry was last modified.
     */
    public Entry(String title, String email, String password, String username, String link, String category, Timestamp dateCreated, Timestamp dateModified) {
        this(title, email, password, username, link, category);
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    /**
     * Returns the title of this entry.
     * @return the title of the entry.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the email associated with this entry.
     * @return the email of the entry.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the password for this entry.
     * @return the password of the entry.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username associated with this entry.
     * @return the username of the entry.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the hyperlink associated with this entry, if any.
     * @return the link of the entry, or null if none.
     */
    public String getLink() {
        return link;
    }

    /**
     * Returns the category to which this entry belongs.
     * @return the category of the entry.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns the creation timestamp of this entry.
     * @return the timestamp when the entry was created.
     * @throws IllegalAccessException if the creation date was not set.
     */
    public Timestamp getDateCreated() throws IllegalAccessException {
        if (dateCreated == null) {
            throw new IllegalAccessException("This Entry was not constructed with a creation date!");
        }
        return dateCreated;
    }

    /**
     * Returns the modification timestamp of this entry.
     * @return the timestamp when the entry was last modified.
     * @throws IllegalAccessException if the modification date was not set.
     */
    public Timestamp getDateModified() throws IllegalAccessException {
        if (dateModified == null) {
            throw new IllegalAccessException("This Entry was not constructed with a modification date!");
        }
        return dateModified;
    }
}