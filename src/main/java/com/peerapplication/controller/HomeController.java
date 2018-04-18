package com.peerapplication.controller;

import com.peerapplication.model.Notification;
import com.peerapplication.model.Thread;
import com.peerapplication.model.User;
import com.peerapplication.util.ControllerUtility;
import com.peerapplication.util.SystemUser;
import com.peerapplication.util.UIUpdater;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import message.AnswerMessage;
import message.Message;
import message.ThreadMessage;
import message.VoteMessage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class HomeController implements Initializable, UIUpdater {

    @FXML
    private TabPane homeTabs;

    @FXML
    private Tab latestThreadTab;

    @FXML
    private TableView<Thread> latestThreadsTable;

    @FXML
    private TableColumn<Thread, String> colTitleLatest;

    @FXML
    private Tab myThreadTab;

    @FXML
    private TableView<Thread> myThreadsTable;

    @FXML
    private TableColumn<Thread, String> colTitleMyThreads;

    @FXML
    private Tab notificationTab;

    @FXML
    private TableView<Notification> notificationsTable;

    @FXML
    private TableColumn<Notification, String> colNotifications;

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
    private Button btnPostThread;

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

    private ObservableList<Thread> latestThreads;

    private ObservableList<Thread> myThreads;

    private Stage stage;

    @FXML
    void btnHomeClicked(MouseEvent event) {

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
    void postThread(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/postthread.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void openLatestThread(MouseEvent event) {
        Thread thread = latestThreadsTable.getSelectionModel().getSelectedItem();
        if (thread != null) {
            ControllerUtility.viewThread(stage, thread);
        }
    }

    @FXML
    void openMyThread(MouseEvent event) {
        Thread thread = myThreadsTable.getSelectionModel().getSelectedItem();
        ControllerUtility.viewThread(stage, thread);
    }

    @FXML
    void openNotification(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        notificationTab.setOnSelectionChanged(ActionEvent -> {
            if (notificationTab.isSelected()) {
                System.out.println("Notification");
            }
        });

        btnHome.setDisable(true);

        myThreadTab.setOnSelectionChanged(ActionEvent -> {
            if (myThreadTab.isSelected()) {
                System.out.println("MyThread");
            }
        });

        latestThreadTab.setOnSelectionChanged(ActionEvent -> {
            if (latestThreadTab.isSelected()) {
                System.out.println("LatestThread");
            }
        });

        latestThreads = FXCollections.observableArrayList(Thread.getLatestThreads(0));
        colTitleLatest.setCellValueFactory(new PropertyValueFactory<>("title"));
        latestThreadsTable.setItems(latestThreads);

        myThreads = FXCollections.observableArrayList(Thread.getUserThreads(SystemUser.getSystemUserID()));
        colTitleMyThreads.setCellValueFactory(new PropertyValueFactory<>("title"));
        myThreadsTable.setItems(myThreads);

        if (notificationTab.isSelected()) {

        } else if (myThreadTab.isSelected()) {

        } else if (latestThreadTab.isSelected()) {

        }
    }

    @Override
    public void updateUI(Message message) {
        if (message instanceof ThreadMessage) {
            ThreadMessage threadMessage = (ThreadMessage) message;

        } else if (message instanceof AnswerMessage) {
            AnswerMessage answerMessage = (AnswerMessage) message;

        } else if (message instanceof VoteMessage) {
            VoteMessage voteMessage = (VoteMessage) message;

        }
    }

    public void init(User user) {
        this.user = user;
        user.getUserWithImage(user.getUserID());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage = (Stage) btnPostThread.getScene().getWindow();
                BufferedImage image = user.getUserImage();
                if (image != null) {
                    userImage.setImage(SwingFXUtils.toFXImage(image, null));
                }
                lblName.setText(user.getName());
                lblRegDate.setText("Joined on " + String.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(new Date(user.getRegisterTime()))));
                lblPostedThreads.setText("Posted Threads : " + String.valueOf(Thread.getUserThreads(user.getUserID()).size()));
                lblEmail.setText(user.getEmail());
            }
        });
    }
}
