package messenger;

import message.HeartBeatMessage;

public class HeartBeatHandler implements Runnable {

    private boolean stop;

    public HeartBeatHandler() {
        this.stop = false;
    }

    @Override
    public void run() {
        while (!stop) {
            HeartBeatMessage heartBeatMessage = new HeartBeatMessage();
            PeerHandler.getSenderController().send(heartBeatMessage, PeerHandler.getBS());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                continue;
            }
        }
    }

    public void stop() {
        this.stop = true;
        System.out.println("Stopped");
    }

    public void start() {
        this.stop = false;
    }
}
