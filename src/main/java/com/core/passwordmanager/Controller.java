/*
Color Palette:
49243E
704264
BB8493
DBAFA0
 */

package com.core.passwordmanager;

import com.core.passwordmanager.data.DatabaseAPI;
import com.core.passwordmanager.data.PasswordGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Objects;

public class Controller {

    // UI COMPONENTS

    @FXML
    private VBox searchResults;
    @FXML
    private Label personalInfo, emailLabel, passwordLabel, usernameLabel, linkLabel, titleLabel, groupNameLabel;
    @FXML
    private ImageView logo;
    @FXML
    private TextField commonEmailEntry, titleEntry, emailEntry, passwordEntry, usernameEntry, linkEntry, groupNameEntry;
    @FXML
    private Button clearSearchBar, addGroupButton, clearGroupButton, generatePasswordButton, addButton;
    @FXML
    private ScrollPane listView;
    @FXML
    private ComboBox<?> groupSelect;
    @FXML
    private TabPane mainView;
    @FXML
    private Tab homeView;
    @FXML
    private ChoiceBox<?> commonEmailsBox, commonGroupNameSelect;

    DatabaseAPI databaseAPI;

    @FXML
    public void initialize() {
        databaseAPI = DatabaseAPI.getInstance(false);
        populateListView();
    }

    private void populateListView() {

    }

    public void addCommonEmail() {
        String commonEmail = commonEmailEntry.getText();
        if (commonEmail.isEmpty()) {
            System.out.println("No value was entered!");
            return;
        }
    }

    public void clearEntryFields() {
        TextField[] entryFields = {titleEntry, emailEntry, passwordEntry, usernameEntry, linkEntry, groupNameEntry};
        for (TextField entry : entryFields) {
            entry.clear();
        }
    }

    public void addNewEntry() {
        String titleText, emailText, passwordText, usernameText, linkText, groupNameText;

        titleText = titleEntry.getText();
        emailText = emailEntry.getText();
        passwordText = passwordEntry.getText();
        usernameText = usernameEntry.getText();
        linkText = linkEntry.getText();
        groupNameText = groupNameEntry.getText();

        // Disallowing null values on this. If any entry box is null rather than being an empty string we exit.
        // Have no good reason to think this would happen but going to check anyways.
        String[] texts = {titleText, emailText, passwordText, usernameText, linkText, groupNameText};
        if (Arrays.stream(texts).anyMatch(Objects::isNull)) {
            System.out.println("Null value found in text fields. Cannot proceed any further!");
            return;
        }

        // Ensuring required fields are not empty
        String[] requiredFields = {titleText, emailText, passwordText};
        for (String field : requiredFields) {
            if (field.isEmpty()) {
                String message = """
                        A required field has been left empty!
                        No entries have been added.
                        """;
                System.out.println(message);
                return;
            }
        }
    }

    public void generatePassword() {
        String password = PasswordGenerator.getPassword();
        passwordEntry.setText(password);
    }





}
