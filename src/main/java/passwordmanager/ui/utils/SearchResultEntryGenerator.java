package passwordmanager.ui.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import passwordmanager.database.Entry;
import passwordmanager.ui.UIConstants;
import passwordmanager.ui.controllers.SearchResultController;

import java.io.IOException;

/**
 * This class is used to generate search result entries.
 */
public class SearchResultEntryGenerator {

    /**
     * Generates a search result entry.
     * @param entry The entry to generate the search result for.
     * @return The generated search result as a Node.
     */
    public static Node generateSearchResult(Entry entry) {

        FXMLLoader loader = new FXMLLoader(SearchResultEntryGenerator.class.getResource(UIConstants.SEARCH_RESULT_ENTRY_TEMPLATE));
        loader.setControllerFactory(c -> new SearchResultController(entry));

        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}


