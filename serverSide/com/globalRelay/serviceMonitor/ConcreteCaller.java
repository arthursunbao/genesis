package com.globalRelay.serviceMonitor;

/**
 * Created by jasonsun on 10/02/2018.
 */
public class ConcreteCaller extends AbstractCaller {
    private String name;
    private int graceTime;
    private int id;

    public ConcreteCaller(String name, int graceTime, int id) {
        this.name = name;
        this.graceTime = graceTime;
        this.id = id;
    }

    @Override
    public void UpdateMsg(String msg) {
        System.out.println("Message from caller " + this.name + " " + msg);
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
