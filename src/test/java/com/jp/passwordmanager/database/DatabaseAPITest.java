package com.jp.passwordmanager.database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseAPITest {
    private static DatabaseAPI databaseAPI;

    @BeforeAll
    public static void setup() {
        databaseAPI = DatabaseAPI.getInstance(true);
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


        assertDoesNotThrow(() -> {
            databaseAPI.newEntry(title, email, password, username, link, category);
        });

        assertDoesNotThrow(() -> {
            databaseAPI.removeEntry(title);
        });
    }

    @Test
    public void testInsertAndRemoveEntryWithEmptyParameters() {
        String title, email, password, username, link, category;
        title = "Example";
        email = "a@a.com";
        password = "password123";
        username = "";
        link = "";
        category = "";


        assertDoesNotThrow(() -> {
            databaseAPI.newEntry(title, email, password, username, link, category);
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

        databaseAPI.newEntry(title, email, password, username, link, category);
        Entry entry = databaseAPI.getEntry(title);

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

        String title2, email2, password2, username2, link2, category2;
        title2 = "Example2";
        email2 = "a@a.com2";
        password2 = "password456";
        username2 = "example2";
        link2 = "www.example.com2";
        category2 = "example2";

        databaseAPI.newEntry(title1, email1, password1, username1, link1, category1);
        databaseAPI.newEntry(title2, email2, password2, username2, link2, category2);

        ArrayList<String> listOfTitles = databaseAPI.getListOfEntryTitles();
        assertEquals(2, listOfTitles.size());
        assertEquals(title1, listOfTitles.get(0));
        assertEquals(title2, listOfTitles.get(1));

        // Cleanup
        databaseAPI.removeEntry(title1);
        databaseAPI.removeEntry(title2);
    }

    @Test
    public void testAddGetAndRemoveCommonEmails() {

        String email1, email2;
        email1 = "example1@gmail.com";
        email2 = "example2@gmail.com";

        databaseAPI.addCommonEmail(email1);

        ArrayList<String> emails = databaseAPI.getListOfCommonEmails();
        assertEquals(1, emails.size());

        databaseAPI.addCommonEmail(email2);
        emails = databaseAPI.getListOfCommonEmails();
        assertEquals(2, emails.size());

        assertEquals(email1, emails.get(0));
        assertEquals(email2, emails.get(1));

    }




}
