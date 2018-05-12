package messenger;

import message.HeartBeatMessage;
import message.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HeartBeatHandler extends Handler {

    private static HeartBeatHandler heartBeatHandler;

    private Object stopLock;
    private boolean stop;

    private HeartBeatHandler() {
        this.stop = false;
        stopLock = new Object();
    }

    public static HeartBeatHandler getHeartBeatHandler() {
        if (heartBeatHandler == null) {
            synchronized (HeartBeatHandler.class) {
                heartBeatHandler = new HeartBeatHandler();
            }
        }
        return heartBeatHandler;
    }


    public void startHeartBeat() {
        ExecutorService heartbeatService = Executors.newSingleThreadExecutor();
        synchronized (stopLock) {
            this.stop = false;
        }
        heartbeatService.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (stopLock) {
                        if (!stop) {
                            HeartBeatMessage heartBeatMessage = new HeartBeatMessage();
                            PeerHandler.getSenderController().send(heartBeatMessage, PeerHandler.getBS());
                        } else {
                            break;
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            }
        });
    }

    public void stop() {
        synchronized (stopLock) {
            this.stop = true;
            System.out.println("Heartbeat Stopped");
        }
    }

    @Override
    public void handle(Message message) {

    }

    @Override
    public void handleFailedMessage(Message message, Peer peer) {

    }
}
