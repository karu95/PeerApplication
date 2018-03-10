package message;

import com.peerapplication.model.Thread;

public class ThreadMessage extends Message {

    private Thread thread;

    public ThreadMessage() {
        super("ThreadMessage");
    }

    public ThreadMessage(Thread thread) {
        this.thread = thread;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}
