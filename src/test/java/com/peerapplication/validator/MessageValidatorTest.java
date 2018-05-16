package com.peerapplication.validator;

import com.peerapplication.util.SystemUser;
import message.AnswerMessage;
import message.LoginMessage;
import message.Message;
import message.ThreadMessage;
import messenger.Peer;
import messenger.PeerHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MessageValidatorTest {


    @Test
    void getMessageValidator() {
        assertEquals(MessageValidator.getMessageValidator(), MessageValidator.getMessageValidator());
    }

    @Test
    void validateMessageBSMessageIDFail() {
        Message message = new LoginMessage();
        assertFalse(MessageValidator.getMessageValidator().validateMessage(message));
    }

    @Test
    void validateMessageBSMessageAddressFail() {
        Message message = new LoginMessage();
        message.setSenderID(1);
        PeerHandler.getBS().setPeerAddress("1.1.1.1");
        message.setSenderAddress("0.0.0.0");
        assertFalse(MessageValidator.getMessageValidator().validateMessage(message));
    }

    @Test
    void validateMessageNotBSMessage() {
        Message message = new ThreadMessage();
        message.setReceiverID(100);
        SystemUser.setSystemUserID(100);
        assertFalse(MessageValidator.getMessageValidator().validateMessage(message));
    }

    @Test
    void validateMessageMessageWithKnownPeer() {
        Message message = new ThreadMessage();
        message.setReceiverID(100);
        SystemUser.setSystemUserID(100);
        PeerHandler.setKnownPeers(new ArrayList<Peer>() {{
            add(new Peer(150, "0.0.0.0", 25025));
        }});
        message.setSenderID(150);
        assertTrue(MessageValidator.getMessageValidator().validateMessage(message));
    }

    @Test
    void validateMessageMessageWithoutKnownPeer() {
        Message message = new AnswerMessage();
        message.setReceiverID(100);
        SystemUser.setLastSeen(100);
        message.setSenderID(180);
        assertFalse(MessageValidator.getMessageValidator().validateMessage(message));
    }
}