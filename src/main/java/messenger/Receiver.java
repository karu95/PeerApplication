package messenger;

import java.net.Socket;

public class Receiver implements Runnable {

    private Socket senderSocket;

    public Receiver(Socket senderSocket) {
        this.senderSocket = senderSocket;
    }

    @Override
    public void run() {

    }
}
