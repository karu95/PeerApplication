package com.peerapplication.controller;

import com.peerapplication.handler.BSHandler;
import com.peerapplication.handler.ForumUpdateHandler;
import com.peerapplication.model.User;
import com.peerapplication.util.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import message.LoginMessage;
import message.Message;
import message.RequestStatusMessage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class LoginController implements Initializable, UIUpdater {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;

    @FXML
    private Label statusLabel;

    @FXML
    void confirmLogin(MouseEvent event) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        if (!(username.isEmpty()) && !(password.isEmpty())) {
            if (username.length() < 8 || username.length() > 20) {
                statusLabel.setText("Invalid username!");
                txtUsername.clear();
            } else if (!username.matches("[a-zA-Z0-9]+")) {
                statusLabel.setText("Invalid username!");
                txtUsername.clear();
            } else if (password.length() < 8 || password.length() > 20) {
                statusLabel.setText("Invalid password!");
                txtPassword.clear();
            } else {
                String pw = PasswordEncrypter.SHA1(password);
                System.out.println(pw);
                LoginMessage loginMessage = new LoginMessage(username, pw);
                BSHandler.login(loginMessage);
            }
        } else {
            statusLabel.setText("All fields are required!");
        }

    }

    @FXML
    void openSignup(MouseEvent event) throws IOException {
        Stage stage = (Stage) btnRegister.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/signup.fxml"));
        Scene scene = new Scene(root, 1035, 859);
        stage.setTitle("Sign Up");
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UIUpdateHandler.refreshUpdater();
        UIUpdateHandler.setLoginUpdater(this);
    }

    @Override
    public void updateUI(Message message) {
        if (message instanceof RequestStatusMessage) {
            RequestStatusMessage reqMessage = (RequestStatusMessage) message;
            if (!(reqMessage.getStatus().equals("Success"))) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        statusLabel.setText(reqMessage.getStatus());
                    }
                });
            } else {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Stage stage = (Stage) btnRegister.getScene().getWindow();
                        try {
                            ForumUpdateHandler.requestUpdate();
                            User user = new User(SystemUser.getSystemUserID());
                            if (user.getUserID() == SystemUser.getSystemUserID()) {
                                ControllerUtility.openHome(stage);
                            } else {
                                ControllerUtility.openRegister(stage);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}

