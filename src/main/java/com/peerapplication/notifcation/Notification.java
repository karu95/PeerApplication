package com.peerapplication.notifcation;

import com.peerapplication.model.Thread;

public abstract class Notification {
    Thread relatedThread;
    String description;
    long timestamp;

    public Thread getRelatedThread() {
        return relatedThread;
    }

    public String getDescription() {
        return description;
    }

    abstract void setDescription();

    public long getTimestamp() {
        return timestamp;
    }
}