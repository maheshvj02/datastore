package org.assignment.datastore.model;

public class DataStoreKey {
    private String key;
    private long timeToLive=1;

    public DataStoreKey(String key) {
        this.key = key;
    }

    public DataStoreKey(String key, long timeToLive) {
        this.key = key;
        this.timeToLive = timeToLive;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }
}
