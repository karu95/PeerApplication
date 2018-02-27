package messenger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiverController implements Runnable{
    private int port;

    public ReceiverController(int port){
        this.port=port;
    }

    @Override
    public void run() {
        ServerSocket receiverSocket;
        Socket senderSocket;
        try {
            receiverSocket = new ServerSocket(port);
            while (true) {
                senderSocket = receiverSocket.accept();
                Thread t = new Thread(new Receiver(senderSocket));
                t.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
