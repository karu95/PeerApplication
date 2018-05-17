package com.peerapplication.util;

import com.peerapplication.controller.*;
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

    private static Stage stage;

    public static void logout() throws IOException {                                                                    //open login
        LogoutMessage logoutMessage = new LogoutMessage(SystemUser.getSystemUserID());
        BSHandler.logout(logoutMessage);
        Parent root = FXMLLoader.load(ControllerUtility.class.getResource("/views/login.fxml"));
        stage.setScene(new Scene(root, 1035, 859));
        stage.setTitle("Login");
        stage.show();
    }

    public static void openHome() {                                                                                     //open home stage
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

    public static void openThreads(String tag) throws IOException {                                                     // open threads stage
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerUtility.class.getResource("/views/viewthreads.fxml"));
        Parent root = fxmlLoader.load();
        ViewThreadsController controller = fxmlLoader.getController();
        controller.init(tag);
        stage.setScene(new Scene(root, 1035, 859));
        stage.setTitle("Search Threads");
        stage.show();
    }

    public static void openSettings() {                                                                                 // open settings stage
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
        stage.setTitle("Profile Settings");
    }

    public static void viewThread(Thread thread) {                                                                      // open thread stage of a given thread
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
        stage.setTitle("Thread : " + thread.getTitle());
    }

    public static void viewUser(User user) {                                                                            // view a given user
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
        stage.setTitle("Profile : " + user.getName());
    }

    public static void login() {                                                                                        // open login
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

    public static void openRegister() throws IOException {
        FXMLLoader loader = new FXMLLoader(ControllerUtility.class.getResource("/views/register.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1035, 859);
        stage.setTitle("Register");
        stage.setScene(scene);
    }

    public static void openBSInfoController(boolean networkError) throws IOException {
        FXMLLoader loader = new FXMLLoader((ControllerUtility.class.getResource("/views/bsinfo.fxml")));
        Parent parent = loader.load();
        if (networkError) {
            BSInfoController bsInfoController = loader.getController();
            bsInfoController.init();
        }
        Scene scene = new Scene(parent, 382, 248);
        stage.setTitle("Provide BS Information");
        stage.setScene(scene);
        stage.show();
    }

    public static void setStage(Stage stage) {
        ControllerUtility.stage = stage;
    }
}