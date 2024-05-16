package passwordmanager.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import passwordmanager.database.DatabaseConnection;
import passwordmanager.model.EntryCache;
import passwordmanager.model.SearchResultNodesCache;
import passwordmanager.livetesting.LiveTestSetup;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/passwordmanager/fxml/MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Password Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        runSetupTasks(args);
        launch();
    }

    public static void runSetupTasks(String[] args) {
        // Testing setup
        if (args.length == 0) {
            DatabaseConnection.setConnection(false);
        } else if ( args[0].equals("testing") ) {
            DatabaseConnection.setConnection(true);
            LiveTestSetup.setup();
        }

        // Cache setups (possible prevents this from going out of scope)
        EntryCache setupCache = EntryCache.getInstance();
        SearchResultNodesCache setupSearchResultNodesCache = SearchResultNodesCache.getInstance();
    }
}