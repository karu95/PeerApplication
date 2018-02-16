package com.peerapplication.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class HomeController {

    @FXML
    private TabPane homeTabs;

    @FXML
    private Pagination pagination;

    @FXML
    private TableView<?> threadTable;

    @FXML
    private TableColumn<?, ?> colAnswers;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private Pagination pagination1;

    @FXML
    private TableView<?> threadTable1;

    @FXML
    private TableColumn<?, ?> colAnswers1;

    @FXML
    private TableColumn<?, ?> colTitle1;

    @FXML
    private Pagination pagination11;

    @FXML
    private TableView<?> threadTable11;

    @FXML
    private TableColumn<?, ?> colAnswers11;

    @FXML
    private TableColumn<?, ?> colTitle11;

    @FXML
    private ImageView userImage;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPostedThreads;

    @FXML
    private Label lblAnsweredThreads;

    @FXML
    private Label lblHeading;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnThreads;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnTags;

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
    void btnTagsClicked(MouseEvent event) {

    }

    @FXML
    void btnThreadsClicked(MouseEvent event) {

    }

    @FXML
    void btnUsersClicked(MouseEvent event) {

    }

    @FXML
    void headingClicked(MouseEvent event) {

    }

}
