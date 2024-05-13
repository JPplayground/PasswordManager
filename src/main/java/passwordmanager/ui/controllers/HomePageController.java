package passwordmanager.ui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HomePageController {

    @FXML
    VBox homePageRootVBox, addPageInfoBox, managePageInfoBox, searchPageInfoBox, aboutPageInfoBox;
    @FXML
    Label welcomeLabel, addPageHeader, managePageHeader, searchPageHeader, aboutPageHeader;
    @FXML
    GridPane infoBoxesGridPane;
    @FXML
    Text addPageInfo, managePageInfo, findPageInfo, aboutPageInfo;
    @FXML
    Button addPageGo, searchPageGo, managePageGo, aboutPageGo;

    private MainWindowController mainWindowController;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    @FXML
    public void initialize() {

        setButtonCallbacks();

        // Not doing this causes the text to not wrap properly
        // Something about the Vbox not being fully rendered yet
        Platform.runLater(() -> {
            bindTextWidths();
        });
    }

    /**
     * Sets the button callbacks for the buttons on the home page.
     */
    private void setButtonCallbacks() {
        addPageGo.setOnAction(e -> mainWindowController.loadAddPage());
        searchPageGo.setOnAction(e -> mainWindowController.loadFindPage());
        managePageGo.setOnAction(e -> mainWindowController.loadManagePage());
        aboutPageGo.setOnAction(e -> mainWindowController.loadAboutPage());
    }

    private void bindTextWidths() {
        addPageInfo.wrappingWidthProperty().bind(addPageInfoBox.widthProperty().multiply(0.85));
        managePageInfo.wrappingWidthProperty().bind(managePageInfoBox.widthProperty().multiply(0.85));
        findPageInfo.wrappingWidthProperty().bind(searchPageInfoBox.widthProperty().multiply(0.85));
        aboutPageInfo.wrappingWidthProperty().bind(aboutPageInfoBox.widthProperty().multiply(0.85));

    }



}
