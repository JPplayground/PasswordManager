package passwordmanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.stage.Window;
import passwordmanager.database.DatabaseAPI;
import passwordmanager.model.Entry;
import passwordmanager.model.EntryCache;
import passwordmanager.model.SearchResultCache;
import passwordmanager.util.PasswordGenerator;

import java.util.List;
import java.util.logging.Logger;

public class MainWindowController {

    // Add Tab UI Elements
    @FXML
    private TextField titleEntryField, emailEntryField, passwordEntryField, usernameEntryField, groupEntryField, linkEntryField;
    @FXML
    private Button clearBtn, addBtn, generatePasswordBtn;
    @FXML
    ChoiceBox<String> emailChoiceBox, groupChoiceBox;

    // View Tab UI Elements
    @FXML
    TextField searchField;
    @FXML
    MenuButton filterMenuButton;
    @FXML
    VBox searchResultsDisplayVBox;

    // Cache
    private final EntryCache entryCache = EntryCache.getInstance();
    private final SearchResultCache searchResultCache = SearchResultCache.getInstance();

    @FXML
    public void initialize() {
        logger.info("Initializing MainWindowController");

        // Load and display search results in the view tab
        setupSearchResultsDisplay();

        // Set the email choice box options to unique emails in the database
        setUpEmailChoiceBoxOptions();

        setUpSearchFilter();

        // Set up various button callbacks
        addBtn.setOnAction(e -> addButtonCallback());
        clearBtn.setOnAction(e -> clearTextFields());
        generatePasswordBtn.setOnAction(e -> generatePasswordCallback());
        emailChoiceBox.setOnAction(e -> emailChoiceBoxCallback());
    }

    private void setupSearchResultsDisplay() {
        logger.info("Setting up search results display");

        // Clear the search results display
        searchResultsDisplayVBox.getChildren().clear();

        // Get search results from cache
        List<Node> searchResults = searchResultCache.getSearchResults();

        // Add search results to display
        for (Node searchResult : searchResults) {
            searchResultsDisplayVBox.getChildren().add(searchResult);
        }
    }

    private void addButtonCallback() {
        // Get the values from the text fields
        String title = titleEntryField.getText();
        String email = emailEntryField.getText();
        String password = passwordEntryField.getText();
        String username = usernameEntryField.getText();
        String group = groupEntryField.getText();
        String link = linkEntryField.getText();

        // Check required text fields are not empty
        if (title.isEmpty() || email.isEmpty() || password.isEmpty() ) {
            logger.warning("Title and password fields are required");

            // Notifying popup
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Required Information");
            alert.setContentText("Title, email, and password fields are required.");
            // Set the owner of the alert to the main window
            Window window = addBtn.getScene().getWindow();
            alert.initOwner(window);
            alert.show();
        }

        // Check if the title already exists
        else if (EntryCache.getInstance().contains(title)) {
            logger.warning("Entry with title already exists");

            // Notifying popup
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Entry Already Exists");
            alert.setContentText("An entry with the title '" + title + "' already exists.");
            // Set the owner of the alert to the main window
            Window window = addBtn.getScene().getWindow();
            alert.initOwner(window);
            alert.show();
        }

        // TODO: Other preconditions to check before adding an entry

        // Add the entry to the database
        else {
            DatabaseAPI.getInstance().newEntry(new Entry(title, email, password, username, link, group));
            entryCache.updateEntries();
            searchResultCache.refreshSearchResults();
            setupSearchResultsDisplay();
            clearTextFields();

        }
    }

    private void setUpEmailChoiceBoxOptions() {
        List<String> emails = entryCache.getUniqueEmails();
        for (String email : emails) {
            emailChoiceBox.getItems().add(email);
        }
    }

    private void emailChoiceBoxCallback() {
        String selectedEmail = emailChoiceBox.getValue();
        emailEntryField.setText(selectedEmail);

        // TODO: Clear the email choice box selection without clearing the text field
        // This doesn't work! : emailChoiceBox.setValue(null);
    }

    private void generatePasswordCallback() {
        // Generate a random password
        String password = PasswordGenerator.getPassword();
        passwordEntryField.setText(password);
    }

    private void clearTextFields() {
        // Clear the text fields
        titleEntryField.clear();
        emailEntryField.clear();
        passwordEntryField.clear();
        usernameEntryField.clear();
        groupEntryField.clear();
        linkEntryField.clear();
    }

    private void setUpSearchFilter() {
        // TODO: Implement filter feature
        // Disabling until feature is implemented
        filterMenuButton.setDisable(true);
    }

    // Logger
    private static final Logger logger = Logger.getLogger(MainWindowController.class.getName());

}
