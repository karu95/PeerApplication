package com.peerapplication.controller;

import com.peerapplication.util.UIUpdater;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import message.Message;
import message.ThreadMessage;

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

    @FXML
    void btnHomeClicked(MouseEvent event) {

    }

    @FXML
    void btnThreadsClicked(MouseEvent event) {

    }

    @FXML
    void headingClicked(MouseEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void openSettings(ActionEvent event) {

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

    }
}
