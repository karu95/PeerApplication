package com.peerapplication.controller;

import javafx.application.Platform;
import message.Message;
import message.RegisterMessage;
import message.RequestStatusMessage;
import com.peerapplication.messenger.PeerHandler;
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

public class SignupController implements Initializable, UIUpdater{

    @FXML
    private Label statusLabel;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnSignup;

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtCnfmPassword;



    @FXML
    void confirmSignup(MouseEvent event) {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String cnfmPassword = txtCnfmPassword.getText().trim();
        if (!(username.isEmpty()) && !(password.isEmpty()) && !(cnfmPassword.isEmpty())){
            if (username.length()<8 || username.length()>20){
                statusLabel.setText("Username should have 8-20 characters!");
                txtUsername.clear();
            } else if(!username.matches("[a-zA-Z0-9]+")) {
                statusLabel.setText("Username should contain only alpha-numeric characters!");
                txtUsername.clear();
            } else if (password.length()<8 || password.length()>20) {
                statusLabel.setText("Password should have 8-20 characters!");
                txtCnfmPassword.clear();
                txtPassword.clear();
            }else if(password.contains("[-*/|&^+ ]+")){
                statusLabel.setText("Password shouldn't contain empty spaces or -*/|&^+ characters!");
            } else if (!password.equals(cnfmPassword)){
                statusLabel.setText("Password mismatch!");
                txtPassword.clear();
                txtCnfmPassword.clear();
            } else {
                RegisterMessage regMsg = new RegisterMessage();
                regMsg.setUsername(username);
                regMsg.setPassword(password);
                PeerHandler.getBsHandler().signup(regMsg);
            }
        } else {
            statusLabel.setText("All fields are required!");
        }


    }

    @FXML
    void openLogin(MouseEvent event) throws IOException {
        Stage stage = (Stage)btnLogin.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        Scene scene = new Scene(root, 1035, 859);
        stage.setTitle("Login");
        stage.setScene(scene);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.setRegisterUpdater(this);
    }

    @Override
    public void updateUI(Message message) {
        RequestStatusMessage req = (RequestStatusMessage) message;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                statusLabel.setText(req.getStatus());
            }
        });
    }
}

