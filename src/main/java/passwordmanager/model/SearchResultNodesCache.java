package passwordmanager.model;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code SearchResultsCache} class is a singleton that provides a cache for search result nodes in the password manager application.
 * This class retrieves all entries from the {@link EntryCache} and generates their corresponding FXML nodes for displaying search results.
 * It also provides methods to access the cached search result nodes.
 *
 * <p>This class ensures that the search result nodes are generated efficiently, and provides timing information for this operation.
 *
 * <p>Usage example:
 * <pre>
 * {@code
 * SearchResultsCache cache = SearchResultsCache.getInstance();
 * ArrayList<Node> searchResultsNodes = cache.getSearchResultsNodes();
 * }
 * </pre>
 *
 * @see EntryCache
 * @see Entry
 * @see SearchResultNodeFactory
 */
public class SearchResultNodesCache {

    private static SearchResultNodesCache instance = null;

    private final List<Node> searchResultsNodes = new ArrayList<>();

    /**
     * Private constructor to initialize the SearchResultsCache.
     * This constructor retrieves all entries from the {@link EntryCache} and generates their corresponding FXML nodes.
     * It also prints the time taken for this operation.
     */
    private SearchResultNodesCache() {
        ArrayList<Entry> entries = EntryCache.getInstance().getEntries();

        for (Entry entry : entries) {
            searchResultsNodes.add(SearchResultNodeFactory.generateSearchResult(entry));
        }
    }

    /**
     * Returns the singleton instance of the {@code SearchResultsCache} class.
     *
     * @return the singleton instance of the {@code SearchResultsCache}.
     */
    public static SearchResultNodesCache getInstance() {
        if (instance == null) {
            instance = new SearchResultNodesCache();
        }
        return instance;
    }

    /**
     * Returns the list of cached search result nodes.
     *
     * @return an {@code ArrayList} containing the cached search result nodes.
     */
    public List<Node> getNodes() {
        return searchResultsNodes;
    }

    /**
     * Returns the list of cached search result nodes that contain the specified search query.
     * @param searchQuery
     * @return an {@code ArrayList} containing the cached search result nodes that contain the specified search query.
     */
    public List<Node> getFilteredNodes(String searchQuery) {

        return searchResultsNodes.stream()
                .filter(node -> {
                    Entry entry = (Entry) node.getProperties().get("entry");
                    return entry.getTitle().toLowerCase().contains(searchQuery.toLowerCase());
                })
                .toList();
    }

}
