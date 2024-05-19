module com.jp.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;
    requires org.xerial.sqlitejdbc;
    requires org.slf4j;
    requires org.slf4j.simple;

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