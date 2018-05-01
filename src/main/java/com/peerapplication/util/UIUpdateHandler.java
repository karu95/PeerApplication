package com.peerapplication.util;

import message.Message;

public class UIUpdateHandler {
    private static UIUpdater registerUpdater;
    private static UIUpdater loginUpdater;
    private static UIUpdater threadUpdater;
    private static UIUpdater answerUpdater;
    private static UIUpdater userInfoUpdater;
    private static UIUpdater voteUpdater;

    public static void setRegisterUpdater(UIUpdater registerUpdater) {
        UIUpdateHandler.registerUpdater = registerUpdater;
    }

    public static void setLoginUpdater(UIUpdater loginUpdater) {
        synchronized (loginUpdater) {
            UIUpdateHandler.loginUpdater = loginUpdater;
        }
    }

    public static void setThreadUpdater(UIUpdater threadUpdater) {
        synchronized (threadUpdater) {
            UIUpdateHandler.threadUpdater = threadUpdater;
        }
    }

    public static void setAnswerUpdater(UIUpdater answerUpdater) {
        synchronized (answerUpdater) {
            UIUpdateHandler.answerUpdater = answerUpdater;
        }
    }

    public static void setUserInfoUpdater(UIUpdater userInfoUpdater) {
        synchronized (userInfoUpdater) {
            UIUpdateHandler.userInfoUpdater = userInfoUpdater;
        }
    }

    public static void informRegisterUpdater(Message message) {
        synchronized (registerUpdater) {
            if (registerUpdater != null) {
                registerUpdater.updateUI(message);
            }
        }
    }

    public static void informLoginUpdater(Message message) {
        synchronized (loginUpdater) {
            if (loginUpdater != null) {
                loginUpdater.updateUI(message);
            }
        }
    }

    public static void informAnswerUpdater(Message message) {
        synchronized (answerUpdater) {
            if (answerUpdater != null) {
                answerUpdater.updateUI(message);
            }
        }
    }

    public static void informThreadUpdater(Message message) {
        synchronized (threadUpdater) {
            if (threadUpdater != null) {
                threadUpdater.updateUI(message);
            }
        }
    }

    public static void informUserUpdater(Message message) {
        synchronized (userInfoUpdater) {
            if (userInfoUpdater != null) {
                userInfoUpdater.updateUI(message);
            }
        }
    }

    public static void setVoteUpdater(UIUpdater voteUpdater) {
        synchronized (voteUpdater) {
            UIUpdateHandler.voteUpdater = voteUpdater;
        }
    }

    public static void informVoteUpdater(Message message) {
        synchronized (voteUpdater) {
            if (voteUpdater != null) {
                voteUpdater.updateUI(message);
            }
        }
    }

    public static void refreshUpdater() {
        synchronized (UIUpdateHandler.class) {
            userInfoUpdater = null;
            registerUpdater = null;
            voteUpdater = null;
            answerUpdater = null;
            threadUpdater = null;
            loginUpdater = null;
        }
    }
}
