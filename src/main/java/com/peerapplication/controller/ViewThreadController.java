package com.peerapplication.controller;

import com.peerapplication.handler.AnswerHandler;
import com.peerapplication.handler.ThreadHandler;
import com.peerapplication.handler.VoteHandler;
import com.peerapplication.model.*;
import com.peerapplication.model.Thread;
import com.peerapplication.util.*;
import com.peerapplication.validator.AnswerValidator;
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
import messenger.PeerHandler;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewThreadController implements Initializable, UIUpdater {

    @FXML
    private TextArea txtAreaAnswer;

    @FXML
    private Button btnAnswer;

    @FXML
    private Label statusLbl;

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

    @FXML
    private Button btnDelThread;

    private Text previousText;

    private Stage stage;

    private Thread thread;

    private HashMap<Integer, User> relatedUsers;

    private HashMap<String, Hyperlink> voteLinks;

    private ExecutorService senderService;

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
    void deleteThread(MouseEvent event) {
        TextInputDialog confirmDelete = new TextInputDialog();
        confirmDelete.setTitle("Do you want to delete?");
        confirmDelete.setHeaderText("Type \"CONFIRM\" to delete.");
        Optional<String> result = confirmDelete.showAndWait();
        if (result.isPresent() && result.get().equals("CONFIRM") && PeerHandler.checkConnection()) {
            DeletedThread deletedThread = new DeletedThread(thread.getThreadID(), SystemUser.getSystemUserID(),
                    new Date(System.currentTimeMillis()).getTime());
            deletedThread.deleteThread();
            senderService.execute(new Runnable() {
                @Override
                public void run() {
                    ThreadHandler.postDeleteThread(deletedThread);
                }
            });
            ControllerUtility.openHome(stage);
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    statusLbl.setText("Thread deletion not completed!");
                }
            });
        }
    }

    @FXML
    void saveAnswer(MouseEvent event) {
        Answer answer = new Answer();
        answer.setTimestamp(new Date(System.currentTimeMillis()).getTime());
        answer.setDescription(txtAreaAnswer.getText().trim());
        answer.setAnswerID(IDGenerator.generateAnswerID(answer.getTimestamp()));
        answer.setThreadID(thread.getThreadID());
        answer.setPostedUserID(SystemUser.getSystemUserID());
        answer.setVotes(new ArrayList<>());
        String validity = AnswerValidator.validateAnswer(answer);
        if (validity.equals("valid") && PeerHandler.checkConnection()) {
            addAnswer(answer);
            answer.saveAnswer();
            senderService.execute(new Runnable() {
                @Override
                public void run() {
                    AnswerHandler.postAnswer(answer);
                }
            });
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    statusLbl.setText(validity);
                }
            });
        }
        txtAreaAnswer.clear();
    }

    @Override
    public void updateUI(Message message) {
        if (message instanceof AnswerMessage) {
            AnswerMessage answerMessage = (AnswerMessage) message;
            if (answerMessage.getAnswer().getThreadID().equals(thread.getThreadID())) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        addAnswer(answerMessage.getAnswer());
                    }
                });
            }
        } else if (message instanceof DeleteThreadMessage) {
            DeleteThreadMessage deleteThreadMessage = (DeleteThreadMessage) message;
            if (deleteThreadMessage.getDeletedThread().getThreadID().equals(thread.getThreadID())) {
                ControllerUtility.openHome(stage);
            }
        } else if (message instanceof VoteMessage) {
            VoteMessage voteMessage = (VoteMessage) message;
            Hyperlink voteLink = voteLinks.get(voteMessage.getVote().getAnswerID());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    voteLink.setText("Voted : " + String.valueOf(Integer.valueOf(voteLink.getText().substring(8)) + 1));
                }
            });
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UIUpdateHandler.refreshUpdater();
        UIUpdateHandler.setAnswerUpdater(this);
        UIUpdateHandler.setVoteUpdater(this);
        UIUpdateHandler.setThreadUpdater(this);
        voteLinks = new HashMap<>();
        senderService = Executors.newSingleThreadExecutor();
        if (SystemUser.getAccountType() == 1) {
            btnDelThread.setDisable(false);
        } else {
            btnDelThread.setDisable(true);
        }
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
        if (answer.getPostedUserID() != SystemUser.getSystemUserID()) {
            userLink.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ControllerUtility.viewUser(stage, relatedUsers.get(answer.getPostedUserID()));
                }
            });
        } else {
            userLink.setDisable(true);
        }
        TextFlow answerFlow = new TextFlow(txtAnswerDetail, userLink);
        answerFlow.setLayoutX(txtThreadHeader.getLayoutX());
        answerFlow.setLayoutY(previousText.getLayoutY() + previousText.getBoundsInLocal().getHeight());
        Hyperlink voteLink = new Hyperlink();
        voteLink.setText("Voted : " + String.valueOf(answer.getVotes().size()));
        for (Vote vote : answer.getVotes()) {
            if (vote.getUserID() == SystemUser.getSystemUserID()) {
                voteLink.setDisable(true);
                break;
            }
        }
        voteLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                voteLink.setText("Voted : " + String.valueOf(Integer.valueOf(voteLink.getText().substring(8)) + 1));
                voteLink.setDisable(true);
                Vote vote = new Vote();
                vote.setVotedTime(new Date(System.currentTimeMillis()).getTime());
                vote.setUserID(SystemUser.getSystemUserID());
                vote.setAnswerID(answer.getAnswerID());
                vote.saveVote();
                senderService.execute(new Runnable() {
                    @Override
                    public void run() {
                        VoteHandler.getVoteHandler().postVote(vote);
                    }
                });
            }
        });
        voteLink.setLayoutX(txtThreadHeader.getLayoutX() + 800);
        voteLink.setLayoutY(answerFlow.getLayoutY());
        voteLinks.putIfAbsent(answer.getAnswerID(), voteLink);
        aPaneThread.getChildren().add(answerFlow);
        aPaneThread.getChildren().add(voteLink);
        txtAnswerDesc.setText(answer.getDescription());
        txtAnswerDesc.setLayoutX(txtThreadHeader.getLayoutX());
        txtAnswerDesc.setLayoutY(answerFlow.getLayoutY() + answerFlow.getBoundsInLocal().getHeight() + 20);
        txtAnswerDesc.setWrappingWidth(txtThreadHeader.getWrappingWidth());
        aPaneThread.getChildren().add(txtAnswerDesc);
        previousText = txtAnswerDesc;
    }
}
