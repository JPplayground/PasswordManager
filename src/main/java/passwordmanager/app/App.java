package passwordmanager.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

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

        int baseWidth = 800;
        int baseHeight = 600;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
        Parent root = loader.load();
        root.prefWidth(800);
        root.prefHeight(600);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/MainWindow.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

//        // TODO: Adjust window size based on display
//        DisplayExaminer examiner = new DisplayExaminer();
//
//        // Adjust the scaling of the window based on the display size
//        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//        double scalePercentage = Math.min(primaryScreenBounds.getWidth() / baseWidth, primaryScreenBounds.getHeight() / baseHeight);
//
//        // Scale the contents
//        root.setScaleX(scalePercentage);
//        root.setScaleY(scalePercentage);
//
//        // Adjust the stage size to fit the scaled contents
//        stage.setWidth(baseWidth * scalePercentage);
//        stage.setHeight(baseHeight * scalePercentage);
//
//        // Center the stage on the screen
//        stage.setX((primaryScreenBounds.getWidth() - stage.getWidth()) / 2);
//        stage.setY((primaryScreenBounds.getHeight() - stage.getHeight()) / 2);
//
//        stage.show();

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
        if (DevelopmentSettings.getApplicationMode() == DevelopmentSettings.ApplicationMode.TESTING) {
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
