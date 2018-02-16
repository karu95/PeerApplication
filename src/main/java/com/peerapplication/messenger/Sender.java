package com.peerapplication.messenger;

import com.peerapplication.message.Message;

import java.net.Socket;

public class Sender implements Runnable{
    private Message message;
    private Socket senderSocket;

    public Sender(Message message){
        this.message = message;
        senderSocket = new Socket();
    }



    @Override
    public void run() {


    }
}
