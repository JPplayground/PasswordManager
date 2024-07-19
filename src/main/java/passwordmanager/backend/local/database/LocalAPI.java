package passwordmanager.backend.local.database;

import passwordmanager.backend.DatabaseAPI;
import passwordmanager.model.Entry;
import passwordmanager.model.EntryBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@code DatabaseAPI} class provides methods for interacting with a database
 * to perform various operations related to managing entries within the application
 *
 * <p>Instances of this class are obtained using the {@link #getInstance()} method.
 * This class ensures safe database operations and encapsulates SQL statements for
 * adding, modifying, removing, and retrieving data related to entries.
 *
 * <p>Note: This class follows the singleton design pattern to ensure only one instance
 * is created throughout the application.
 *
 * <p>Usage example:
 * <pre>
 * {@code
 * DatabaseAPI dbAPI = DatabaseAPI.getInstance();
 * dbAPI.newEntry("example title", "email@example.com", "password123", "username", "http://example.com", "example category");
 * }
 * </pre>
 *
 * @see Entry
 */
public class LocalAPI implements DatabaseAPI {

    private PreparedStatementGenerator preparedStatementGenerator;

    // Singleton Instance
    private static LocalAPI instance;

    /**
     * Private constructor to initialize the database connection and create necessary tables.
     * This constructor is called internally to ensure only one instance of the {@code DatabaseAPI}
     * class exists throughout the application.
     */
    private LocalAPI() {
        try {
            this.preparedStatementGenerator = new PreparedStatementGenerator();

            try (PreparedStatement createEntryTable = preparedStatementGenerator. prepareEntryTableCreationStatement()) {
                createEntryTable.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the singleton instance of the {@code DatabaseAPI} class.
     *
     * @return the singleton instance of the {@code DatabaseAPI}.
     */
    public static LocalAPI getInstance() {
        if (instance == null) {
            instance = new LocalAPI();
        }
        return instance;
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public void newEntry(Entry entry) {
        try {
            try (PreparedStatement stmt = preparedStatementGenerator.prepareInsertEntryStatement(entry)) {
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public void modifyEntry(String title, EntryFields field, String newValue) {
        String email = null;
        String secondaryEmail = null;
        String password = null;
        String username = null;
        String phoneNumber = null;
        String link = null;
        String category = null;

        switch (field) {
            case EMAIL -> email = newValue;
            case SECONDARY_EMAIL -> secondaryEmail = newValue;
            case PASSWORD -> password = newValue;
            case USERNAME -> username = newValue;
            case PHONE_NUMBER -> phoneNumber = newValue;
            case LINK -> link = newValue;
            case CATEGORY -> category = newValue;
            default -> throw new IllegalArgumentException("Invalid field: " + field);
        }

        try {
            try (PreparedStatement stmt = preparedStatementGenerator.prepareEntryUpdateStatement(title, email, secondaryEmail, password, username, phoneNumber, link, category)) {
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public void removeEntry(String title) {
        try {
            try (PreparedStatement stmt = preparedStatementGenerator.prepareRemoveEntryStatement(title)) {
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public void removeEntry(Entry entry) {
        try {
            // Uses removeEntry(String) by getting title from entry
            try (PreparedStatement stmt = preparedStatementGenerator.prepareRemoveEntryStatement(entry.getTitle())) {
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public Entry getEntry(String titleKey) {
        try {
            ResultSet resultSet;
            try (PreparedStatement stmt = preparedStatementGenerator.prepareGetEntryStatement(titleKey)) {
                resultSet = stmt.executeQuery();
                if (resultSet.next()) {

                    // Retrieve entry data from the result set
                    String title = resultSet.getString(EntryFields.TITLE.toString());
                    String email = resultSet.getString(EntryFields.EMAIL.toString());
                    String secondaryEmail = resultSet.getString(EntryFields.SECONDARY_EMAIL.toString());
                    String password = resultSet.getString(EntryFields.PASSWORD.toString());
                    String username = resultSet.getString(EntryFields.USERNAME.toString());
                    String phoneNumber = resultSet.getString(EntryFields.PHONE_NUMBER.toString());
                    String link = resultSet.getString(EntryFields.LINK.toString());
                    String category = resultSet.getString(EntryFields.CATEGORY.toString());
                    Timestamp dateCreated = resultSet.getTimestamp(EntryFields.DATE_CREATED.toString());
                    Timestamp dateModified = resultSet.getTimestamp(EntryFields.DATE_MODIFIED.toString());

                    // Create and return the Entry object
                    return new EntryBuilder(title)
                            .email(email)
                            .password(password)
                            .secondaryEmail(secondaryEmail)
                            .username(username)
                            .link(link)
                            .phoneNumber(phoneNumber)
                            .category(category)
                            .dateCreated(dateCreated)
                            .dateModified(dateModified)
                            .build();

                } else {
                    throw new SQLException("Entry not found with title: " + titleKey);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public ArrayList<Entry> getAllEntries() {
        ArrayList<String> entryTitles = getEntryTitles();
        ArrayList<Entry> entries = new ArrayList<>();

        for (String title : entryTitles) {
            entries.add(getEntry(title));
        }

        return entries;
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public ArrayList<String> getEntryTitles() {
        ArrayList<String> entries = new ArrayList<>();
        try {
            ResultSet resultSet;
            try (PreparedStatement stmt = preparedStatementGenerator.prepareGetAllEntryTitlesStatement()) {
                resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    entries.add(resultSet.getString(1));
                }
            }
            return entries;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public Set<String> getGroups() {
        Set<String> groups = new HashSet<>();
        try {
            ResultSet resultSet;
            try (PreparedStatement stmt = preparedStatementGenerator.prepareGetListOfGroupsStatement()) {
                resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    groups.add(resultSet.getString(1));
                }
            }
            return groups;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
