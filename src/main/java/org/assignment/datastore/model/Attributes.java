package org.assignment.datastore.model;

public class Attributes {
    private  Integer index;
    private  Long timeToLive;

    public Attributes(Integer index,Long timeToLive){
        this.index=index;
        this.timeToLive=timeToLive;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(Long timeToLive) {
        this.timeToLive = timeToLive;
    }
}
