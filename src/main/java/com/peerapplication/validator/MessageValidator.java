package com.peerapplication.validator;

import com.peerapplication.util.SystemUser;
import message.BSMessage;
import message.JoinMessage;
import message.Message;
import messenger.PeerHandler;

public class MessageValidator {
    private static MessageValidator messageValidator;

    private MessageValidator() {

    }

    public static MessageValidator getMessageValidator() {
        if (messageValidator == null) {
            synchronized (MessageValidator.class) {
                messageValidator = new MessageValidator();
            }
        }
        return messageValidator;
    }

    public boolean validateMessage(Message message) {
        boolean validity = true;
        if (message instanceof BSMessage) {
            if (message.getSenderID() != 1) {
                System.out.println("Invalid bs sender");
                validity = false;
            } else if (!message.getSenderAddress().equals(PeerHandler.getBS().getPeerAddress())) {
                System.out.println("Invalid bs address");
                validity = false;
            }
        } else {
            if (message.getReceiverID() != SystemUser.getSystemUserID()) {
                System.out.println("Invalid receiver");
                validity = false;
            } else {
                if (!(message instanceof JoinMessage)) {
                    if (!PeerHandler.getKnownPeers().containsKey(message.getSenderID())) {
                        System.out.println("Unknown sender");
                        validity = false;
                    }
                }
            }
        }
        return validity;
    }
}
