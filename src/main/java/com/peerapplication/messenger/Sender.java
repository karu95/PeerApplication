package com.peerapplication.messenger;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import message.ForumUpdateMessage;
import message.Message;
import message.RegisterMessage;
import message.RequestStatusMessage;

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
            System.out.println("Running run method");
            if (message instanceof RegisterMessage){
                System.out.println("yes");
            }
            ObjectOutputStream os = new ObjectOutputStream(senderSocket.getOutputStream());
            System.out.println("Hi");
            if (message instanceof RegisterMessage){
                System.out.println("Inside BS message");
                os.writeObject(message);
                System.out.println("Message sent");
                ObjectInputStream is = new ObjectInputStream(senderSocket.getInputStream());
                RequestStatusMessage response = (RequestStatusMessage) is.readObject();
                is.close();
                System.out.println("Message Received");
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
