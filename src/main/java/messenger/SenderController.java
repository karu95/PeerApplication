package messenger;

import message.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SenderController {

    private ExecutorService senderService;

    public SenderController() {
        senderService = new ThreadPoolExecutor(20, 100,
                1, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

    public void send(Message message, Peer peer) {
        message.setReceiverAddress(peer.getPeerAddress());
        message.setReceiverPort(peer.getPeerPort());
        message.setReceiverID(peer.getUserID());
        message.setTimestamp(new Date(System.currentTimeMillis()).getTime());
        Sender sender = new Sender(message, peer);
        senderService.execute(sender);
    }

    public void sendToAll(Message message, ArrayList<Peer> receivers) {
        if (!receivers.isEmpty()) {
            for (Peer receiver : receivers) {
                Message clonedMessage = null;
                try {
                    clonedMessage = (Message) message.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                send(clonedMessage, receiver);
            }
        }
    }
}
