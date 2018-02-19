package com.peerapplication.controller;

import com.peerapplication.messenger.PeerHandler;
import javafx.application.Platform;
import message.LoginMessage;
import message.Message;
import message.RequestStatusMessage;
import com.peerapplication.util.Main;
import com.peerapplication.util.UIUpdater;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable, UIUpdater{

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
    void confirmLogin(MouseEvent event) {
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
                LoginMessage loginMessage = new LoginMessage(username, password);
                PeerHandler.getBsHandler().login(loginMessage);
            }
        } else {
            statusLabel.setText("All fields are required!");
        }

    }

    @FXML
    void openSignup(MouseEvent event) throws IOException {
        Stage stage = (Stage)btnRegister.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/signup.fxml"));
        Scene scene = new Scene(root, 1035, 859);
        stage.setTitle("Sign Up");
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.setLoginUpdater(this);
    }

    @Override
    public void updateUI(Message message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage stage = (Stage)btnRegister.getScene().getWindow();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/homepage.fxml"));
                    Scene scene = new Scene(root, 1035, 859);
                    stage.setTitle("Home");
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

