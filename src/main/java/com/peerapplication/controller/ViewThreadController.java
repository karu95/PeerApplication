package com.peerapplication.controller;

import com.peerapplication.model.Answer;
import com.peerapplication.model.Thread;
import com.peerapplication.util.UIUpdater;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import message.Message;

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

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Thread thread = new Thread();
        thread.setTitle("How to get width and height of the javafx.scene.text.Text control? [duplicate] sdhakshdkashdkjk hjsakhasjkfhjk haklasjlkjklsjvskljkl \n jlkjdsklfjsdlkj lksjdljfsdklj klsjvlksdjklj kljskglsdkj");
        thread.setUserID(10005000);
        thread.setDescription("As in the subject - how one can get width and height of the " +
                "javafx.scene.text.Text control? This question refers to an example situation, where one " +
                "want to create background for the Text in a form of javafx.scene.shape.Rectangle, whose size depends on the size of Text.");
        thread.setTimestamp((new Date(System.currentTimeMillis())).getTime());
        init(thread);
    }

    public void init(Thread thread) {
        txtThreadHeader.setText(thread.getTitle());
        Text txtThreadDetail = new Text();
        txtThreadDetail.setLayoutX(txtThreadHeader.getLayoutX());
        txtThreadDetail.setLayoutY(txtThreadHeader.getLayoutY() + txtThreadHeader.getBoundsInLocal().getHeight() + 15);
        txtThreadDetail.setText("By " + thread.getUserID() + " on " + new Date(thread.getTimestamp()));
        Text txtThreadDescription = new Text();
        txtThreadDescription.setText(thread.getDescription());
        txtThreadDescription.setWrappingWidth(txtThreadHeader.getWrappingWidth());
        txtThreadDescription.setLayoutX(txtThreadHeader.getLayoutX());
        txtThreadDescription.setLayoutY(txtThreadDetail.getLayoutY() + txtThreadDetail.getBoundsInLocal().getHeight() + 15);
        aPaneThread.getChildren().add(txtThreadDetail);
        aPaneThread.getChildren().add(txtThreadDescription);
        previousText = txtThreadDescription;
    }

    private void addAnswer(Answer answer) {
        Text txtAnswerDetail = new Text();
        Text txtAnswerDesc = new Text();
        txtAnswerDetail.setText("Answered by " + answer.getPostedUserID() + " on " + new Date(answer.getTimestamp()));
        txtAnswerDetail.setLayoutX(txtThreadHeader.getLayoutX());
        txtAnswerDetail.setLayoutY(previousText.getLayoutY() + previousText.getBoundsInLocal().getHeight() + 15);
        txtAnswerDetail.setWrappingWidth(txtThreadHeader.getWrappingWidth());
        aPaneThread.getChildren().add(txtAnswerDetail);
        previousText = txtAnswerDetail;
        txtAnswerDesc.setText(answer.getDescription());
        txtAnswerDesc.setLayoutX(txtThreadHeader.getLayoutX());
        txtAnswerDesc.setLayoutY(previousText.getLayoutY() + previousText.getBoundsInLocal().getHeight() + 5);
        txtAnswerDesc.setWrappingWidth(txtThreadHeader.getWrappingWidth());
        aPaneThread.getChildren().add(txtAnswerDesc);
        previousText = txtAnswerDesc;
    }
}
