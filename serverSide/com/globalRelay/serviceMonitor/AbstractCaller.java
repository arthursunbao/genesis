package com.globalRelay.serviceMonitor;

/**
 * Created by jasonsun on 10/02/2018.
 */
public class AbstractCaller {

    private String name;
    private int graceTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGraceTime() {
        return graceTime;
    }

    public void setGraceTime(int graceTime) {
        this.graceTime = graceTime;
    }

    public void UpdateMsg(String msg){};
}
