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

    exports passwordmanager.app;
    opens passwordmanager.frontend.controller to javafx.fxml;
}
