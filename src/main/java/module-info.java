module passwordmanager {
    requires javafx.controls;
    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;
    requires org.xerial.sqlitejdbc;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.sun.jna.platform;
    requires com.sun.jna;

    exports passwordmanager.database;
    exports passwordmanager.util;
    exports passwordmanager.app;
    exports passwordmanager.model;
    exports passwordmanager.controller;

    opens passwordmanager.controller to javafx.fxml;
    opens passwordmanager.app to javafx.fxml;
    exports passwordmanager.encryption;
    opens passwordmanager.encryption to javafx.fxml;

}
