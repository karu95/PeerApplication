package com.peerapplication.util;

import com.peerapplication.messenger.PeerHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{

    private static int systemUserID;

    public static int getSystemUserID() {
        return systemUserID;
    }

    public static void setSystemUserID(int systemUserID) {
        Main.systemUserID = systemUserID;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/homepage.fxml"));
        Scene scene = new Scene(parent, 1035, 859);
        setUserAgentStylesheet(STYLESHEET_MODENA);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public static void main(String[] args){

        int port = 25030;
        if (args.length>1){
            port = Integer.parseInt(args[1]);
        }
        PeerHandler.setup(port);

        launch(args);

    }
}
