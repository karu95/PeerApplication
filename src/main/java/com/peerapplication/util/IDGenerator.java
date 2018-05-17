package com.peerapplication.util;

public class IDGenerator {
    public static String generateThreadID(long timestamp) {                                                             //generate threadID
        return String.valueOf(SystemUser.getSystemUserID()) + "0" + String.valueOf(timestamp);

    }

    public static String generateAnswerID(long timestamp) {
        return String.valueOf(SystemUser.getSystemUserID()) + "1" + String.valueOf(timestamp);                          // generate answer ID
    }
}