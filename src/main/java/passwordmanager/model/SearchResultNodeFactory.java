package passwordmanager.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import passwordmanager.ui.UIPaths;
import passwordmanager.controller.SearchResultController;

import java.io.IOException;

/**
 * The {@code SearchResultEntryGenerator} class is used to generate search result entries for the password manager application.
 * This class provides a method to create a search result entry as a {@link Node} based on a given {@link Entry}.
 *
 * <p>The generated search result entry is based on an FXML template specified in the {@link UIPaths} class.
 * The {@link SearchResultController} is used as the controller for the FXML template, and it is initialized with the provided entry.
 *
 * <p>Usage example:
 * <pre>
 * {@code
 * Entry entry = ...;
 * Node searchResultNode = SearchResultEntryGenerator.generateSearchResult(entry);
 * }
 * </pre>
 *
 * @see Entry
 * @see UIPaths
 * @see SearchResultController
 */
public class SearchResultNodeFactory {

    /**
     * Generates a search result entry for the given {@link Entry}.
     *
     * @param entry The entry to generate the search result for.
     * @return The generated search result as a {@link Node}.
     */
    public static Node generateSearchResult(Entry entry) {
        FXMLLoader loader = new FXMLLoader(SearchResultNodeFactory.class.getResource(UIPaths.SEARCH_RESULT_ENTRY_TEMPLATE));

        loader.setControllerFactory(c -> new SearchResultController(entry));

        try {
            Node node = loader.load();

            // Set entry information
            node.getProperties().put("entry", entry);

            return node;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
