package com.globalRelay.serviceMonitor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jasonsun on 10/02/2018.
 */
public class ConcreteMonitor implements AbstractMonitor {

    private ArrayList<AbstractCaller> callerList = new ArrayList<>();
    private ConcurrentHashMap<AbstractCaller, ArrayList<String>> outrageTime = new ConcurrentHashMap<>();
    private ConcurrentHashMap<AbstractCaller, Integer> graceTimeMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, ArrayList<AbstractCaller>> idObserver = new ConcurrentHashMap<>();
    private int serviceId;
    private String service_type;
    private String lastUpdateTime;
    private String serviceName;

    @Override
    public void addObservers(AbstractCaller observers, int serviceId) {
        if(idObserver.containsKey(serviceId)){
            idObserver.get(serviceId).add(observers);
        }
        else{
            idObserver.put(serviceId, new ArrayList<AbstractCaller>());
            idObserver.get(serviceId).add(observers);
        }
        //callerList.add(observers);
        System.out.println("One observer is added! for Service ID " + serviceId);
    }

    @Override
    public void deleteObservers(AbstractCaller observers, int serviceId) {
        if(!idObserver.containsKey(serviceId) && serviceId !=1) {
            throw new IllegalArgumentException("Erroor Service ID");
        }
        else{
            idObserver.get(serviceId).remove(observers);
            System.out.println("One observer is added! for Service ID " + serviceId);
        }
        //callerList.remove(observers);

    }

    @Override
    public void notifyAllObservers(String msg, int serviceId) {
        for (AbstractCaller list : idObserver.get(serviceId)) {
            list.UpdateMsg(msg);
        }
    }

    @Override
    public void notifyObserver(AbstractCaller observer, HashMap<AbstractCaller, ArrayList<String>> outrageTime, String msg){
        //If the certain observer(caller) has outrage time, then we check the current time with the outrage Time and then avoid if needed
        if(outrageTime.containsKey(observer)){
            String currentTime = String.valueOf(new Date());
            String startTime =  outrageTime.get(observer).get(0);
            String endTime = outrageTime.get(observer).get(1);
            if(Integer.valueOf(startTime) < Integer.valueOf(currentTime) && Integer.valueOf(endTime) > Integer.valueOf(currentTime)){
                //Do nothing since it is in outrage time
            }
            else{
                observer.UpdateMsg(msg);
            }

        }
    }

    @Override
    public void serviceOutrage(AbstractCaller observer, String startTime, String endTime, int serviceId){
        //Add the record of outrage time for specific observer(caller)
        if(!idObserver.containsKey(serviceId)){
            throw new IllegalArgumentException("Error with ServiceId");
        }
        ArrayList<String> list = new ArrayList<String>();
        list.add(startTime);
        list.add(endTime);
        outrageTime.put(observer, list);
        System.out.println("Outrage time starting from " + startTime + " to " + endTime + " for " + observer.getName() + " is saved");
    }

    @Override
    public void addGraceTime(AbstractCaller observer, int graceTime, int serviceId){
        if(!idObserver.containsKey(serviceId)){
            throw new IllegalArgumentException("Error with ServiceId");
        }
        this.graceTimeMap.putIfAbsent(observer, graceTime);
        System.out.println("Caller " + observer.getName() + " has set grace time for " + graceTime + " ms");
    }

    public List<AbstractCaller> getCallerList() {
        return callerList;
    }


    public ConcurrentHashMap<AbstractCaller, ArrayList<String>> getOutrageTime() {
        return outrageTime;
    }

    public void setOutrageTime(ConcurrentHashMap<AbstractCaller, ArrayList<String>> outrageTime) {
        this.outrageTime = outrageTime;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public ConcurrentHashMap<AbstractCaller, Integer> getGraceTimeMap() {
        return graceTimeMap;
    }

    public void setGraceTimeMap(ConcurrentHashMap<AbstractCaller, Integer> graceTime) {
        this.graceTimeMap = graceTime;
    }

    public ConcurrentHashMap<Integer, ArrayList<AbstractCaller>> getIdObserver() {
        return idObserver;
    }

    public void setIdObserver(ConcurrentHashMap<Integer, ArrayList<AbstractCaller>> idObserver) {
        this.idObserver = idObserver;
    }
}
