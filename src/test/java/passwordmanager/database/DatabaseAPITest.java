package passwordmanager.database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import passwordmanager.model.Entry;
import passwordmanager.model.EntryBuilder;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseAPITest {

    private static DatabaseAPI databaseAPI;

    @BeforeAll
    public static void setup() {
        DatabaseConnection.setConnection();
        databaseAPI = DatabaseAPI.getInstance();
    }

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
            databaseAPI.newEntry(entry);
        });

        assertDoesNotThrow(() -> {
            databaseAPI.removeEntry(title);
        });
    }

    @Test
    public void testInsertAndRemoveEntryWithEmptyParameters() {
        String title;
        title = "Example";

        Entry entry = new EntryBuilder(title).build();

        assertDoesNotThrow(() -> {
            databaseAPI.newEntry(entry);
        });

        assertDoesNotThrow(() -> {
            databaseAPI.removeEntry(title);
        });
    }


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

        databaseAPI.newEntry(entry);
        entry = databaseAPI.getEntry(title);

        assertEquals(entry.getTitle(), title);
        assertEquals(entry.getEmail(), email);
        assertEquals(entry.getPassword(), password);
        assertEquals(entry.getUsername(), username);
        assertEquals(entry.getLink(), link);
        assertEquals(entry.getCategory(), category);

        assertInstanceOf(Timestamp.class, entry.getDateCreated());
        assertInstanceOf(Timestamp.class, entry.getDateModified());

        // Cleanup
        databaseAPI.removeEntry(title);

    }

    @Test
    public void testGetListOfEntryTitles() {
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

        databaseAPI.newEntry(entry1);
        databaseAPI.newEntry(entry2);

        ArrayList<String> listOfTitles = databaseAPI.getListOfEntryTitles();

        assertEquals(2, listOfTitles.size());
        assertEquals(title1, listOfTitles.get(0));
        assertEquals(title2, listOfTitles.get(1));

        // Cleanup
        databaseAPI.removeEntry(title1);
        databaseAPI.removeEntry(title2);
    }

}
