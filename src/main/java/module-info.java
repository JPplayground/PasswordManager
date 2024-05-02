module com.core.passwordmanager3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires com.h2database;
    requires annotations;


    opens com.core.passwordmanager to javafx.fxml;
    exports com.core.passwordmanager;
    exports com.core.passwordmanager.data;
    opens com.core.passwordmanager.data to javafx.fxml;
}