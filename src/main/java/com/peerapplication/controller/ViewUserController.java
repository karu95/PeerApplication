package com.peerapplication.controller;

import com.peerapplication.model.User;
import com.peerapplication.util.ControllerUtility;
import com.peerapplication.util.UIUpdater;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import message.Message;

import java.io.IOException;
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

    private User user;

    @FXML
    void btnHomeClicked(MouseEvent event) {
        Stage stage = (Stage) btnHome.getScene().getWindow();
        ControllerUtility.openHome(stage);
    }

    @FXML
    void btnThreadsClicked(MouseEvent event) throws IOException {
        Stage stage = (Stage) btnHome.getScene().getWindow();
        ControllerUtility.openThreads(stage);
    }

    @FXML
    void headingClicked(MouseEvent event) {
        btnHomeClicked(event);
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnHome.getScene().getWindow();
        ControllerUtility.logout(stage);
    }

    @FXML
    void openSettings(ActionEvent event) {
        Stage stage = (Stage) btnHome.getScene().getWindow();
        ControllerUtility.openSettings(stage);
    }

    @FXML
    void openThread(MouseEvent event) {

    }

    @Override
    public void updateUI(Message message) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(User user) {
        this.user = user;

    }
}
