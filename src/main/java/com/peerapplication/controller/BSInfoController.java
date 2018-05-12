package com.peerapplication.controller;

import com.peerapplication.util.ControllerUtility;
import javafx.application.Platform;
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

    private boolean testConnection;

    private String error;

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
            testConnection = false;
            connectionTester.shutdown();
            PeerHandler.startConnectionTest();
            ControllerUtility.login();
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
        error = "";
        this.testConnection = true;
        connectionTester = Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = Executors.defaultThreadFactory().newThread(r);
                thread.setDaemon(true);
                return thread;
            }
        });
        connectionTester.execute(new Runnable() {
            @Override
            public void run() {
                while (testConnection) {
                    if (PeerHandler.checkConnection()) {
                        btnConnect.setDisable(false);
                        if (!error.equals("Unable to connect to BS")) {
                            error = "Provide BS Information!";
                        }
                    } else {
                        btnConnect.setDisable(true);
                        error = "No network connection!";
                    }
                    if (!error.equals(lblStatus.getText())) {
                        String finalError = error;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                lblStatus.setText(finalError);
                            }
                        });
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        txtBSIP.setText("192.168.8.102");
        txtBSPort.setText("25025");
    }

    public void init() {
        error = "Unable to connect to BS";
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lblStatus.setText("Unable to connect to BS");
            }
        });
    }
}
