package com.peerapplication.messenger;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import message.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Sender implements Runnable{
    private Message message;
    private Socket senderSocket;

    public Sender(Message message){
        this.message = message;
        try {
            senderSocket = new Socket(message.getReceiverAddress(), message.getReceiverPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void run() {
        try {
            ObjectOutputStream os = new ObjectOutputStream(senderSocket.getOutputStream());
            if (message instanceof BSMessage){
                os.writeObject(message);
                ObjectInputStream is = new ObjectInputStream(senderSocket.getInputStream());
                RequestStatusMessage response = (RequestStatusMessage) is.readObject();
                is.close();
                PeerHandler.getBsHandler().handleRequest(response);
            }else if (message instanceof ForumUpdateMessage){

            }
            os.close();
            senderSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
