package passwordmanager.backend.local.SQLLite;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import passwordmanager.backend.DatabaseConnection;
import passwordmanager.model.Entry;
import passwordmanager.model.EntryBuilder;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for verifying the functionality of the LocalAPI.
 */
public class LocalAPITests {

    private static LocalAPI localAPI;

    /**
     * Sets up the database connection and initializes the LocalAPI instance before all tests.
     */
    @BeforeAll
    public static void setup() {
        DatabaseConnection.setConnection();
        localAPI = LocalAPI.getInstance();
    }

    /**
     * Tests inserting and removing an entry in the database.
     */
    @Test
    public void testInsertAndRemoveEntry() {
        String title, email, password, username, link, category;
        title = "Example";
        email = "a@a.com";
        password = "password123";
        username = "example";
        link = "www.example.com";
        category = "example";


        Entry entry = new EntryBuilder(title)
                .email(email)
                .password(password)
                .username(username)
                .link(link)
                .category(category)
                .build();


        assertDoesNotThrow(() -> {
            localAPI.newEntry(entry);
        });

        assertDoesNotThrow(() -> {
            localAPI.removeEntry(title);
        });
    }

    /**
     * Tests inserting and removing an entry with empty parameters in the database.
     */
    @Test
    public void testInsertAndRemoveEntryWithEmptyParameters() {
        String title;
        title = "Example";

        Entry entry = new EntryBuilder(title).build();

        assertDoesNotThrow(() -> {
            localAPI.newEntry(entry);
        });

        assertDoesNotThrow(() -> {
            localAPI.removeEntry(title);
        });
    }

    /**
     * Tests retrieving an entry from the database.
     *
     * @throws IllegalAccessException if the entry cannot be accessed.
     */
    @Test
    public void testGetEntry() throws IllegalAccessException {
        String title, email, password, username, link, category;
        title = "Example";
        email = "a@a.com";
        password = "password123";
        username = "example";
        link = "www.example.com";
        category = "example";

        Entry entry = new EntryBuilder(title)
                .email(email)
                .password(password)
                .username(username)
                .link(link)
                .category(category)
                .build();

        localAPI.newEntry(entry);
        entry = localAPI.getEntry(title);

        assertEquals(entry.getTitle(), title);
        assertEquals(entry.getEmail(), email);
        assertEquals(entry.getPassword(), password);
        assertEquals(entry.getUsername(), username);
        assertEquals(entry.getLink(), link);
        assertEquals(entry.getCategory(), category);

        assertInstanceOf(Timestamp.class, entry.getDateCreated());
        assertInstanceOf(Timestamp.class, entry.getDateModified());

        // Cleanup
        localAPI.removeEntry(title);

    }

    /**
     * Tests retrieving all entry titles from the database.
     */
    @Test
    public void testGetEntryTitles() {
        String title1, email1, password1, username1, link1, category1;
        title1 = "Example1";
        email1 = "a@a.com1";
        password1 = "password123";
        username1 = "example1";
        link1 = "www.example.com1";
        category1 = "example1";

        Entry entry1 = new EntryBuilder(title1)
                .email(email1)
                .password(password1)
                .username(username1)
                .link(link1)
                .category(category1)
                .build();

        String title2, email2, password2, username2, link2, category2;
        title2 = "Example2";
        email2 = "a@a.com2";
        password2 = "password456";
        username2 = "example2";
        link2 = "www.example.com2";
        category2 = "example2";

        Entry entry2 = new EntryBuilder(title2)
                .email(email2)
                .password(password2)
                .username(username2)
                .link(link2)
                .category(category2)
                .build();

        localAPI.newEntry(entry1);
        localAPI.newEntry(entry2);

        ArrayList<String> listOfTitles = localAPI.getEntryTitles();

        assertEquals(2, listOfTitles.size());
        assertEquals(title1, listOfTitles.get(0));
        assertEquals(title2, listOfTitles.get(1));

        // Cleanup
        localAPI.removeEntry(title1);
        localAPI.removeEntry(title2);
    }

}
