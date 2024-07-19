package passwordmanager.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import passwordmanager.backend.local.livetesting.LiveTestSetup;
import passwordmanager.frontend.cache.EntryCache;
import passwordmanager.backend.local.database.DatabaseConnection;
import passwordmanager.frontend.cache.SearchResultFXMLCache;

import java.io.IOException;

/**
 * Main application class for the password manager.
 */
public class App extends Application {

    /**
     * Main method to run setup tasks, preload caches, and launch the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        runSetupTasks(args);
        preloadCaches();
        launch();
    }

    /**
     * Starts the JavaFX application by setting up the primary stage.
     *
     * @param stage the primary stage for this application
     * @throws IOException if loading the FXML file fails
     */
    @Override
    public void start(Stage stage) throws IOException {
        int baseWidth = 800;
        int baseHeight = 600;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
        Parent root = loader.load();
        root.prefWidth(baseWidth);
        root.prefHeight(baseHeight);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/MainWindow.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

        // TODO: Adjust window size based on display
    }

    /**
     * Runs the necessary setup tasks before the application starts.
     *
     * @param args the command line arguments
     */
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
        if (DevelopmentSettings.getApplicationMode() == DevelopmentSettings.ApplicationMode.TESTING) {
            LiveTestSetup.setup();
        }

        // Check if key exists
    }

    /**
     * Preloads the necessary caches for the application.
     */
    public static void preloadCaches() {
        // Cache setup
        EntryCache setupCache = EntryCache.getInstance();

        // Search result cache
        SearchResultFXMLCache searchResultFXMLCache = SearchResultFXMLCache.getInstance();
        searchResultFXMLCache.loadSearchResults();
    }
}
