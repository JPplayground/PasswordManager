package passwordmanager.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import passwordmanager.database.DatabaseAPI;
import passwordmanager.database.Entry;
import passwordmanager.database.EntryCache;
import passwordmanager.database.SearchResultsCache;
import passwordmanager.ui.utils.SearchResultEntryGenerator;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class FindPageController {

    private SearchResultsCache searchResultsCache = SearchResultsCache.getInstance();

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
    GridPane searchResultDisplay;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    @FXML
    public void initialize() {

        // Timing this
        Instant start = Instant.now();

        int column = 0;
        int row = 0;

        for (Node searchResultNode : searchResultsCache.getSearchResultsNodes()) {
            searchResultDisplay.add(searchResultNode, column, row);

            column++;
            if (column == 2) {
                column = 0;
                row++;
            }

        }

        // Debugging
        System.out.println(searchResultDisplay.getRowCount());

        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Building entry FXMLs operation took " + timeElapsed.toMillis() + " milliseconds");


    }
}
