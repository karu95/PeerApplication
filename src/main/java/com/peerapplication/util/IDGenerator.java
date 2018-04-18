package com.peerapplication.util;

public class IDGenerator {
    public static String generateThreadID(long timestamp) {
        return String.valueOf(SystemUser.getSystemUserID()) + "0" + String.valueOf(timestamp);

    }

    public static String generateAnswerID(long timestamp) {
        return String.valueOf(SystemUser.getSystemUserID()) + "1" + String.valueOf(timestamp);
    }

    public static boolean validateThreadID(String threadID) {
        boolean valid = false;

        return valid;
    }

    public static boolean validateAnswerID(String answerID) {
        boolean valid = false;

        return valid;
    }
}
