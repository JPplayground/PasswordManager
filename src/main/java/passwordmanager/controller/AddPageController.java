package passwordmanager.controller;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import passwordmanager.database.DatabaseAPI;
import passwordmanager.model.Entry;
import passwordmanager.model.EntryCache;
import passwordmanager.util.PasswordGenerator;

public class AddPageController {

    private MainWindowController mainWindowController;

    @FXML
    GridPane rootGridPane;
    @FXML
    Label addCommonEmailsLabel, addNewEntryLabel, titleEntryLabel, emailEntryLabel, passwordEntryLabel, usernameEntryLabel, linkEntryLabel, groupEntryLabel;
    @FXML
    TextField addCommonEmailEntryField, titleEntryField, emailEntryField, passwordEntryField, usernameEntryField, linkEntryField, groupEntryField;
    @FXML
    Button addCommonEmailButton, generatePasswordBtn, addEntryButton, clearFieldsButton;
    @FXML
    ChoiceBox emailChoicebox, groupChoicebox;
    @FXML
    Label emailMessageBox, entryMessageBox;

    DatabaseAPI databaseAPI = DatabaseAPI.getInstance();

    @FXML
    public void initialize() {
        setCallbacks();
        setDropdowns();

        // Bind width of choice boxes to width column 2 - a percentage of the width of the root grid pane
        emailChoicebox.prefWidthProperty().bind(rootGridPane.widthProperty().multiply(0.175));
        groupChoicebox.prefWidthProperty().bind(rootGridPane.widthProperty().multiply(0.175));


    }

    public void setCallbacks() {

        addCommonEmailButton.setOnAction(event -> addCommonEmail());
        generatePasswordBtn.setOnAction(event -> generatePassword());
        addEntryButton.setOnAction(event -> addEntry());
        clearFieldsButton.setOnAction(event -> clearFields());

        // When a group is selected, set the group field to the selected group
        groupChoicebox.setOnAction(event -> {
            Object selectedItem = groupChoicebox.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                groupEntryField.setText(selectedItem.toString());
                groupChoicebox.getSelectionModel().clearSelection();
            }
        });

        // When an email is selected, set the email field to the selected email
        emailChoicebox.setOnAction(event -> {
            Object selectedItem = emailChoicebox.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                emailEntryField.setText(selectedItem.toString());
                emailChoicebox.getSelectionModel().clearSelection();
            }
        });

    }

    public void setDropdowns() {

        // Clear the dropdowns before adding new items
        emailChoicebox.getItems().clear();
        groupChoicebox.getItems().clear();

        emailChoicebox.getItems().addAll(databaseAPI.getListOfCommonEmails());
        groupChoicebox.getItems().addAll(databaseAPI.getListOfGroups());
    }


    public void addCommonEmail() {
        System.out.println("Adding common email");

        String commonEmail = addCommonEmailEntryField.getText();

        if (commonEmail.isEmpty()) {
            System.out.println("Common email field is empty. Common email not added.");
            return;
        }

        databaseAPI.addCommonEmail(commonEmail);
        addCommonEmailEntryField.clear();

        System.out.println("Added common email: " + commonEmail);

        createNotificationMessage("Added: " + commonEmail, emailMessageBox);

        // Reset the dropdowns to include the new email
        setDropdowns();

        // Ensure entry cache is updated
        EntryCache.getInstance().loadAllEntries();

    }

    public void generatePassword() {
        String randomPassword = PasswordGenerator.getPassword();
        passwordEntryField.setText(randomPassword);
        System.out.println("Generated password: " + randomPassword);
    }

    public void addEntry() {

        if (titleEntryField.getText().isEmpty() || emailEntryField.getText().isEmpty() || passwordEntryField.getText().isEmpty()) {
            System.out.println("Required fields left empty. Entry not added.");
            return;
        }

        Entry newEntry = new Entry(
                titleEntryField.getText(),
                emailEntryField.getText(),
                passwordEntryField.getText(),
                usernameEntryField.getText(),
                linkEntryField.getText(),
                groupEntryField.getText()
        );

        databaseAPI.newEntry(newEntry);
        clearFields();

        createNotificationMessage("Entry added!", entryMessageBox);

        // Reset the dropdowns to include the new entry group in case one was added
        setDropdowns();
    }


    private void clearFields() {
        titleEntryField.clear();
        emailEntryField.clear();
        passwordEntryField.clear();
        usernameEntryField.clear();
        linkEntryField.clear();
        groupEntryField.clear();
    }

    public void createNotificationMessage(String message, Label messageBox) {
        messageBox.setText(message);

        // Create a PauseTransition that lasts for 1 second
        PauseTransition pt = new PauseTransition(Duration.seconds(1));

        // Set an onFinished event handler for the PauseTransition
        pt.setOnFinished(event -> {
            // Create a FadeTransition for the messageBox
            FadeTransition ft = new FadeTransition(Duration.millis(2000), messageBox);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);

            // Set an onFinished event handler for the FadeTransition
            ft.setOnFinished(e -> messageBox.setText(""));

            // Start the fade transition
            ft.play();
        });

        // Start the pause transition
        pt.play();
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }
}
