package com.globalRelay.serviceMonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jasonsun on 10/02/2018.
 */

public class tcpHandlingThread implements Runnable {

    /*
        This function setups TCP connection with the clientSide service and gets service information from the service.
        We use multi-threaded application to receive multiple connections from the service.
        */

    private AbstractMonitor monitor = new ConcreteMonitor();
    private Socket client = null;
    private ConcurrentHashMap<Integer, ConcreteMonitor> service = new ConcurrentHashMap<>();
    private Hashtable<Integer, ConcreteMonitor> Service = new Hashtable<>();
    public tcpHandlingThread(Socket client, ConcurrentHashMap<Integer, ConcreteMonitor> service, AbstractMonitor monitor) {
        this.client = client;
        this.service = service;
        this.monitor = monitor;
    }

    public void run(){
    /*
        Run() function will implement the following functionality:
        1) Receive data from remote service
        2) Judge whether the sending data is for initial connection or for maintaining status information
            a) if it is initial connection, then store the service information into monitor service concurrenthashmap
            b) if it is maintaining status information, then check the time duration comparing to previous sent time stamp
     */
        try{
            //PrintStream out = new PrintStream(client.getOutputStream());
            String str = "";
            BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
            boolean flag =true;
            while(flag) {
                if(buf.readLine() != null){
                    str = buf.readLine();
                    System.out.println(str);
                    String[] strArray = str.split("&&");
                    String service_name = strArray[0].split(":")[1];
                    String service_id = strArray[1].split(":")[1];

                    String connection_type = strArray[2].split(":")[1];
                    String last_connected_time = strArray[3].split(":")[1];

                    System.out.println("TCP Message: Connection is connected." + " Service Name: " + service_name + " Status: " + connection_type + " Service Id: " + service_id + " Last Update Time: " + last_connected_time);

                    if(Service.containsKey(Integer.valueOf(service_id))) {
                        ConcreteMonitor curService = service.get(Integer.valueOf(service_id));
                        if(curService.getService_type().equals("INIT_CONN")){
                            curService.setService_type("MAINTAIN");
                        }
                        else{
                            if (!curService.getLastUpdateTime().equals(String.valueOf(new Date()))){
                                curService.setServiceName(String.valueOf(new Date()));
                                //Service.put(Integer.valueOf(service_id), curService);
                            }
                            else {
                                System.out.println("Lost Connection to the Service. Now Exit");
                                break;
                            }
                        }
                    }
                    else{
                        // Current Service doesn't exist, then just restore the current service
                        ConcreteMonitor curService = new ConcreteMonitor();
                        curService.setService_type(connection_type);
                        curService.setServiceName(service_name);
                        curService.setLastUpdateTime(last_connected_time);
                        curService.setServiceId(Integer.valueOf(service_id));
                        //Service.put(Integer.valueOf(service_id), curService);
                        monitor.notifyAllObservers(("Service ID " + service_id + " Service is UP !"), Integer.valueOf(service_id));
                    }
                    if(str.equals("EOF")) {
                        System.out.println("Service is off");
                        flag = false;


                    }
                }

            }
            client.close();
        }
        catch(NullPointerException e){

        }
        catch(IOException e){
            System.out.println("Socket Closed from Service Side, Exit");
            System.exit(0);
        }
    }

    public AbstractMonitor getMonitor() {
        return monitor;
    }

    public void setMonitor(AbstractMonitor monitor) {
        this.monitor = monitor;
    }

}
