package com.peerapplication.notifcation;

import com.peerapplication.model.Thread;

public abstract class Notification {
    protected Thread relatedThread;
    protected String description;
    protected long timestamp;

    public Thread getRelatedThread() {
        return relatedThread;
    }

    public String getDescription() {
        return description;
    }

    protected abstract void setDescription();

    public long getTimestamp() {
        return timestamp;
    }
}
