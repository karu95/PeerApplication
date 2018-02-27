package com.peerapplication.util;

public class SystemUser {
    private static int systemUserID;
    private static int accountType;

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
}
