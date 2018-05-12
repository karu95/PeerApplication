package com.peerapplication.controller;

import com.peerapplication.model.Thread;
import com.peerapplication.model.User;
import com.peerapplication.util.ControllerUtility;
import com.peerapplication.util.UIUpdateHandler;
import com.peerapplication.util.UIUpdater;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import message.Message;
import message.ThreadMessage;
import message.UserInfoMessage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ViewUserController implements Initializable, UIUpdater {

    @FXML
    private TabPane homeTabs;

    @FXML
    private Tab myThreadTab;

    @FXML
    private TableView<Thread> postedThreadsTable;

    @FXML
    private TableColumn<Thread, String> colTitle;

    @FXML
    private ImageView userImage;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPostedThreads;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblRegDate;

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

    private ObservableList<Thread> postedThreads;

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
    void openThread(MouseEvent event) {
        Thread thread = postedThreadsTable.getSelectionModel().getSelectedItem();
        if (thread != null) {
            ControllerUtility.viewThread(thread);
        }
    }

    @Override
    public void updateUI(Message message) {
        if (message instanceof UserInfoMessage) {
            UserInfoMessage userInfoMessage = (UserInfoMessage) message;
            if (userInfoMessage.getUser().getUserID() == user.getUserID()) {
                this.user = userInfoMessage.getUser();
                updateInterface();
            }
        } else if (message instanceof ThreadMessage) {
            ThreadMessage threadMessage = (ThreadMessage) message;
            if (threadMessage.getThread().getUserID() == user.getUserID()) {
                postedThreads.add(0, threadMessage.getThread());
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UIUpdateHandler.refreshUpdater();
        UIUpdateHandler.setThreadUpdater(this);
        UIUpdateHandler.setUserInfoUpdater(this);
        updateInterface();
    }

    public void init(User user) {
        this.user = user;
        this.user.getUserWithImage(user.getUserID());
        postedThreads = FXCollections.observableArrayList(Thread.getUserThreads(user.getUserID()));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        postedThreadsTable.setItems(postedThreads);
    }

    private void updateInterface() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                BufferedImage bfdImage = user.getUserImage();
                if (bfdImage != null) {
                    userImage.setImage(SwingFXUtils.toFXImage(bfdImage, null));
                }
                lblName.setText(user.getName());
                lblRegDate.setText("Joined on " + String.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(new Date(user.getRegisterTime()))));
                lblPostedThreads.setText("Posted Threads : " + String.valueOf(Thread.getUserThreads(user.getUserID()).size()));
                lblEmail.setText(user.getEmail());
            }
        });
    }
}
