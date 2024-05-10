package passwordmanager.livetesting;

import passwordmanager.database.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The {@code EntryGenerator} class is responsible for generating random Entry objects
 * for testing purposes within a password manager application.
 * This class provides a method to generate a specified number of random Entry objects
 * using pre-defined arrays of titles, emails, passwords, usernames, links, and categories.
 *
 * <p>The generated entries are randomly selected from the predefined arrays for each attribute,
 * ensuring variety in the generated data. Timestamps for creation and modification are not
 * included in the generation process and will be set by the caller if needed.
 *
 * @author Josh Patterson
 */
public class EntryGenerator {

    /**
     * Generates a specified number of random Entry objects for testing purposes.
     *
     * @param count the number of Entry objects to generate.
     * @return a list containing the randomly generated Entry objects.
     */
    public static List<Entry> generateEntries(int count) {
        List<Entry> entries = new ArrayList<>();
        Random random = new Random();

        List<String> usedTitles = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            // TITLES MUST BE UNIQUE
            String title;
            do {
                title = TITLES[random.nextInt(TITLES.length)];
            } while (usedTitles.contains(title));

            usedTitles.add(title);

            String email = EMAILS[random.nextInt(EMAILS.length)];
            String password = PASSWORDS[random.nextInt(PASSWORDS.length)];
            String username = USERNAMES[random.nextInt(USERNAMES.length)];
            String link = LINKS[random.nextInt(LINKS.length)];
            String category = CATEGORIES[random.nextInt(CATEGORIES.length)];

            entries.add(new Entry(title, email, password, username, link, category));
        }

        return entries;
    }

    // Predefined arrays for generating random data
    private static final String[] TITLES = {
            "Facebook", "Gmail", "Twitter", "LinkedIn", "Instagram", "Reddit", "Amazon", "Netflix", "Dropbox", "GitHub",
            "Yahoo", "Microsoft", "Google Drive", "Twitch", "Slack", "WhatsApp", "Zoom", "Skype", "Discord", "Pinterest",
            "Tumblr", "Snapchat", "Ebay", "PayPal", "Spotify", "Apple", "PlayStation", "Xbox", "Nintendo", "Steam"
    };

    private static final String[] EMAILS = {
            "user1@example.com", "user2@example.com", "user3@example.com", "user4@example.com", "user5@example.com",
            "john.doe@example.com", "jane.doe@example.com", "testuser@example.com", "info@example.com", "support@example.com",
            "contact@example.com", "admin@example.com", "sales@example.com", "marketing@example.com", "customer@example.com",
            "developer@example.com", "service@example.com", "feedback@example.com", "billing@example.com", "webmaster@example.com",
            "user6@example.com", "user7@example.com", "user8@example.com", "user9@example.com", "user10@example.com",
            "user11@example.com", "user12@example.com", "user13@example.com", "user14@example.com", "user15@example.com"
    };

    private static final String[] PASSWORDS = {
            "password1", "password2", "password3", "password4", "password5",
            "qwerty", "123456", "abc123", "password123", "letmein",
            "monkey", "dragon", "football", "welcome", "login",
            "admin", "starwars", "123abc", "passw0rd", "iloveyou",
            "123456789", "sunshine", "princess", "12345678", "qwerty123",
            "12345", "superman", "password1!", "hello123", "trustno1"
    };

    private static final String[] USERNAMES = {
            "user1", "user2", "user3", "user4", "user5",
            "john_doe", "jane_doe", "testuser", "info", "support",
            "contact", "admin", "sales", "marketing", "customer",
            "developer", "service", "feedback", "billing", "webmaster",
            "user6", "user7", "user8", "user9", "user10",
            "user11", "user12", "user13", "user14", "user15"
    };

    private static final String[] LINKS = {
            "https://www.facebook.com", "https://www.gmail.com", "https://www.twitter.com", null, "https://www.instagram.com",
            null, "https://www.amazon.com", "https://www.netflix.com", null, "https://www.github.com",
            "https://www.yahoo.com", "https://www.microsoft.com", "https://www.google.com/drive", "https://www.twitch.tv", "https://www.slack.com",
            "https://www.whatsapp.com", "https://www.zoom.us", "https://www.skype.com", "https://www.discord.com", "https://www.pinterest.com",
            "https://www.tumblr.com", "https://www.snapchat.com", "https://www.ebay.com", "https://www.paypal.com", "https://www.spotify.com",
            "https://www.apple.com", "https://www.playstation.com", "https://www.xbox.com", "https://www.nintendo.com", "https://www.steam.com"
    };

    private static final String[] CATEGORIES = {
            "Social Media", "Email", "Social Media", "Professional", "Social Media",
            "Social News", "Shopping", "Streaming", "Cloud Storage", "Development",
            "Search", "Technology", "Productivity", "Gaming", "Communication",
            "Messaging", "Video Conferencing", "Communication", "Communication", "Social Media",
            "Blogging", "Messaging", "E-commerce", "Finance", "Streaming Music",
            "Technology", "Gaming", "Gaming", "Gaming", "Gaming"
    };

}
