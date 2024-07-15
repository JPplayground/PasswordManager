module passwordmanager {
    requires javafx.fxml;
    requires javafx.controls;

    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;

    requires org.xerial.sqlitejdbc;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.sun.jna.platform;
    requires com.sun.jna;

    exports passwordmanager.backend.local.database;
    exports passwordmanager.frontend.util;
    exports passwordmanager.app;
    exports passwordmanager.model;
    exports passwordmanager.frontend.controller;
    exports passwordmanager.backend;

    opens passwordmanager.frontend.controller to javafx.fxml;
    opens passwordmanager.app to javafx.fxml;
    exports passwordmanager.backend.encryption;
    opens passwordmanager.backend.encryption to javafx.fxml;
    exports passwordmanager.backend.encryption.windows;
    opens passwordmanager.backend.encryption.windows to javafx.fxml;
    exports passwordmanager.backend.encryption.linux;
    opens passwordmanager.backend.encryption.linux to javafx.fxml;
    exports passwordmanager.frontend.cache;

}
