package message;

import java.util.Date;

public class HeartBeatMessage extends Message {

    public HeartBeatMessage() {
        super("HeartBeat");
        super.setTimestamp(new Date(System.currentTimeMillis()).getTime());
    }

}
