package passwordmanager.database;

import javafx.scene.Node;
import passwordmanager.ui.utils.SearchResultEntryGenerator;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class SearchResultsCache {

    private static SearchResultsCache instance = null;

    private ArrayList<Node> searchResultsNodes = new ArrayList<>();

    private SearchResultsCache() {
        ArrayList<Entry> entries = EntryCache.getInstance().getEntries();


        // Timing this
        Instant start = Instant.now();
        for (Entry entry : entries) {
            searchResultsNodes.add(SearchResultEntryGenerator.generateSearchResult(entry));
        }
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Building entry FXMLs operation took " + timeElapsed.toMillis() + " milliseconds");



    }

    public static SearchResultsCache getInstance() {
        if (instance == null) {
            instance = new SearchResultsCache();
        }
        return instance;
    }

    public ArrayList<Node> getSearchResultsNodes() {
        return searchResultsNodes;
    }

}
