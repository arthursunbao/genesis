package com.globalRelay.serviceClientSide;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jasonsun on 10/02/2018.
 */

class ScheduledTask extends TimerTask {
    private AbstractService currentService;
    private int serviceId;
    private int hostPort;
    private int timeOut;
    private String hostAddress;
    private String typeOfConnection;
    private String serviceName;

    public ScheduledTask(AbstractService currentService, int serviceId, int hostPort, int timeOut, String hostAddress, String typeOfConnection, String serviceName) {
        this.currentService = currentService;
        this.serviceId = serviceId;
        this.hostPort = hostPort;
        this.timeOut = timeOut;
        this.hostAddress = hostAddress;
        this.typeOfConnection = typeOfConnection;
        this.serviceName = serviceName;
    }

    @Override
    public void run() {
        System.out.println((new Date()) + "Sending Service Message Now");
        currentService.sendServiceInfo(serviceName,serviceId, typeOfConnection, String.valueOf(new Date()));
        new Timer().schedule(new ScheduledTask(currentService, serviceId, hostPort, timeOut, hostAddress, typeOfConnection, serviceName), 5000);
    }

    public AbstractService getCurrentService() {
        return currentService;
    }

    public void setCurrentService(AbstractService currentService) {
        this.currentService = currentService;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getHostPort() {
        return hostPort;
    }

    public void setHostPort(int hostPort) {
        this.hostPort = hostPort;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public String getTypeOfConnection() {
        return typeOfConnection;
    }

    public void setTypeOfConnection(String typeOfConnection) {
        this.typeOfConnection = typeOfConnection;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }





}