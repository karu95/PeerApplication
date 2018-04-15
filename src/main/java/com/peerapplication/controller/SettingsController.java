package com.peerapplication.controller;

import com.peerapplication.model.User;
import com.peerapplication.util.ControllerUtility;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private ImageView userImage;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label statusLabelUser;

    @FXML
    private PasswordField txtOldPwd;

    @FXML
    private PasswordField txtNewPwd;

    @FXML
    private PasswordField txtCnrmPwd;

    @FXML
    private Button btnChangePwd;

    @FXML
    private Label statusLabelPwd;

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
        Stage stage = (Stage) btnChangePwd.getScene().getWindow();
        ControllerUtility.openHome(stage);
    }

    @FXML
    void btnThreadsClicked(MouseEvent event) throws IOException {
        Stage stage = (Stage) btnChangePwd.getScene().getWindow();
        ControllerUtility.openThreads(stage);
    }

    @FXML
    void headingClicked(MouseEvent event) {
        btnHomeClicked(event);
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnChangePwd.getScene().getWindow();
        ControllerUtility.logout(stage);
    }

    @FXML
    void openSettings(ActionEvent event) {
        Stage stage = (Stage) btnChangePwd.getScene().getWindow();
        ControllerUtility.openSettings(stage);
    }

    @FXML
    void updateSettings(MouseEvent event) {

    }

    @FXML
    void selectImage(MouseEvent event) {

    }

    @FXML
    void changePassword(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(User user) {
        this.user = user;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                userImage.setImage(SwingFXUtils.toFXImage(user.getUserImage(), null));
                txtName.setText(user.getName());
                txtEmail.setText(user.getEmail());
            }
        });
    }
}
