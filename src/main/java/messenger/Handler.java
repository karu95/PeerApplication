package messenger;

import message.Message;

public abstract class Handler {

    public abstract void handle(Message message);
}
