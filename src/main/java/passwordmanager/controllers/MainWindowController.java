/*
Color Palette:
49243E
704264
BB8493
DBAFA0
 */

package passwordmanager.controllers;

import passwordmanager.database.DatabaseAPI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;


public class MainWindowController {

    DatabaseAPI databaseAPI;

    // Sidebar buttons
    @FXML
    private Button homeBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button findBtn;
    @FXML
    private Button manageBtn;
    @FXML
    private Button aboutBtn;

    @FXML
    private Pane pageDisplay;

    private String homePagePath = "/com/jp/passwordmanager/HomePage.fxml";
    private String addPagePath = "/com/jp/passwordmanager/AddPage.fxml";
    private String findPagePath = "/com/jp/passwordmanager/FindPage.fxml";
    private String managePagePath = "/com/jp/passwordmanager/ManagePage.fxml";
    private String aboutPagePath = "/com/jp/passwordmanager/AboutPage.fxml";

    @FXML
    public void initialize() {

        // Set page loading callbacks
        homeBtn.setOnMouseClicked(event -> loadHomePage());
        addBtn.setOnMouseClicked(event -> loadAddPage());
        findBtn.setOnMouseClicked(event -> loadFindPage());
        manageBtn.setOnMouseClicked(event -> loadManagePage());
        aboutBtn.setOnMouseClicked(event -> loadAboutPage());

        // Load home page on startup
        loadHomePage();
    }

    @FXML
    void loadHomePage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(homePagePath));
            Pane newLoadedPane = fxmlLoader.load();
            pageDisplay.getChildren().clear();
            pageDisplay.getChildren().add(newLoadedPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadAddPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(addPagePath));
            Pane newLoadedPane = fxmlLoader.load();
            pageDisplay.getChildren().clear();
            pageDisplay.getChildren().add(newLoadedPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadFindPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(findPagePath));
            Pane newLoadedPane = fxmlLoader.load();
            pageDisplay.getChildren().clear();
            pageDisplay.getChildren().add(newLoadedPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadManagePage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(managePagePath));
            Pane newLoadedPane = fxmlLoader.load();
            pageDisplay.getChildren().clear();
            pageDisplay.getChildren().add(newLoadedPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadAboutPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(aboutPagePath));
            Pane newLoadedPane = fxmlLoader.load();
            pageDisplay.getChildren().clear();
            pageDisplay.getChildren().add(newLoadedPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
