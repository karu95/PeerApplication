package com.peerapplication.controller;

import com.peerapplication.model.Tag;
import com.peerapplication.model.Thread;
import com.peerapplication.util.ControllerUtility;
import com.peerapplication.util.UIUpdateHandler;
import com.peerapplication.util.UIUpdater;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import message.Message;
import message.ThreadMessage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ViewThreadsController implements UIUpdater, Initializable {

    ObservableList<Thread> observableList;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private TableView<Thread> threadTable;
    @FXML
    private TableColumn<Thread, String> colTitle;
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
        ControllerUtility.openHome();
    }

    @FXML
    void btnThreadsClicked(MouseEvent event) {

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
        Thread thread = threadTable.getSelectionModel().getSelectedItem();
        ControllerUtility.viewThread(thread);
    }

    @FXML
    void search(MouseEvent event) {
        String relatedTagsString = txtSearch.getText().trim();
        searchThreads(relatedTagsString);
    }

    @Override
    public void updateUI(Message message) {
        if (message instanceof ThreadMessage) {
            ThreadMessage threadMessage = (ThreadMessage) message;
            observableList.add(0, threadMessage.getThread());
        }
    }

    public void init(String tag) {
        txtSearch.setText(tag);
        searchThreads(tag);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UIUpdateHandler.refreshUpdater();
        UIUpdateHandler.setThreadUpdater(this);
        btnThreads.setDisable(true);
        observableList = FXCollections.observableArrayList(Thread.getLatestThreads(0));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        threadTable.setItems(observableList);
    }

    private void searchThreads(String relatedTagsString) {
        if (relatedTagsString.length() > 0) {
            String[] relatedTags = relatedTagsString.split(",");
            for (int i = 0; i < relatedTags.length; i++) {
                relatedTags[i] = relatedTags[i].trim();
            }
            System.out.println(Arrays.toString(relatedTags));
            ArrayList<String> relatedThreadIDs = Tag.getRelatedThreads(relatedTags);
            System.out.println("Related threads count " + relatedThreadIDs.size());
            HashMap<String, Integer> relatedThreadsFreq = new HashMap<>();
            for (String threadID : relatedThreadIDs) {
                if (relatedThreadsFreq.get(threadID) == null) {
                    relatedThreadsFreq.put(threadID, 1);
                } else {
                    relatedThreadsFreq.replace(threadID, relatedThreadsFreq.get(threadID) + 1);
                }
            }
            LinkedList<Map.Entry<String, Integer>> unsortedList = new LinkedList<>(relatedThreadsFreq.entrySet());
            Collections.sort(unsortedList, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });
            observableList.clear();
            System.out.println("Cleared");
            for (Map.Entry thread : unsortedList) {
                observableList.add(new Thread((String) thread.getKey()));
            }
            System.out.println("Observable list size " + observableList.size());
        } else {
            observableList = FXCollections.observableList(Thread.getLatestThreads(0));
        }
    }
}
