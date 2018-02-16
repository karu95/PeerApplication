package com.peerapplication.util;

import com.peerapplication.messenger.PeerHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{

    private static int systemUserID;
    private static int accountType;
    private static UIUpdater registerListener;

    private static UIUpdater loginListener;

    public static int getSystemUserID() {
        return systemUserID;
    }

    public static void setSystemUserID(int systemUserID) {
        Main.systemUserID = systemUserID;
    }

    public static void setRegisterListener(UIUpdater registerListener) {
        Main.registerListener = registerListener;
    }

    public static UIUpdater getLoginListener() {
        return loginListener;
    }

    public static void setLoginListener(UIUpdater loginListener) {
        Main.loginListener = loginListener;
    }

    public static UIUpdater getRegisterListener() {
        return registerListener;
    }

    public static int getAccountType() {
        return accountType;
    }

    public static void setAccountType(int accountType) {
        Main.accountType = accountType;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
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
