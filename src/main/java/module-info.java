module com.jp.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires com.h2database;
    requires annotations;
    requires java.datatransfer;
    requires java.desktop;


    opens passwordmanager to javafx.fxml;
    exports passwordmanager;
    exports passwordmanager.database;
    opens passwordmanager.database to javafx.fxml;
    exports passwordmanager.ui.controllers;
    opens passwordmanager.ui.controllers to javafx.fxml;
    exports passwordmanager.ui;
    opens passwordmanager.ui to javafx.fxml;
    exports passwordmanager.ui.utils;
    opens passwordmanager.ui.utils to javafx.fxml;
}