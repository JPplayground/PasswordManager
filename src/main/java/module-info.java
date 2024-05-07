module com.jp.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires com.h2database;
    requires annotations;


    opens com.jp.passwordmanager to javafx.fxml;
    exports com.jp.passwordmanager;
    exports com.jp.passwordmanager.database;
    opens com.jp.passwordmanager.database to javafx.fxml;
    exports com.jp.passwordmanager.controllers;
    opens com.jp.passwordmanager.controllers to javafx.fxml;
}