package com.peerapplication.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import messenger.PeerHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class BSInfoController implements Initializable {

    @FXML
    private TextField txtBSIP;

    @FXML
    private TextField txtBSPort;

    @FXML
    private Label lblStatus;

    @FXML
    private Button btnConnect;

    @FXML
    void connect(MouseEvent event) {
        int port = Integer.parseInt(txtBSPort.getText().trim());
        String ipAddress = txtBSIP.getText().trim();

        if (!portNumberValidator(port)) {
            lblStatus.setText("Invalid port number!");
        } else if (!ipAddressValidator(ipAddress)) {
            lblStatus.setText("Invalid IP address!");
        } else {
            PeerHandler.getBS().setPeerAddress(ipAddress);
            PeerHandler.getBS().setPeerPort(port);
        }
    }

    private boolean portNumberValidator(int port) {
        boolean valid = false;
        if ((port > 1024) && (port < 65536)) {
            valid = true;
        }
        return valid;
    }

    private boolean ipAddressValidator(String ipAdd) {
        boolean valid = true;
        if (!(ipAdd.matches("[0-9.]+") && (ipAdd.length() > 0 && ipAdd.length() < 16))) {
            valid = false;
        }
        return valid;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtBSPort.setText("25030");
    }
}
