package com.peerapplication.handler;

import com.peerapplication.message.*;
import com.peerapplication.messenger.PeerHandler;

public class BSHandler {

    public void login(LoginMessage loginMessage){

    }

    public void signup(RegisterMessage registerMessage){

        PeerHandler.getSenderController().send(registerMessage);
    }

    public void logout(LogoutMessage logoutMessage){

    }

    public void changePassword(PasswordChangeMessage pwChangeMessage){

    }

    public void handleRequest(RequestStatusMessage message){

    }

}
