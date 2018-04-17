package com.peerapplication.util;

import message.Message;

public class UIUpdateHandler {
    private static UIUpdater registerUpdater;
    private static UIUpdater loginUpdater;
    private static UIUpdater threadUpdater;
    private static UIUpdater answerUpdater;
    private static UIUpdater userInfoUpdater;

    public static void setRegisterUpdater(UIUpdater registerUpdater) {
        UIUpdateHandler.registerUpdater = registerUpdater;
    }

    public static void setLoginUpdater(UIUpdater loginUpdater) {
        UIUpdateHandler.loginUpdater = loginUpdater;
    }

    public static void setThreadUpdater(UIUpdater threadUpdater) {
        UIUpdateHandler.threadUpdater = threadUpdater;
    }

    public static void setAnswerUpdater(UIUpdater answerUpdater) {
        UIUpdateHandler.answerUpdater = answerUpdater;
    }

    public static void setUserInfoUpdater(UIUpdater userInfoUpdater) {
        UIUpdateHandler.userInfoUpdater = userInfoUpdater;
    }

    public static void informRegisterUpdater(Message message) {
        if (registerUpdater != null) {
            registerUpdater.updateUI(message);
        }
    }

    public static void informLoginUpdater(Message message) {
        if (loginUpdater != null) {
            loginUpdater.updateUI(message);
        }
    }

    public static void informAnswerUpdater(Message message) {
        if (answerUpdater != null) {
            answerUpdater.updateUI(message);
        }
    }

    public static void informThreadUpdater(Message message) {
        if (threadUpdater != null) {
            threadUpdater.updateUI(message);
        }
    }

    public static void informUserUpdater(Message message) {
        if (userInfoUpdater != null) {
            userInfoUpdater.updateUI(message);
        }

    }


}
