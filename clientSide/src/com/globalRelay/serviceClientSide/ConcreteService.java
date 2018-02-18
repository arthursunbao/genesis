package com.globalRelay.serviceClientSide;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jasonsun on 10/02/2018.
 */
public class ConcreteService implements AbstractService{

    private int serviceId;
    private int hostPort;
    private int timeOut;
    private String hostAddress;
    private String typeOfConnection;
    private String serviceName;
    private boolean connectionEstablished;

    public ConcreteService(int serviceId, int hostPort, int timeOut, String hostAddress, String typeOfConnection, String serviceName, boolean connectionEstablished) {
        this.serviceId = serviceId;
        this.hostPort = hostPort;
        this.timeOut = timeOut;
        this.hostAddress = hostAddress;
        this.typeOfConnection = typeOfConnection;
        this.serviceName = serviceName;
        this.connectionEstablished = connectionEstablished;
    }

    private Socket client = null;


    @Override
    public void createCompleteService(int serviceId, int hostPort, int timeOut, String connStatus, String hostAddress, String serviceName, int timeInterval){
        if(hostPort < 0 || timeOut <= 0 || connStatus.equals("") || hostAddress.equals("") || serviceName.equals("")){
            throw new IllegalArgumentException("Wrong Argument, Please kindly help to double check");
        }
        AbstractService service = new ConcreteService(serviceId, hostPort, timeOut, hostAddress, connStatus, serviceName, true);
        service.createConnection(hostAddress,hostPort,timeOut);
        service.sendServiceInfo(serviceName,serviceId, connStatus, String.valueOf(new Date()));
        //Initiate Scheduled Task to send periodic message to monitor
        TimerTask task = new ScheduledTask(service, serviceId, hostPort, timeOut, hostAddress, connStatus, serviceName);
        Timer timer = new Timer();
        timer.schedule(task, timeInterval);
    }

    @Override
    public void deleteCompleteService(){
        System.out.println("Delete All Service TCP Connection");
        System.exit(0);
    }


    @Override
    public void createConnection(String hostAddress, int hostPort, int timeOut) {
        if(hostAddress.equals("") || hostPort < 0 || timeOut < 0){
            throw new IllegalArgumentException("Wrong Argument, Please kindly help to double check");
        }
        try{

            client = new Socket(hostAddress, hostPort);
            client.setSoTimeout(timeOut);
        }

        catch(IOException e){
            System.out.println("Error Setup for TCP Connection. Please check. Now Exiting");
            System.exit(0);
        }

    }

    @Override
    public void sendServiceInfo(String serviceName, int serviceId, String typeOfConnection, String currentTime){
        if(serviceName.equals("") || serviceId < 0 || typeOfConnection.equals("") || currentTime.equals("")){
            throw new IllegalArgumentException("Wrong Argument, Please kindly help to double check");
        }
        try{
            if(client != null && !serviceName.equals("") && serviceId != -1 && !typeOfConnection.equals("")){
                PrintStream out = new PrintStream(client.getOutputStream());
                out.println("SERVICE_NAME:" + serviceName + "&&" + "SERVICE_ID:" + serviceId + "&&" + "CONNECTION_TYPE:" + typeOfConnection + "&&" + currentTime);
                //out.println("EOF");
            }

        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error Sending Message. Please help to check");
        }
    }


    public int getServiceId() {
        return serviceId;
    }

    public int getHostPort() {
        return hostPort;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public String getTypeOfConnection() {
        return typeOfConnection;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public void setHostPort(int hostPort) {
        this.hostPort = hostPort;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public void setTypeOfConnection(String typeOfConnection) {
        this.typeOfConnection = typeOfConnection;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}

