package messenger;

import message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver implements Runnable {

    private Socket senderSocket;

    public Receiver(Socket senderSocket) {
        this.senderSocket = senderSocket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream os = new ObjectInputStream(senderSocket.getInputStream());
            Message message = (Message) os.readObject();
            PeerHandler.handle(message);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
