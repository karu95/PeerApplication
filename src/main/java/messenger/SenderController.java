package messenger;

import com.peerapplication.util.SystemUser;
import message.Message;

import java.util.ArrayList;
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
        message.setSenderID(SystemUser.getSystemUserID());
        message.setSenderAddress(PeerHandler.getUserAddress());
        message.setSenderPort(PeerHandler.getUserPort());
        message.setReceiverAddress(peer.getPeerAddress());
        message.setReceiverPort(peer.getPeerPort());
        Sender sender = new Sender(message, peer);
        senderService.execute(sender);
    }

    public void sendToAll(Message message, ArrayList<Peer> receivers) {
        if (!receivers.isEmpty()) {
            for (Peer receiver : receivers) {
                send(message, receiver);
            }
        }
    }

}
