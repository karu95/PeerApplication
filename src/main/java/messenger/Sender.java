package messenger;

import com.peerapplication.handler.BSHandler;
import message.BSMessage;
import message.ForumUpdateMessage;
import message.Message;
import message.RequestStatusMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sender implements Runnable {
    private Message message;
    private Socket senderSocket;
    private Peer peer;

    public Sender(Message message, Peer peer) {
        this.message = message;
        this.peer = peer;
    }


    @Override
    public void run() {
        synchronized (peer) {
            try {
                senderSocket = new Socket(message.getReceiverAddress(), message.getReceiverPort());
                ObjectOutputStream os = new ObjectOutputStream(senderSocket.getOutputStream());
                if (message instanceof BSMessage) {
                    os.writeObject(message);
                    ObjectInputStream is = new ObjectInputStream(senderSocket.getInputStream());
                    RequestStatusMessage response = (RequestStatusMessage) is.readObject();
                    is.close();
                    ExecutorService handler = Executors.newSingleThreadExecutor();
                    handler.submit(new Runnable() {
                        @Override
                        public void run() {
                                PeerHandler.handle(response);
                            }
                    });
                } else {
                    os.writeObject(message);
                    os.flush();
                }
                os.close();
                senderSocket.close();
            } catch (IOException e) {
                if (peer.getUserID() == 1) {
                    System.out.println("No Network Connection!");
                } else {
                    PeerHandler.removeKnownPeer(peer.getUserID());
                }
            } catch (ClassNotFoundException ex) {

            }
        }
    }
}
