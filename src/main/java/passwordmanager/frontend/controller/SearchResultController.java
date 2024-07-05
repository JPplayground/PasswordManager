package passwordmanager.frontend.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import passwordmanager.backend.local.database.LocalAPI;
import passwordmanager.model.Entry;
import passwordmanager.frontend.cache.EntryCache;
import passwordmanager.frontend.cache.SearchResultCache;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

import java.util.Optional;

public class SearchResultController {

    Entry entry;

    @FXML
    VBox root;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField emailTextField, passwordTextField, usernameTextField;

    @FXML
    private Button copyEmailBtn, copyPasswordBtn, copyUsernameBtn, editBtn, deleteBtn;

    @FXML
    public void initialize() {

        setCopyCallbacks();
        setDeleteCallback();
        setEditCallback();

        // Setting the textfields to be non focus-traversable
        emailTextField.setFocusTraversable(false);
        passwordTextField.setFocusTraversable(false);
        usernameTextField.setFocusTraversable(false);

        // TODO: Focus appears on title entry field initially, would like to remove that if possible
    }

    public void setEntry(Entry entry) {
        this.entry = entry;

        emailTextField.setText(this.entry.getEmail());
        passwordTextField.setText(this.entry.getPassword());

        // Gray out the username field if it is empty
        if (this.entry.getUsername().isEmpty()) {
            usernameTextField.setDisable(true);
        } else {
            usernameTextField.setText(this.entry.getUsername());
        }

        titleLabel.setText(this.entry.getTitle());
    }

    private void setCopyCallbacks() {
        copyEmailBtn.setOnAction((ActionEvent event) -> {
            StringSelection stringSelection = new StringSelection(emailTextField.getText());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        });

        copyPasswordBtn.setOnAction((ActionEvent event) -> {
            StringSelection stringSelection = new StringSelection(passwordTextField.getText());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        });

        copyUsernameBtn.setOnAction((ActionEvent event) -> {
            StringSelection stringSelection = new StringSelection(usernameTextField.getText());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        });
    }

    private void setEditCallback() {
        // TODO: Implement edit feature
        // Things that need to happen here:
        // 1. Open the EditEntryDialog
        // 2. ...

        // For now going to disable the edit button
        editBtn.setDisable(true);
    }

    private void setDeleteCallback() {
        // TODO: Re-implement more efficient way to keep caches accurate
        // TODO: Figure out why title on succeeding entry gets highlighted when deleting an entry

        deleteBtn.setOnAction((ActionEvent event) -> {

            // Confirm deletion popup
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Are you sure you want to delete this entry?");
            alert.setContentText("This action cannot be undone.");

            // Set the owner of the alert to the main window
            Window window = deleteBtn.getScene().getWindow();
            alert.initOwner(window);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                // Delete the entry from the database and update caches
                LocalAPI.getInstance().removeEntry(this.entry.getTitle());
                EntryCache.getInstance().updateEntries();
                SearchResultCache.getInstance().refreshSearchResults();

                // Remove the entry from the VBox
                VBox parent = (VBox) root.getParent();
                parent.getChildren().remove(root);

                // Cleanup resources
                cleanup();
            } else {

            }
        });
    }

    private void cleanup() {
        // Remove event handlers
        copyEmailBtn.setOnAction(null);
        copyPasswordBtn.setOnAction(null);
        copyUsernameBtn.setOnAction(null);
        editBtn.setOnAction(null);
        deleteBtn.setOnAction(null);

        // Clear references to help with garbage collection
        entry = null;
        emailTextField.setText("");
        passwordTextField.setText("");
        usernameTextField.setText("");
    }
}
