package com.peerapplication.controller;

import com.peerapplication.model.Answer;
import com.peerapplication.model.Thread;
import com.peerapplication.util.UIUpdater;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import message.AnswerMessage;
import message.DeleteThreadMessage;
import message.Message;
import message.VoteMessage;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

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
    void logout(ActionEvent event) {

    }

    @FXML
    void openSettings(ActionEvent event) {

    }

    @FXML
    void saveAnswer(MouseEvent event) {
        Answer answer = new Answer();
        answer.setDescription(txtAreaAnswer.getText());
        answer.setPostedUserID(10005000);
        answer.setTimestamp(new Date(System.currentTimeMillis()).getTime());
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
    }

    public void init(Thread thread) {
        txtThreadHeader.setText(thread.getTitle());
        Text txtThreadDetail = new Text();
        Hyperlink userLink = new Hyperlink();
        userLink.setText("Click Me!");
        userLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Link Clicked!");
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
        System.out.println(threadFlow.getLayoutY());
        aPaneThread.getChildren().add(threadFlow);
        aPaneThread.getChildren().add(txtThreadDescription);
        previousText = txtThreadDescription;
    }

    private void addAnswer(Answer answer) {
        Text txtAnswerDetail = new Text();
        Text txtAnswerDesc = new Text();
        txtAnswerDetail.setText("Answered on " + new Date(answer.getTimestamp()) + " by");
        Hyperlink userLink = new Hyperlink();
        userLink.setText("Username");
        userLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Answer User" + new Date(answer.getTimestamp()));
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
