package passwordmanager.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import passwordmanager.livetesting.LiveTestSetup;
import passwordmanager.model.EntryCache;
import passwordmanager.database.DatabaseConnection;
import passwordmanager.model.SearchResultCache;

import java.io.IOException;
import java.util.logging.Logger;

public class App extends Application {

    // Logger
    private static final Logger logger = Logger.getLogger(App.class.getName());

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
        Parent root = loader.load();

        root.prefWidth(800);
        root.prefHeight(600);

        Scene scene = new Scene(root);

        // set resizable to false
        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        runSetupTasks(args);
        runPreloadTasks();
        launch();
    }

    public static void runSetupTasks(String[] args) {
        logger.info("Running setup tasks");

        // Testing setup is done here determined by hard coded settings
        // See: DeveloperSettings.java
        DatabaseConnection.setConnection();

        // Sets testing data for UI components
        if (DeveloperSettings.getApplicationMode() == DeveloperSettings.ApplicationMode.TESTING) {
            LiveTestSetup.setup();
        }
    }

    public static void runPreloadTasks() {
        logger.info("Running preload tasks");

        // Cache setup
        EntryCache setupCache = EntryCache.getInstance();

        // Search result cache
        SearchResultCache searchResultCache = SearchResultCache.getInstance();
        searchResultCache.loadSearchResults();

    }

}
