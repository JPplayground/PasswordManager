module com.jp.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires com.h2database;
    requires annotations;
    requires java.datatransfer;
    requires java.desktop;

    exports passwordmanager.database;
    opens passwordmanager.database to javafx.fxml;
    exports passwordmanager.controller;
    opens passwordmanager.controller to javafx.fxml;
    exports passwordmanager.ui;
    opens passwordmanager.ui to javafx.fxml;
    exports passwordmanager.util;
    opens passwordmanager.util to javafx.fxml;
    exports passwordmanager.app;
    opens passwordmanager.app to javafx.fxml;
    exports passwordmanager.model;
    opens passwordmanager.model to javafx.fxml;
}