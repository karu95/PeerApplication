package com.peerapplication.controller;

import com.peerapplication.handler.ThreadHandler;
import com.peerapplication.model.Tag;
import com.peerapplication.model.Thread;
import com.peerapplication.util.ControllerUtility;
import com.peerapplication.util.IDGenerator;
import com.peerapplication.util.SystemUser;
import com.peerapplication.validator.ThreadValidator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import messenger.PeerHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PostThreadController implements Initializable {

    @FXML
    private TextField txtTitle;

    @FXML
    private Button btnPost;

    @FXML
    private TextField txtTags;

    @FXML
    private Label statusLabel;

    @FXML
    private TextArea txtDescription;

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

    private Stage stage;

    @FXML
    void btnHomeClicked(MouseEvent event) {
        ControllerUtility.openHome(stage);
    }

    @FXML
    void btnThreadsClicked(MouseEvent event) throws IOException {
        ControllerUtility.openThreads(stage);
    }

    @FXML
    void headingClicked(MouseEvent event) {
        btnHomeClicked(event);
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        ControllerUtility.logout(stage);
    }

    @FXML
    void openSettings(ActionEvent event) {
        ControllerUtility.openSettings(stage);
    }

    @FXML
    void post(MouseEvent event) throws IOException {
        Thread thread = new Thread();
        thread.setTimestamp(new Date(System.currentTimeMillis()).getTime());
        thread.setDescription(txtDescription.getText().trim());
        thread.setTitle(txtTitle.getText());
        thread.setUserID(SystemUser.getSystemUserID());
        thread.setThreadID(IDGenerator.generateThreadID(thread.getTimestamp()));
        String[] tags = txtTags.getText().trim().split(",");
        ArrayList<Tag> relatedTags = new ArrayList<>();
        for (int i = 0; i < tags.length; i++) {
            relatedTags.add(new Tag(tags[i].trim()));
        }
        thread.setTags(relatedTags);
        String validity = ThreadValidator.validateThread(thread);
        if (validity.equals("valid") && PeerHandler.checkConnection()) {
            thread.saveThread();
            ExecutorService postThreadService = Executors.newSingleThreadExecutor();
            postThreadService.execute(new Runnable() {
                @Override
                public void run() {
                    ThreadHandler.postThread(thread);
                }
            });
            Stage stage = (Stage) btnPost.getScene().getWindow();
            ControllerUtility.viewThread(stage, thread);
        } else {
            statusLabel.setText(validity);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage = (Stage) btnPost.getScene().getWindow();
            }
        });
    }
}
