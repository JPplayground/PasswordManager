package passwordmanager.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.stage.Window;
import passwordmanager.database.DatabaseAPI;
import passwordmanager.model.Entry;
import passwordmanager.model.EntryBuilder;
import passwordmanager.model.EntryCache;
import passwordmanager.model.SearchResultCache;
import passwordmanager.util.PasswordGenerator;

import java.util.List;
import java.util.logging.Logger;

public class MainWindowController {

    // Root
    @FXML
    private TabPane rootTabPane;

    // Add Tab UI Elements
    @FXML
    private TextField titleEntryField, emailEntryField, secondaryEmailEntryField, passwordEntryField,
            usernameEntryField, phoneNumberEntryField, groupEntryField, linkEntryField;
    @FXML
    private Button clearBtn, addBtn, generatePasswordBtn;
    @FXML
    ChoiceBox<String> emailChoiceBox, groupChoiceBox, secondaryEmailChoiceBox;

    // View Tab UI Elements
    @FXML
    TextField searchField;
    @FXML
    MenuButton filterMenuButton;
    @FXML
    ScrollPane scrollBox;
    @FXML
    VBox searchResultsDisplayVBox;

    // Caches
    private final EntryCache entryCache = EntryCache.getInstance();
    private final SearchResultCache searchResultCache = SearchResultCache.getInstance();

    @FXML
    public void initialize() {
        logger.info("Initializing MainWindowController");

        // Load and display search results in the view tab
        setupSearchResultsDisplay();

        // Set the email choice box options to unique emails in the database
        setUpEmailChoiceBoxOptions();

        // Set up various button callbacks
        addBtn.setOnAction(e -> addButtonCallback());
        clearBtn.setOnAction(e -> clearTextFields());
        generatePasswordBtn.setOnAction(e -> generatePasswordCallback());
        emailChoiceBox.setOnAction(e -> emailChoiceBoxCallback());

        setUpChooseFilterButton();
    }

    private void setUpChooseFilterButton() {
        // TODO: Implement filtering by different criteria
        filterMenuButton.setDisable(true);
    }

    private void setupSearchResultsDisplay() {
        logger.info("Setting up search results display");

        // Clear the search results display
        searchResultsDisplayVBox.getChildren().clear();

        // Get search results from cache
        List<Node> searchResults = searchResultCache.getSearchResults();

        // Add search results to display
        // Default is to display all entries in the database
        for (Node searchResult : searchResults) {
            searchResultsDisplayVBox.getChildren().add(searchResult);
        }

        // Add a listener to the search bar
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filterSearchResults(newValue);
            }
        });
    }

    public double getScrollBarPosition() {
        return scrollBox.getVvalue();
    }

    public void setScrollBarPosition(double position) {
        scrollBox.setVvalue(position);
    }

    private void filterSearchResults(String searchText) {
        // Clear the search results display
        searchResultsDisplayVBox.getChildren().clear();

        // Get search results from cache
        List<Node> searchResults = searchResultCache.getSearchResults();

        for (Node searchResult : searchResults) {
            Entry currentResultEntry = (Entry) searchResult.getUserData();
            if (currentResultEntry.titleContains(searchText)) {
                searchResultsDisplayVBox.getChildren().add(searchResult);
            }
        }
    }

    private void addButtonCallback() {
        // Get the values from the text fields
        String title = titleEntryField.getText();
        String email = emailEntryField.getText();
        String secondaryEmail = secondaryEmailEntryField.getText();
        String password = passwordEntryField.getText();
        String username = usernameEntryField.getText();
        String phoneNumber = phoneNumberEntryField.getText();
        String link = linkEntryField.getText();
        String group = groupEntryField.getText();


        // Check title is present
        if (title.isEmpty() ) {
            logger.warning("Title is required!");

            // Notifying popup
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Required Information");
            alert.setContentText("A title is required to add an entry.");
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

            Entry entry = new EntryBuilder(title)
                    .email(email)
                    .secondaryEmail(secondaryEmail)
                    .password(password)
                    .username(username)
                    .phoneNumber(phoneNumber)
                    .link(link)
                    .category(group)
                    .build();

            DatabaseAPI.getInstance().newEntry(entry);
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
        // TODO: Need to reset this when new emails are added
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
        secondaryEmailEntryField.clear();
        passwordEntryField.clear();
        usernameEntryField.clear();
        phoneNumberEntryField.clear();
        groupEntryField.clear();
        linkEntryField.clear();
    }

    // Logger
    private static final Logger logger = Logger.getLogger(MainWindowController.class.getName());

}
