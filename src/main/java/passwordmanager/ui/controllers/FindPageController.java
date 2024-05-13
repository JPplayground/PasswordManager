package passwordmanager.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import passwordmanager.database.DatabaseAPI;
import passwordmanager.database.Entry;
import passwordmanager.ui.utils.SearchResultEntryGenerator;

import java.util.ArrayList;

public class FindPageController {

    private final DatabaseAPI databaseAPI = DatabaseAPI.getInstance();

    private MainWindowController mainWindowController;

    @FXML
    GridPane searchFunctionsGridPane;
    @FXML
    TextField searchEntryBox;
    @FXML
    Label searchLabel;
    @FXML
    ScrollPane searchResultsScrollPane;
    @FXML
    VBox searchResultsVBox;


    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void initialize() {
        ArrayList<Entry> entries = databaseAPI.getAllEntries();

        for (Entry entry : entries) {
            Node entryNode = SearchResultEntryGenerator.generateSearchResult(entry);

            // Add the entry to the VBox
            searchResultsVBox.getChildren().add(entryNode);
        }

    }
}
