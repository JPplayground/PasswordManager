package com.jp.passwordmanager.data;

public class DatabaseConfig {
    public static final String DRIVER_URL = "org.h2.Driver";
    public static final String APP_CONNECTION_URL = "jdbc:h2:~/test";
    public static final String TEST_CONNECTION_URL_WITH_PROXY = "jdbc:p6spy:h2:mem:test";
    public static final String ENTRIES_TABLE_NAME = "ENTRIES";
    public static final String COMMON_EMAILS_TABLE_NAME = "COMMON_EMAILS";
    public static final String COMMON_EMAILS_COLUMN_NAME = "email";

    public enum EntryColumns {
        TITLE("title"),
        EMAIL("email"),
        PASSWORD("password"),
        USERNAME("username"),
        LINK("link"),
        CATEGORY("category"),
        DATE_CREATED("date_created"),
        DATE_MODIFIED("date_modified");

        private final String columnName;

        EntryColumns(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }
}

