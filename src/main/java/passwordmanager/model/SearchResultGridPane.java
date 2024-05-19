package passwordmanager.model;

import javafx.scene.layout.GridPane;

public class SearchResultGridPane extends GridPane {

    private Entry entry;

    public SearchResultGridPane() {
        super();
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

}
