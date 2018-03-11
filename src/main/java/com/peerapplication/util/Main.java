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

import java.io.File;


public class Main extends Application {

    private static UIUpdater registerUpdater;
    private static UIUpdater loginUpdater;
    private static UIUpdater threadUpdater;
    private static UIUpdater answerUpdater;

    public static UIUpdater getRegisterUpdater() {
        return registerUpdater;
    }

    public static void setRegisterUpdater(UIUpdater registerUpdater) {
        Main.registerUpdater = registerUpdater;
    }

    public static UIUpdater getLoginUpdater() {
        return loginUpdater;
    }

    public static void setLoginUpdater(UIUpdater loginUpdater) {
        Main.loginUpdater = loginUpdater;
    }

    public static UIUpdater getThreadUpdater() {
        return threadUpdater;
    }

    public static void setThreadUpdater(UIUpdater threadUpdater) {
        Main.threadUpdater = threadUpdater;
    }

    public static UIUpdater getAnswerUpdater() {
        return answerUpdater;
    }

    public static void setAnswerUpdater(UIUpdater answerUpdater) {
        Main.answerUpdater = answerUpdater;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        Scene scene = new Scene(parent, 1035, 859);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public static void main(String[] args) {

        int port = 25030;
        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }
        PeerHandler.setup(port);

        TableRepository tableRepo = new TableRepository();
        tableRepo.createTables();

        File imageDir = new File("/" + System.getProperty("user.dir") + "/images");
        if (!imageDir.exists()) {
            imageDir.mkdir();
        }

        registerHandlers();
        launch(args);

    }

    private static void registerHandlers() {
        BSHandler bsHandler = new BSHandler();
        ThreadHandler threadHandler = new ThreadHandler();
        PeerHandler.registerHandler("LoginStatus", bsHandler);
        PeerHandler.registerHandler("RegisterStatus", bsHandler);
        PeerHandler.registerHandler("ThreadMessage", threadHandler);
        PeerHandler.registerHandler("DeleteThreadMessage", threadHandler);
        PeerHandler.registerHandler("AnswerMessage", new AnswerHandler());
        PeerHandler.registerHandler("UserInfoMessage", new UserHandler());
        PeerHandler.registerHandler("VoteMessage", new VoteHandler());
        PeerHandler.registerHandler("ForumUpdateMessage", new ForumUpdateHandler());
        PeerHandler.registerHandler("PWChangeStatus", bsHandler);
    }
}
