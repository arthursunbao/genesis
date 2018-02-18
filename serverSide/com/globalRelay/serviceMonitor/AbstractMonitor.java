package com.globalRelay.serviceMonitor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jasonsun on 10/02/2018.
 */
public interface AbstractMonitor {

    public void addObservers(AbstractCaller observers, int serviceId);
    public void deleteObservers(AbstractCaller observers, int serviceId);
    public void notifyAllObservers(String msg, int serviceId);
    public void serviceOutrage(AbstractCaller observer, String startTime, String endTime, int serviceId);
    public void notifyObserver(AbstractCaller observer, HashMap<AbstractCaller, ArrayList<String>> outrageTime, String msg);
    public void addGraceTime(AbstractCaller observer, int graceTime, int serviceId);
}


