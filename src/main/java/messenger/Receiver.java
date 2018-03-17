package messenger;

import message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Receiver implements Runnable {

    private Socket senderSocket;

    Receiver(Socket senderSocket) {
        this.senderSocket = senderSocket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream os = new ObjectInputStream(senderSocket.getInputStream());
            Message message = (Message) os.readObject();
            ExecutorService receiverWorker = Executors.newSingleThreadExecutor();
            receiverWorker.execute(new Runnable() {
                @Override
                public void run() {
                    PeerHandler.handle(message);
                }
            });
            os.close();
        } catch (IOException e) {
            return;
        } catch (ClassNotFoundException e) {
            return;
        }
    }
}
