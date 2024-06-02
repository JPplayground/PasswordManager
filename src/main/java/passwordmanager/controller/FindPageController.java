package passwordmanager.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import passwordmanager.model.SearchResultNodesCache;

import java.util.ArrayList;
import java.util.List;

public class FindPageController {

    private SearchResultNodesCache searchResultNodesCache = SearchResultNodesCache.getInstance();

    private MainWindowController mainWindowController;

    private List<Node> currentDisplayNodes;

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

        currentDisplayNodes = searchResultNodesCache.getNodes();
        displaySearchResults();

        // Bind grid pane height to scroll pane height
        searchResultDisplay.minHeightProperty().bind(searchResultsScrollPane.heightProperty());

        searchEntryBox.textProperty().addListener((observable, oldValue, newValue) -> textChanged(newValue));
    }

    public void displaySearchResults() {

        System.out.println("Display search results called.");

        searchResultDisplay.getChildren().clear();

        int column = 0;
        int row = 0;

        for (Node searchResultNode : currentDisplayNodes) {
            searchResultDisplay.add(searchResultNode, column, row);

            column++;
            if (column == 2) {
                column = 0;
                row++;
            }
        }
    }

    public void textChanged(String filterString) {
        System.out.println("Text changed: " + filterString);

        if (filterString.isEmpty()) {

            currentDisplayNodes = searchResultNodesCache.getNodes();

        } else {

            currentDisplayNodes = searchResultNodesCache.getFilteredNodes(filterString);
        }

        displaySearchResults();
    }
}
