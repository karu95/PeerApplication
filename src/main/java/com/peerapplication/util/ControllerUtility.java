package com.peerapplication.util;

import com.peerapplication.controller.HomeController;
import com.peerapplication.controller.SettingsController;
import com.peerapplication.controller.ViewThreadController;
import com.peerapplication.controller.ViewUserController;
import com.peerapplication.handler.BSHandler;
import com.peerapplication.model.Thread;
import com.peerapplication.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import message.LogoutMessage;

import java.io.IOException;

public class ControllerUtility {
    public static void logout(Stage stage) throws IOException {
        LogoutMessage logoutMessage = new LogoutMessage(SystemUser.getSystemUserID());
        BSHandler.logout(logoutMessage);
        Parent root = FXMLLoader.load(ControllerUtility.class.getResource("/views/login.fxml"));
        stage.setScene(new Scene(root, 1035, 859));
        stage.setTitle("Login");
        stage.show();
    }

    public static void openHome(Stage stage) {
        FXMLLoader loader = new FXMLLoader(ControllerUtility.class.getResource("/views/homepage.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = new User(SystemUser.getSystemUserID());
        HomeController controller = loader.getController();
        controller.init(user);
        Scene scene = new Scene(parent, 1035, 859);
        stage.setScene(scene);
        stage.setTitle("Welcome : " + user.getName());
        stage.show();
    }

    public static void openThreads(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(ControllerUtility.class.getResource("/views/viewthreads.fxml"));
        stage.setScene(new Scene(root, 1035, 859));
        stage.setTitle("Threads");
        stage.show();
    }

    public static void openSettings(Stage stage) {
        FXMLLoader loader = new FXMLLoader(ControllerUtility.class.getResource("/views/settings.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = new User();
        user.getUserWithImage(SystemUser.getSystemUserID());
        SettingsController controller = loader.getController();
        controller.init(user);
        Scene scene = new Scene(parent, 1035, 859);
        stage.setScene(scene);
        stage.setTitle("Settings");
    }

    public static void viewThread(Stage stage, Thread thread) {
        FXMLLoader loader = new FXMLLoader(ControllerUtility.class.getResource("/views/viewthread.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ViewThreadController viewThreadController = loader.getController();
        viewThreadController.init(thread);
        Scene scene = new Scene(parent, 1035, 859);
        stage.setScene(scene);
        stage.setTitle("View Thread");
    }

    public static void viewUser(Stage stage, User user) {
        FXMLLoader loader = new FXMLLoader(ControllerUtility.class.getResource("/views/viewuser.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ViewUserController controller = loader.getController();
        controller.init(user);
        Scene scene = new Scene(parent, 1035, 859);
        stage.setScene(scene);
        stage.setTitle("Profile: " + user.getName());
    }

    public static void login(Stage stage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(ControllerUtility.class.getResource("/views/login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 1035, 859);
        stage.setTitle("Login");
        stage.setScene(scene);
    }

    public static void openRegister(Stage stage) throws IOException {
        FXMLLoader loader;
        loader = new FXMLLoader(ControllerUtility.class.getResource("/views/register.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1035, 859);
        stage.setTitle("Register");
        stage.setScene(scene);
    }
}
