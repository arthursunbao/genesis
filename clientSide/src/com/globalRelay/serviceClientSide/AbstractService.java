package com.globalRelay.serviceClientSide;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jasonsun on 11/02/2018.
 *
 * AbstractService Interface
 */
public interface AbstractService {
    public void createConnection(String hostAddress, int hostPort, int timeOut);
    public void sendServiceInfo(String serviceName, int serviceId, String typeOfConnection, String currentTime);
    public void createCompleteService(int serviceId, int hostPort, int timeOut, String connStatus, String hostAddress, String serviceName, int timeInterval);
    public void deleteCompleteService();

}
