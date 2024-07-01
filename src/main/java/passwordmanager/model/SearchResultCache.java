package passwordmanager.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import passwordmanager.controller.SearchResultController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SearchResultCache {

    // Logger
    private static final Logger logger = Logger.getLogger(SearchResultCache.class.getName());

    // Singleton instance
    private static SearchResultCache instance = null;

    // Cached list of search results
    private List<Node> searchResults = new ArrayList<>();

    // Get singleton instance of the SearchResultCache
    public static SearchResultCache getInstance() {
        if (instance == null) {
            logger.info("Creating new SearchResultCache instance");
            instance = new SearchResultCache();
        }
        return instance;
    }

    // Load the search result cache (call this on app startup or when the cache needs to be refreshed)
    public void loadSearchResults() {
        logger.info("Initializing search result cache");

        // Get all entries from the database and build cache
        List<Entry> entries = EntryCache.getInstance().getEntries();
        for (Entry entry : entries) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SearchResult.fxml"));
                Node searchResult = loader.load();
                SearchResultController controller = loader.getController();
                controller.setEntry(entry);

                // Embed entry data in node for later access (filtering in MainWindowController)
                searchResult.setUserData(entry);

                searchResults.add(searchResult);
            } catch (IOException e) {
                logger.severe("Failed to load SearchResult.fxml");
            }
        }
    }

    // Refresh the search results cache
    public void refreshSearchResults() {
        logger.info("Refreshing search result cache");
        searchResults.clear();
        loadSearchResults();
    }

    public List<Node> getSearchResults() {
        return searchResults;
    }





}
