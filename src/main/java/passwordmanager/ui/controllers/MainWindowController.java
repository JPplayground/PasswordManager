/*
Color Palette:
49243E
704264
BB8493
DBAFA0
 */

package passwordmanager.ui.controllers;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import passwordmanager.ui.UIConstants;


import java.io.IOException;

public class MainWindowController {

    String currentLoadedPage = UIConstants.HOME_PAGE_PATH;

    // Sidebar buttons
    @FXML
    private Button homeBtn, addBtn, findBtn, manageBtn, aboutBtn;

    @FXML
    private BorderPane mainBorderPane;

    private boolean firstLoad = true;

    @FXML
    public void initialize() {
        setButtonWidths();
        setButtonCallbacks();
        loadHomePage();
        setResizeCallback();
    }

    /**
     * This method is used to set the width of the buttons in the sidebar to match the width of the left side of the border pane.
     */
    public void setButtonWidths() {
        // Set button widths to match size of left side of border pane
        homeBtn.prefWidthProperty().bind(mainBorderPane.widthProperty());
        addBtn.prefWidthProperty().bind(mainBorderPane.widthProperty());
        findBtn.prefWidthProperty().bind(mainBorderPane.widthProperty());
        manageBtn.prefWidthProperty().bind(mainBorderPane.widthProperty());
        aboutBtn.prefWidthProperty().bind(mainBorderPane.widthProperty());
    }

    /**
     * This method is used to set the callbacks for the buttons in the sidebar.
     */
    public void setButtonCallbacks() {
        homeBtn.setOnAction(event -> loadHomePage());
        addBtn.setOnAction(event -> loadAddPage());
        findBtn.setOnAction(event -> loadFindPage());
        manageBtn.setOnAction(event -> loadManagePage());
        aboutBtn.setOnAction(event -> loadAboutPage());
    }

    /**
     * This method is used to load the home page.
     */
    public void loadHomePage() {
        if (currentLoadedPage.equals(UIConstants.HOME_PAGE_PATH) && (!firstLoad)) {
            return;
        }
        System.out.println("Loading home page");

        // Load the home page and set the controller
        FXMLLoader loader = loadPageWithFadeTransition(UIConstants.HOME_PAGE_PATH);
        HomePageController homePageController = loader.getController();
        homePageController.setMainWindowController(this);

        currentLoadedPage = UIConstants.HOME_PAGE_PATH;
        firstLoad = false;
    }

    /**
     * This method is used to load the add page.
     */
    public void loadAddPage() {
        if (currentLoadedPage.equals(UIConstants.ADD_PAGE_PATH)) {
            return;
        }
        System.out.println("Loading add page");

        // Load the add page and set the controller
        FXMLLoader loader = loadPageWithFadeTransition(UIConstants.ADD_PAGE_PATH);
        AddPageController addPageController = loader.getController();
        addPageController.setMainWindowController(this);

        currentLoadedPage = UIConstants.ADD_PAGE_PATH;
    }

    /**
     * This method is used to load the find page.
     */
    public void loadFindPage() {
        if (currentLoadedPage.equals(UIConstants.FIND_PAGE_PATH)) {
            return;
        }
        System.out.println("Loading find page");

        FXMLLoader loader = loadPageWithFadeTransition(UIConstants.FIND_PAGE_PATH);
        FindPageController findPageController = loader.getController();
        findPageController.setMainWindowController(this);

        currentLoadedPage = UIConstants.FIND_PAGE_PATH;
    }

    /**
     * This method is used to load the manage page.
     */
    public void loadManagePage() {
        if (currentLoadedPage.equals(UIConstants.MANAGE_PAGE_PATH)) {
            return;
        }
        System.out.println("Loading manage page");

        FXMLLoader loader = loadPageWithFadeTransition(UIConstants.MANAGE_PAGE_PATH);
        ManagePageController managePageController = loader.getController();
        managePageController.setMainWindowController(this);

        currentLoadedPage = UIConstants.MANAGE_PAGE_PATH;
    }

    /**
     * This method is used to load the about page.
     */
    public void loadAboutPage() {
        if (currentLoadedPage.equals(UIConstants.ABOUT_PAGE_PATH)) {
            return;
        }
        System.out.println("Loading about page");

        FXMLLoader loader = loadPageWithFadeTransition(UIConstants.ABOUT_PAGE_PATH);
        AboutPageController aboutPageController = loader.getController();
        aboutPageController.setMainWindowController(this);

        currentLoadedPage = UIConstants.ABOUT_PAGE_PATH;
    }

    /**
     * This method is used to load a page with a fade transition.
     * The returned FXMLLoader object can be used to pass a reference of MainWindowController to the controller of the loaded page.
     *
     * @param fxmlPath The path to the FXML file.
     * @return The FXMLLoader object.
     */
    public FXMLLoader loadPageWithFadeTransition(String fxmlPath) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        try {
            // Load the new page
            Node newPage = fxmlLoader.load();

            // Create the fade transition
            FadeTransition ft = new FadeTransition(Duration.millis(250), newPage);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);

            // Set the new page to the center of the border pane
            mainBorderPane.setCenter(newPage);

            // Start the transition
            ft.play();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading page");
        }

        return fxmlLoader;

    }

    /**
     * This method is used to set the resize callback for the main border pane.
     */
    public void setResizeCallback() {
        mainBorderPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Width: " + newValue);
        });

        mainBorderPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Height: " + newValue);
        });
    }

}
