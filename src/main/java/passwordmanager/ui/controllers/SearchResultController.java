package passwordmanager.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.text.Text;
import passwordmanager.database.Entry;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.awt.*;
import java.net.URI;


public class SearchResultController {

    @FXML
    Label titlePlaceholder, dateCreatedPlaceholder, dateModifiedPlaceholder, emailsLabel, passwordLabel, usernameLabel;
    @FXML
    Text emailPlaceholder, passwordPlaceholder, usernamePlaceholder;
    @FXML
    Button copyEmailButton, copyPasswordButton, copyUsernameButton, linkButton;

    String title, username, password, link;
    String dateCreated, dateModified;

    /**
     * Constructor for the search result controller.
     * @param entry The entry to display.
     */
    public SearchResultController(Entry entry) {

        this.title = entry.getTitle();
        this.username = entry.getUsername();
        this.password = entry.getPassword();
        this.link = entry.getLink();

        if (entry.getDateCreated() != null && entry.getDateModified() != null) {
            this.dateCreated = entry.getDateCreated().toString();
            this.dateModified = entry.getDateModified().toString();
        } else {
            this.dateCreated = "N/A";
            this.dateModified = "N/A";
        }
    }

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
        setOpenLinkCallback();
        setCopyCallbacks();
        setLabels();
    }

    /**
     * Sets the text for the labels and placeholders.
     */
    private void setCopyCallbacks() {
        copyEmailButton.setOnAction(e -> copyToClipboard(emailPlaceholder.getText()));
        copyPasswordButton.setOnAction(e -> copyToClipboard(passwordPlaceholder.getText()));
        copyUsernameButton.setOnAction(e -> copyToClipboard(usernamePlaceholder.getText()));
    }

    /**
     * Copies the given text to the clipboard.
     * @param text The text to copy.
     */
    private void copyToClipboard(String text) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    /**
     * Sets the callback for the open link button.
     */
    private void setOpenLinkCallback() {
        linkButton.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI(link));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void setLabels() {
        titlePlaceholder.setText(title);
        emailPlaceholder.setText(username);
        passwordPlaceholder.setText(password);
        usernamePlaceholder.setText(username);
        dateCreatedPlaceholder.setText(dateCreated);
        dateModifiedPlaceholder.setText(dateModified);
    }

}
