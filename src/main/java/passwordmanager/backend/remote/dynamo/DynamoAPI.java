package passwordmanager.backend.remote.dynamo;

import passwordmanager.backend.DatabaseAPI;
import passwordmanager.backend.EntryFields;
import passwordmanager.model.Entry;

import java.util.List;
import java.util.Set;

public class DynamoAPI implements DatabaseAPI {

    @Override
    public void newEntry(Entry entry) {

    }

    @Override
    public void modifyEntry(String title, EntryFields field, String newValue) {

    }

    @Override
    public void removeEntry(String title) {

    }

    @Override
    public void removeEntry(Entry entry) {

    }

    @Override
    public Entry getEntry(String titleKey) {
        return null;
    }

    @Override
    public List<Entry> getAllEntries() {
        return List.of();
    }

    @Override
    public List<String> getEntryTitles() {
        return List.of();
    }

    @Override
    public Set<String> getGroups() {
        return Set.of();
    }
}
