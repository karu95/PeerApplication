package com.peerapplication.util;

import messenger.PeerHandler;

import java.io.File;
import java.sql.Date;

public class SystemUser {
    private static int systemUserID;
    private static int accountType;
    private static long lastSeen;
    private static String imageLocation;
    private static boolean tablesCreated;

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
        System.out.println("Get Last " + new Date(lastSeen));
        return lastSeen;
    }

    public static void setLastSeen(long lastSeen) {
        System.out.println("Got Last" + new Date(lastSeen));
        if (!tablesCreated) {
            SystemUser.lastSeen = lastSeen;
        } else {
            SystemUser.lastSeen = 0;
        }
        System.out.println("Last " + new Date(SystemUser.lastSeen));
    }

    public static String getImageLocation() {
        if (imageLocation == null) {
            imageLocation = "/" + System.getProperty("user.dir") + "/images" + String.valueOf(PeerHandler.getUserPort()) + "/";
            File imageDir = new File(imageLocation);
            if (!imageDir.exists()) {
                imageDir.mkdir();
            }
        }
        return imageLocation;
    }

    public static void setTablesCreated(boolean tablesCreated) {
        SystemUser.tablesCreated = tablesCreated;
    }
}
