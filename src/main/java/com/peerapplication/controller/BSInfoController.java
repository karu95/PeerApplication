package com.peerapplication.controller;

import com.peerapplication.util.ControllerUtility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import messenger.PeerHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class BSInfoController implements Initializable {

    @FXML
    private TextField txtBSIP;

    @FXML
    private TextField txtBSPort;

    @FXML
    private Label lblStatus;

    @FXML
    private Button btnConnect;

    private ExecutorService connectionTester;

    @FXML
    void connect(MouseEvent event) throws IOException {
        int port = Integer.parseInt(txtBSPort.getText().trim());
        String ipAddress = txtBSIP.getText().trim();

        if (!portNumberValidator(port)) {
            lblStatus.setText("Invalid port number!");
        } else if (!ipAddressValidator(ipAddress)) {
            lblStatus.setText("Invalid IP address!");
        } else {
            PeerHandler.getBS().setPeerAddress(ipAddress);
            PeerHandler.getBS().setPeerPort(port);
            PeerHandler.getBS().setUserID(1);
            Stage primaryStage = (Stage) btnConnect.getScene().getWindow();
            ControllerUtility.login(primaryStage);
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
        connectionTester = Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = Executors.defaultThreadFactory().newThread(r);
                thread.setDaemon(true);
                return thread;
            }
        });
        if (!PeerHandler.checkConnection()) {
            lblStatus.setText("No network connection!");
            btnConnect.setDisable(true);
            connectionTester.execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (PeerHandler.checkConnection()) {
                            btnConnect.setDisable(false);
                            break;
                        }
                    }
                }
            });
        }
        txtBSIP.setText("192.168.8.100");
        txtBSPort.setText("25025");
    }
}
