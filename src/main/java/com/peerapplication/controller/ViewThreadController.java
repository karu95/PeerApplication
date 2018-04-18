package com.peerapplication.controller;

import com.peerapplication.handler.AnswerHandler;
import com.peerapplication.model.Answer;
import com.peerapplication.model.Thread;
import com.peerapplication.model.User;
import com.peerapplication.util.ControllerUtility;
import com.peerapplication.util.IDGenerator;
import com.peerapplication.util.SystemUser;
import com.peerapplication.util.UIUpdater;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import message.AnswerMessage;
import message.DeleteThreadMessage;
import message.Message;
import message.VoteMessage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewThreadController implements Initializable, UIUpdater {

    @FXML
    private TextArea txtAreaAnswer;

    @FXML
    private Button btnAnswer;

    @FXML
    private AnchorPane aPaneThread;

    @FXML
    private Text txtThreadHeader;

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

    private Text previousText;

    private Stage stage;

    private Thread thread;

    private HashMap<Integer, User> relatedUsers;

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
    void saveAnswer(MouseEvent event) {
        Answer answer = new Answer();
        answer.setTimestamp(new Date(System.currentTimeMillis()).getTime());
        answer.setDescription(txtAreaAnswer.getText());
        answer.setAnswerID(IDGenerator.generateAnswerID(answer.getTimestamp()));
        answer.setThreadID(thread.getThreadID());
        answer.setPostedUserID(SystemUser.getSystemUserID());
        answer.saveAnswer();
        ExecutorService answerSender = Executors.newSingleThreadExecutor();
        answerSender.execute(new Runnable() {
            @Override
            public void run() {
                AnswerHandler.postAnswer(answer);
            }
        });
        addAnswer(answer);
        txtAreaAnswer.clear();
    }

    @Override
    public void updateUI(Message message) {
        if (message instanceof AnswerMessage) {
            AnswerMessage answerMessage = (AnswerMessage) message;

        } else if (message instanceof DeleteThreadMessage) {
            DeleteThreadMessage deleteThreadMessage = (DeleteThreadMessage) message;

        } else if (message instanceof VoteMessage) {
            VoteMessage voteMessage = (VoteMessage) message;

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage = (Stage) btnAnswer.getScene().getWindow();
            }
        });
    }

    public void init(Thread thread) {
        relatedUsers = new HashMap<>();
        thread.getCompleteThread(thread.getThreadID());
        this.thread = thread;
        relatedUsers.putIfAbsent(thread.getUserID(), new User(thread.getUserID()));
        txtThreadHeader.setText(thread.getTitle());
        Text txtThreadDetail = new Text();
        Hyperlink userLink = new Hyperlink();
        userLink.setText(relatedUsers.get(thread.getUserID()).getName());
        userLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ControllerUtility.viewUser(stage, relatedUsers.get(thread.getUserID()));
            }
        });
        txtThreadDetail.setText("Posted on " + new Date(thread.getTimestamp()) + " by");
        TextFlow threadFlow = new TextFlow(txtThreadDetail, userLink);
        threadFlow.setLayoutX(txtThreadHeader.getLayoutX());
        threadFlow.setLayoutY(txtThreadHeader.getLayoutY() + txtThreadHeader.getBoundsInLocal().getHeight());
        Text txtThreadDescription = new Text();
        txtThreadDescription.setText(thread.getDescription());
        txtThreadDescription.setWrappingWidth(txtThreadHeader.getWrappingWidth());
        txtThreadDescription.setLayoutX(txtThreadHeader.getLayoutX());
        txtThreadDescription.setLayoutY(threadFlow.getLayoutY() + threadFlow.getBoundsInLocal().getHeight() + 20);
        aPaneThread.getChildren().add(threadFlow);
        aPaneThread.getChildren().add(txtThreadDescription);
        previousText = txtThreadDescription;
        for (Answer answer : thread.getAnswers()) {
            addAnswer(answer);
        }
    }

    private void addAnswer(Answer answer) {
        Text txtAnswerDetail = new Text();
        Text txtAnswerDesc = new Text();
        txtAnswerDetail.setText("Answered on " + new Date(answer.getTimestamp()) + " by");
        Hyperlink userLink = new Hyperlink();
        if (relatedUsers.containsKey(answer.getPostedUserID())) {
            userLink.setText(relatedUsers.get(answer.getPostedUserID()).getName());
        } else {
            relatedUsers.putIfAbsent(answer.getPostedUserID(), new User(answer.getPostedUserID()));
            userLink.setText(relatedUsers.get(answer.getPostedUserID()).getName());
        }
        userLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ControllerUtility.viewUser(stage, relatedUsers.get(answer.getPostedUserID()));
            }
        });
        TextFlow answerFlow = new TextFlow(txtAnswerDetail, userLink);
        answerFlow.setLayoutX(txtThreadHeader.getLayoutX());
        answerFlow.setLayoutY(previousText.getLayoutY() + previousText.getBoundsInLocal().getHeight());
        aPaneThread.getChildren().add(answerFlow);
        txtAnswerDesc.setText(answer.getDescription());
        txtAnswerDesc.setLayoutX(txtThreadHeader.getLayoutX());
        txtAnswerDesc.setLayoutY(answerFlow.getLayoutY() + answerFlow.getBoundsInLocal().getHeight() + 20);
        txtAnswerDesc.setWrappingWidth(txtThreadHeader.getWrappingWidth());
        aPaneThread.getChildren().add(txtAnswerDesc);
        previousText = txtAnswerDesc;
    }
}
