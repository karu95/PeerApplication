package messenger;

import message.BSMessage;
import message.Message;
import message.RequestStatusMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
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
                senderSocket = new Socket(peer.getPeerAddress(), peer.getPeerPort());
                ObjectOutputStream os = new ObjectOutputStream(senderSocket.getOutputStream());
                if (message instanceof BSMessage) {
                    os.writeObject(message);
                    os.flush();
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
                if (e instanceof ConnectException) {
                    System.out.println("message failed" + message.getTitle());
                    PeerHandler.handleFailedMessage(message, peer);
                }

            } catch (ClassNotFoundException ex) {

            }
        }
    }
}
