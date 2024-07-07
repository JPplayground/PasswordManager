package passwordmanager.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import passwordmanager.backend.encryption.windows.WinKeyStorageUtil;
import passwordmanager.backend.local.livetesting.LiveTestSetup;
import passwordmanager.frontend.cache.EntryCache;
import passwordmanager.backend.local.database.DatabaseConnection;
import passwordmanager.frontend.cache.SearchResultFXMLCache;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        runSetupTasks(args);
        preloadCaches();
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
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

    public static void runSetupTasks(String[] args) {

        // TODO: Ensure only one instance of the program is running

        // Determine current OS
        PlatformExaminer platformExaminer = new PlatformExaminer();

        // TODO: Key storage
        // If windows, run windows key storage util
        // If Linux, run linux key storage util

        // Testing setup is done here determined by hard coded settings
        // See: DeveloperSettings.java
        DatabaseConnection.setConnection();

        // Sets testing data for UI components
        if (DeveloperSettings.getApplicationMode() == DeveloperSettings.ApplicationMode.TESTING) {
            LiveTestSetup.setup();
        }

        // Check if key exists
    }

    public static void preloadCaches() {

        // Cache setup
        EntryCache setupCache = EntryCache.getInstance();

        // Search result cache
        SearchResultFXMLCache searchResultFXMLCache = SearchResultFXMLCache.getInstance();
        searchResultFXMLCache.loadSearchResults();

    }

}
