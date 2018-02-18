package com.globalRelay.serviceMonitor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ConcurrentHashMap;




/**
 * Created by jasonsun on 10/02/2018.
 */

class monitorTcpProcess {


    public monitorTcpProcess(AbstractMonitor monitor) {
        this.monitor = monitor;
    }

    private AbstractMonitor monitor = new ConcreteMonitor();
    private ConcurrentHashMap<Integer, ConcreteMonitor> service = new ConcurrentHashMap<>();

    public void setupTCPListing(){
            try{
                ServerSocket server = new ServerSocket(20006);
                //Set Timeout to 1s
                server.setSoTimeout(10000);
                Socket client = null;
                boolean f = true;
                System.out.println("TCP Listening Started From Monitor Side");
                while(f){
                    client = server.accept();
                    System.out.println("Connection Successful!");
                    new Thread(new tcpHandlingThread(client, service, monitor)).start();
                }
                server.close();
            }
            catch(SocketTimeoutException e){
                System.out.println("The Service has been shut down from service side");
            }
            catch(IOException e) {
                e.printStackTrace();
            }
    }


    public void initMonitor(){
        this.service = null;
    }

    public ConcurrentHashMap<Integer, ConcreteMonitor> getService() {
        return service;
    }

    public void setService(ConcurrentHashMap<Integer, ConcreteMonitor> service) {
        this.service = service;
    }

    public AbstractMonitor getMonitor() {
        return monitor;
    }

    public void setMonitor(AbstractMonitor monitor) {
        this.monitor = monitor;
    }
}
