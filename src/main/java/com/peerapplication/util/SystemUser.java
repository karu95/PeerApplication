package com.peerapplication.util;

import java.io.File;

public class SystemUser {
    private static int systemUserID;
    private static int accountType;
    private static long lastSeen;
    private static String imageLocation;

    public static int getAccountType() {
        return accountType;
    }

    public static void setAccountType(int accountType) {
        SystemUser.accountType = accountType;
    }

    public static int getSystemUserID() {
        return systemUserID;
    }

    public static void setSystemUserID(int systemUserID) {
        SystemUser.systemUserID = systemUserID;
    }

    public static long getLastSeen() {
        return lastSeen;
    }

    public static void setLastSeen(long lastSeen) {
        SystemUser.lastSeen = lastSeen;
    }

    public static String getImageLocation() {
        if (imageLocation == null) {
            imageLocation = "/" + System.getProperty("user.dir") + "/images" + String.valueOf(systemUserID) + "/";
            File imageDir = new File(imageLocation);
            if (!imageDir.exists()) {
                imageDir.mkdir();
            }
        }
        return imageLocation;
    }
}
