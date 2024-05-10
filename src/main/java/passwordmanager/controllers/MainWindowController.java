/*
Color Palette:
49243E
704264
BB8493
DBAFA0
 */

package passwordmanager.controllers;

import javafx.scene.layout.BorderPane;
import passwordmanager.database.DatabaseAPI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;


import java.io.IOException;


public class MainWindowController {

    DatabaseAPI databaseAPI;

    // Sidebar buttons
    @FXML
    private Button homeBtn, addBtn, findBtn, manageBtn, aboutBtn;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    public void initialize() {
        // Callbacks
        homeBtn.setOnAction(event -> loadHomePage());
        addBtn.setOnAction(event -> loadAddPage());
        findBtn.setOnAction(event -> loadFindPage());
        manageBtn.setOnAction(event -> loadManagePage());
        aboutBtn.setOnAction(event -> loadAboutPage());
    }

    public void loadHomePage() {
        System.out.println("Loading home page");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/passwordmanager/fxml/HomePage.fxml"));
        try {
            mainBorderPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAddPage() {
        System.out.println("Loading add page");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/passwordmanager/fxml/AddPage.fxml"));
        try {
            mainBorderPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFindPage() {
        System.out.println("Loading find page");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/passwordmanager/fxml/FindPage.fxml"));
        try {
            mainBorderPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadManagePage() {
        System.out.println("Loading manage page");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/passwordmanager/fxml/ManagePage.fxml"));
        try {
            mainBorderPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAboutPage() {
        System.out.println("Loading about page");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/passwordmanager/fxml/AboutPage.fxml"));
        try {
            mainBorderPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
