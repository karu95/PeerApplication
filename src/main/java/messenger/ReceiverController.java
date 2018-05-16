package messenger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ReceiverController implements Runnable {
    private int port;
    private ExecutorService receiverService;

    public ReceiverController(int port) {
        this.port = port;
        receiverService = new ThreadPoolExecutor(50, 100,
                1, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

    @Override
    public void run() {
        ServerSocket receiverSocket;
        Socket senderSocket;
        try {
            receiverSocket = new ServerSocket(port);
            while (true) {
                senderSocket = receiverSocket.accept();
                receiverService.execute(new Receiver(senderSocket));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
