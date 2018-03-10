package messenger;

import com.peerapplication.util.SystemUser;
import message.HeartBeatMessage;

public class HeartBeatHandler implements Runnable {

    private boolean stop;

    public HeartBeatHandler() {
        this.stop = false;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                Thread.sleep(120000);
            } catch (InterruptedException e) {
                continue;
            }
            HeartBeatMessage heartBeatMessage = new HeartBeatMessage();
            heartBeatMessage.setSenderID(SystemUser.getSystemUserID());
            PeerHandler.getSenderController().send(heartBeatMessage, PeerHandler.getBS());
        }
    }

    public void stop() {
        this.stop = true;
    }

    public void start() {
        this.stop = false;
    }
}
