package passwordmanager.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import passwordmanager.model.SearchResultNodesCache;

import java.util.ArrayList;

public class FindPageController {

    private SearchResultNodesCache searchResultNodesCache = SearchResultNodesCache.getInstance();

    private ArrayList<Node> searchResultsNodes = searchResultNodesCache.getSearchResultsNodes();

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
        displaySearchResults();
    }

    public void displaySearchResults() {
        int column = 0;
        int row = 0;

        for (Node searchResultNode : searchResultNodesCache.getSearchResultsNodes()) {
            searchResultDisplay.add(searchResultNode, column, row);

            column++;
            if (column == 2) {
                column = 0;
                row++;
            }
        }
    }
}
