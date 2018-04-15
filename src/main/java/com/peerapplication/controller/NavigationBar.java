package com.peerapplication.controller;

import com.peerapplication.handler.BSHandler;
import com.peerapplication.util.SystemUser;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import message.LogoutMessage;

import java.io.IOException;

public class NavigationBar {
    public static void logout(Stage stage) throws IOException {
        LogoutMessage logoutMessage = new LogoutMessage(SystemUser.getSystemUserID());
        BSHandler.logout(logoutMessage);
        Parent root = FXMLLoader.load(NavigationBar.class.getResource("/views/login.fxml"));
        stage.setScene(new Scene(root, 1035, 859));
        stage.show();
    }

    public static void openHome(Stage stage) {

    }

    public static void openThreads(Stage stage) {

    }

    public static void openSettings(Stage stage) {

    }


}
