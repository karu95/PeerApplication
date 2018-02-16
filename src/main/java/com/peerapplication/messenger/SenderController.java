package com.peerapplication.messenger;

import com.peerapplication.message.Message;

public class SenderController {
    public void send(Message message) {
        message.setSenderAddress(PeerHandler.getUserAddress());
        message.setSenderPort(PeerHandler.getUserPort());

    }
}
