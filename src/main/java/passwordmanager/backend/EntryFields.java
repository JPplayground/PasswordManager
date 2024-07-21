package passwordmanager.backend;

/**
 * Enum representing the columns in the entries table.
 */
public enum EntryFields {
    TITLE("title"),
    EMAIL("email"),
    SECONDARY_EMAIL("secondary_email"),
    PASSWORD("password"),
    USERNAME("username"),
    PHONE_NUMBER("phone_number"),
    LINK("link"),
    CATEGORY("category"),
    DATE_CREATED("date_created"),
    DATE_MODIFIED("date_modified");

    private final String columnName;

    EntryFields(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String toString() {
        return columnName;
    }
}