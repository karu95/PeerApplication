package com.peerapplication.controller;

import com.peerapplication.model.Thread;
import com.peerapplication.model.User;
import com.peerapplication.notifcation.Notification;
import com.peerapplication.notifcation.NotificationHandler;
import com.peerapplication.util.ControllerUtility;
import com.peerapplication.util.SystemUser;
import com.peerapplication.util.UIUpdateHandler;
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
import message.DeleteThreadMessage;
import message.Message;
import message.ThreadMessage;

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

    @FXML
    void btnHomeClicked(MouseEvent event) {

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
    void postThread(MouseEvent event) throws IOException {
        Stage stage = (Stage) btnPostThread.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/postthread.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void openLatestThread(MouseEvent event) {
        Thread thread = latestThreadsTable.getSelectionModel().getSelectedItem();
        if (thread != null) {
            ControllerUtility.viewThread(thread);
        }
    }

    @FXML
    void openMyThread(MouseEvent event) {
        Thread thread = myThreadsTable.getSelectionModel().getSelectedItem();
        ControllerUtility.viewThread(thread);
    }

    @FXML
    void openNotification(MouseEvent event) {
        Thread thread = notificationsTable.getSelectionModel().getSelectedItem().getRelatedThread();
        NotificationHandler.getNotificationHandler().notificationRead(notificationsTable.getSelectionModel().getSelectedItem());
        ControllerUtility.viewThread(thread);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UIUpdateHandler.refreshUpdater();
        UIUpdateHandler.setThreadUpdater(this);
        UIUpdateHandler.setAnswerUpdater(this);
        UIUpdateHandler.setVoteUpdater(this);
        notificationTab.setOnSelectionChanged(ActionEvent -> {
            if (notificationTab.isSelected()) {
                System.out.println(NotificationHandler.getNotificationHandler().getNotifications().size());
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

        colNotifications.setCellValueFactory(new PropertyValueFactory<>("description"));
        notificationsTable.setItems(NotificationHandler.getNotificationHandler().getNotifications());
    }

    @Override
    public void updateUI(Message message) {
        if (message instanceof ThreadMessage) {
            ThreadMessage threadMessage = (ThreadMessage) message;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    latestThreads.add(0, threadMessage.getThread());
                }
            });
        } else if (message instanceof DeleteThreadMessage) {
            DeleteThreadMessage deleteThreadMessage = (DeleteThreadMessage) message;
            latestThreads = FXCollections.observableArrayList(Thread.getLatestThreads(0));
            myThreads = FXCollections.observableArrayList(Thread.getUserThreads(SystemUser.getSystemUserID()));
        }
    }

    public void init(User user) {
        this.user = user;
        user.getUserWithImage(user.getUserID());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
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
