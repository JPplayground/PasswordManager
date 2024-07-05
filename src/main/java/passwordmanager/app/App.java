package passwordmanager.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import passwordmanager.backend.local.encryption.windows.WinKeyStorageUtil;
import passwordmanager.backend.local.livetesting.LiveTestSetup;
import passwordmanager.frontend.cache.EntryCache;
import passwordmanager.backend.local.database.DatabaseConnection;
import passwordmanager.frontend.cache.SearchResultCache;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // If windows, run windows key storage util

        // If Linux, run linux key storage util

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
        Parent root = loader.load();

        root.prefWidth(800);
        root.prefHeight(600);

        Scene scene = new Scene(root);

        // set resizable to false
        // stage.setResizable(false);

        scene.getStylesheets().add(getClass().getResource("/css/MainWindow.css").toExternalForm());

        stage.setScene(scene);
        stage.show();

        WinKeyStorageUtil.isKeyPresent();

    }

    public static void main(String[] args) {
        runSetupTasks(args);
        runPreloadTasks();
        launch();
    }

    public static void runSetupTasks(String[] args) {

        // Testing setup is done here determined by hard coded settings
        // See: DeveloperSettings.java
        DatabaseConnection.setConnection();

        // Sets testing data for UI components
        if (DeveloperSettings.getApplicationMode() == DeveloperSettings.ApplicationMode.TESTING) {
            LiveTestSetup.setup();
        }

        // Check if key exists
    }

    public static void runPreloadTasks() {

        // Cache setup
        EntryCache setupCache = EntryCache.getInstance();

        // Search result cache
        SearchResultCache searchResultCache = SearchResultCache.getInstance();
        searchResultCache.loadSearchResults();

    }

}
