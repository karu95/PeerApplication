package message;

import java.util.Date;

public class HeartBeatMessage extends Message {

    public HeartBeatMessage() {
        super("HeartBeatMessage");
        super.setTimestamp(new Date(System.currentTimeMillis()).getTime());
    }

}
