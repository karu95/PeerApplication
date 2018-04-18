package com.peerapplication.util;

import com.peerapplication.handler.*;
import com.peerapplication.repository.TableRepository;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import messenger.PeerHandler;


public class Main extends Application {

    public static void main(String[] args) {
        int port = 25034;
        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }
        PeerHandler.setup(port);
        TableRepository tableRepo = new TableRepository();
        tableRepo.createTables();

        registerHandlers();
        launch(args);
    }

    private static void registerHandlers() {
        PeerHandler.registerHandler("LoginStatus", BSHandler.getBSHandler());
        PeerHandler.registerHandler("RegisterStatus", BSHandler.getBSHandler());
        PeerHandler.registerHandler("ThreadMessage", ThreadHandler.getThreadHandler());
        PeerHandler.registerHandler("DeleteThreadMessage", ThreadHandler.getThreadHandler());
        PeerHandler.registerHandler("AnswerMessage", AnswerHandler.getAnswerHandler());
        PeerHandler.registerHandler("UserInfoMessage", UserHandler.getUserHandler());
        PeerHandler.registerHandler("VoteMessage", VoteHandler.getVoteHandler());
        PeerHandler.registerHandler("ForumUpdateMessage", ForumUpdateHandler.getForumUpdateHandler());
        PeerHandler.registerHandler("PWChangeStatus", BSHandler.getBSHandler());
        PeerHandler.registerHandler("Login", BSHandler.getBSHandler());
        PeerHandler.registerHandler("Register", BSHandler.getBSHandler());
        PeerHandler.registerHandler("HeartBeatMessage", BSHandler.getBSHandler());
        PeerHandler.registerHandler("Logout", BSHandler.getBSHandler());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/bsinfo.fxml"));
        Scene scene = new Scene(parent, 1035, 859);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("BS Information");
        primaryStage.show();
    }
}
