package com.jp.passwordmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class AddPageController {

    @FXML
    private TextField titleEntry;
    @FXML
    private TextField emailEntry;
    @FXML
    private TextField passwordEntry;
    @FXML
    private TextField usernameEntry;
    @FXML
    private TextField linkEntry;
    @FXML
    private TextField groupEntry;

    @FXML
    private Button addCommonEmailBtn;
    @FXML
    private Button clearEntryFieldsBtn;
    @FXML
    private Button addEntryBtn;

    @FXML
    private ChoiceBox<String> commonEmailDropdown;
    @FXML
    private ChoiceBox<String> groupDropdown;




}
