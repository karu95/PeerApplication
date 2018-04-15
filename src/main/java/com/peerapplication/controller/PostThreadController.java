package com.peerapplication.controller;

import com.peerapplication.handler.ThreadHandler;
import com.peerapplication.model.Tag;
import com.peerapplication.model.Thread;
import com.peerapplication.util.IDGenerator;
import com.peerapplication.util.SystemUser;
import com.peerapplication.validator.ThreadValidator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import messenger.PeerHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PostThreadController {

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

    @FXML
    void btnHomeClicked(MouseEvent event) {

    }

    @FXML
    void btnThreadsClicked(MouseEvent event) {

    }

    @FXML
    void headingClicked(MouseEvent event) {

    }

    @FXML
    void post(MouseEvent event) throws IOException {
        Thread thread = new Thread();
        thread.setTimestamp(new Date(System.currentTimeMillis()).getTime());
        thread.setDescription(txtDescription.getText());
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
        if (validity.equals("valid")) {
            if (PeerHandler.checkConnection()) {
                thread.saveThread();
                ExecutorService postThreadService = Executors.newSingleThreadExecutor();
                postThreadService.execute(new Runnable() {
                    @Override
                    public void run() {
                        ThreadHandler.postThread(thread);
                    }
                });
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/viewthread.fxml"));
                Parent parent = loader.load();
                ViewThreadController viewThreadController = loader.getController();
                viewThreadController.init(thread);
                Scene scene = new Scene(parent, 1035, 859);
                Stage stage = (Stage) btnPost.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("View Thread");
                stage.show();
            }
        } else {
            statusLabel.setText(validity);
        }
    }

}
