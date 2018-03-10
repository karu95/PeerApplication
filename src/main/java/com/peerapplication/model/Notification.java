package com.peerapplication.model;

public class Notification {
    private int notificationID;
    private int relatedUserID;
    private boolean notificationStatus;
    private String type;
    private String relatedID;

    public Notification() {
    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public int getRelatedUserID() {
        return relatedUserID;
    }

    public void setRelatedUserID(int relatedUserID) {
        this.relatedUserID = relatedUserID;
    }

    public boolean isNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(boolean notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRelatedID() {
        return relatedID;
    }

    public void setRelatedID(String relatedID) {
        this.relatedID = relatedID;
    }
}
