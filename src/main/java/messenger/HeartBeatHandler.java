package messenger;

import com.peerapplication.util.SystemUser;
import message.HeartBeatMessage;

public class HeartBeatHandler implements Runnable{

    private boolean loggedIn;

    public HeartBeatHandler(){
        loggedIn = true;
    }

    @Override
    public void run() {
        while (loggedIn) {
            try {
                Thread.sleep(120000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            HeartBeatMessage heartBeatMessage = new HeartBeatMessage();
            heartBeatMessage.setSenderID(SystemUser.getSystemUserID());
            PeerHandler.getSenderController().send(heartBeatMessage, PeerHandler.getBS());
        }
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
