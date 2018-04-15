package com.peerapplication.controller;

import com.peerapplication.model.User;
import com.peerapplication.util.UIUpdater;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import message.Message;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewUserController implements Initializable, UIUpdater {

    @FXML
    private TabPane homeTabs;

    @FXML
    private Tab myThreadTab;

    @FXML
    private TableView<?> postedThreadsTable;

    @FXML
    private TableColumn<?, ?> colAnswers;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private ImageView userImage;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPostedThreads;

    @FXML
    private Label lblAnsweredThreads;

    @FXML
    private Label lblRegDate;

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
        btnHomeClicked(event);
    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void openSettings(ActionEvent event) {

    }

    @Override
    public void updateUI(Message message) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(User user) {

    }
}
