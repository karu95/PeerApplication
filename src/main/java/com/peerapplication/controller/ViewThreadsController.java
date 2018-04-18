package com.peerapplication.controller;

import com.peerapplication.util.ControllerUtility;
import com.peerapplication.util.UIUpdater;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import message.Message;
import message.ThreadMessage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewThreadsController implements UIUpdater, Initializable {

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnSearch;

    @FXML
    private TableView<?> threadTable;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private Label lblHeading;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnThreads;

    @FXML
    private MenuButton btnMenu;

    @FXML
    private MenuItem menuItemSettings;

    @FXML
    private MenuItem menuItemLogout;

    private Stage stage;

    @FXML
    void btnHomeClicked(MouseEvent event) {
        ControllerUtility.openHome(stage);
    }

    @FXML
    void btnThreadsClicked(MouseEvent event) {

    }

    @FXML
    void headingClicked(MouseEvent event) {
        btnHomeClicked(event);
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        ControllerUtility.logout(stage);
    }

    @FXML
    void openSettings(ActionEvent event) {
        ControllerUtility.openSettings(stage);
    }

    @FXML
    void openThread(MouseEvent event) {

    }

    @FXML
    void search(MouseEvent event) {

    }

    @Override
    public void updateUI(Message message) {
        if (message instanceof ThreadMessage) {
            ThreadMessage threadMessage = (ThreadMessage) message;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnThreads.setDisable(true);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage = (Stage) btnSearch.getScene().getWindow();
            }
        });
    }
}
