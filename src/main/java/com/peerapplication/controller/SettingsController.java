package com.peerapplication.controller;

import com.peerapplication.handler.BSHandler;
import com.peerapplication.handler.UserHandler;
import com.peerapplication.model.User;
import com.peerapplication.util.*;
import com.peerapplication.validator.UserValidator;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import message.Message;
import message.PasswordChangeMessage;
import message.RequestStatusMessage;
import messenger.PeerHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SettingsController implements Initializable, UIUpdater {

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
    private PasswordField txtCnfrmPwd;

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

    private FileChooser fileChooser;

    private UserValidator userValidator;

    private File file;

    private User user;

    private ExecutorService userUpdater;

    @FXML
    void btnHomeClicked(MouseEvent event) {
        ControllerUtility.openHome();
    }

    @FXML
    void btnThreadsClicked(MouseEvent event) throws IOException {
        ControllerUtility.openThreads("");
    }

    @FXML
    void headingClicked(MouseEvent event) {
        btnHomeClicked(event);
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        ControllerUtility.logout();
    }

    @FXML
    void openSettings(ActionEvent event) {
        ControllerUtility.openSettings();
    }

    @FXML
    void updateSettings(MouseEvent event) throws CloneNotSupportedException, IOException {                              // update settings
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        User updatedUser = new User();
        updatedUser.getUserWithImage(SystemUser.getSystemUserID());
        if (file != null) {                                                                                             //check image uploaded
            updatedUser.setUserImage(ImageIO.read(file));
            updatedUser.setImageURL(String.valueOf(user.getUserID()));
        }
        updatedUser.setName(name);
        updatedUser.setEmail(email);
        if (!updatedUser.equals(user)) {                                                                                // check whether user info updated
            String validity = userValidator.validate(updatedUser);
            if (validity.equals("Success") && PeerHandler.checkConnection()) {                                          //validate new user
                updatedUser.setLastProfileUpdate(new Date(System.currentTimeMillis()).getTime());
                userUpdater.execute(new Runnable() {
                    @Override
                    public void run() {
                        UserHandler.postUser(updatedUser);                                                              //send updated user info.
                    }
                });
                updatedUser.saveUser();                                                                                 // save updated user
                user = updatedUser;
                statusLabelUser.setText("User information updated successfully!");
            } else {
                statusLabelUser.setText(validity);
            }
        } else {
            System.out.println("No new update");
        }
    }

    @FXML
    void selectImage(MouseEvent event) throws IOException {
        Stage stage = (Stage) btnChangePwd.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            userImage.setImage(image);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userUpdater = Executors.newSingleThreadExecutor();
        menuItemSettings.setDisable(true);
        userValidator = UserValidator.getUserValidator();
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        fileChooser.setTitle("Select Profile Picture");
    }

    @FXML
    void changePassword(MouseEvent event) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String oldPwd = txtOldPwd.getText().trim();
        String newPwd = txtNewPwd.getText().trim();
        String cnfrmPwd = txtCnfrmPwd.getText().trim();
        if (!oldPwd.isEmpty()) {
            if (newPwd.equals(cnfrmPwd)) {
                if (newPwd.length() < 8 || newPwd.length() > 20) {
                    statusLabelPwd.setText("Password should have 8-20 characters!");
                } else if (newPwd.contains("[-*/|&^+ ]+")) {
                    statusLabelPwd.setText("Password shouldn't contain empty spaces or -*/|&^+ characters!");
                } else {
                    PasswordChangeMessage passwordChangeMessage = new PasswordChangeMessage();
                    passwordChangeMessage.setOldPassword(PasswordEncrypter.SHA1(oldPwd));
                    passwordChangeMessage.setNewPassword(PasswordEncrypter.SHA1(newPwd));
                    passwordChangeMessage.setUserID(SystemUser.getSystemUserID());
                    BSHandler.changePassword(passwordChangeMessage);
                    System.out.println("Success");
                    statusLabelPwd.setText("Please Wait!");
                }
            } else {
                statusLabelPwd.setText("Password doesn't match!");
            }
        } else {
            statusLabelPwd.setText("Please provide old password");
        }
        txtNewPwd.clear();
        txtOldPwd.clear();
        txtCnfrmPwd.clear();
    }

    public void init(User user) {
        this.user = user;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                BufferedImage image = user.getUserImage();
                if (image != null) {
                    userImage.setImage(SwingFXUtils.toFXImage(image, null));
                }
                txtName.setText(user.getName());
                txtEmail.setText(user.getEmail());
            }
        });
        UIUpdateHandler.setUserInfoUpdater(this);
    }

    @Override
    public void updateUI(Message message) {
        if (message instanceof RequestStatusMessage) {
            RequestStatusMessage reqStatusMessage = (RequestStatusMessage) message;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    statusLabelPwd.setText(reqStatusMessage.getStatus());
                }
            });
        }
    }
}
