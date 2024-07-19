package passwordmanager.frontend.cache;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import passwordmanager.frontend.controller.SearchResultController;
import passwordmanager.model.Entry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * The {@code SearchResultFXMLCache} class is a singleton that provides a cache for search results in the password manager application.
 * This class loads and caches search results as JavaFX nodes upon initialization and provides methods to access and refresh the cached results.
 *
 * <p>This class ensures that the search results are loaded efficiently and provides logging information for these operations.
 *
 * <p>Usage example:
 * <pre>
 * {@code
 * SearchResultFXMLCache cache = SearchResultFXMLCache.getInstance();
 * List<Node> searchResults = cache.getSearchResults();
 * }
 * </pre>
 *
 * @see EntryCache
 * @see Entry
 * @see SearchResultController
 */
public class SearchResultFXMLCache {

    // Logger for logging purposes
    private static final Logger logger = Logger.getLogger(SearchResultFXMLCache.class.getName());

    // Singleton instance
    private static SearchResultFXMLCache instance = null;

    // Cached list of search results
    private List<Node> searchResults = new ArrayList<>();

    /**
     * Returns the singleton instance of the {@code SearchResultFXMLCache} class.
     *
     * @return the singleton instance of the {@code SearchResultFXMLCache}.
     */
    public static SearchResultFXMLCache getInstance() {
        if (instance == null) {
            logger.info("Creating new SearchResultCache instance");
            instance = new SearchResultFXMLCache();
        }
        return instance;
    }

    /**
     * Loads the search result cache. This method should be called on app startup or when the cache needs to be refreshed.
     */
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
                logger.severe("Failed to load SearchResult.fxml: " + e.getMessage());
            }
        }
    }

    /**
     * Refreshes the search results cache.
     */
    public void refreshSearchResults() {
        logger.info("Refreshing search result cache");
        searchResults.clear();
        loadSearchResults();
    }

    /**
     * Returns the list of cached search results.
     *
     * @return a {@code List} containing the cached search results.
     */
    public List<Node> getSearchResults() {
        return searchResults;
    }
}
