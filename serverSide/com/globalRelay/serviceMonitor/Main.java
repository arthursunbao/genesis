package com.globalRelay.serviceMonitor;

public class Main {

    public static void main(String[] args) {

        /**
         *
         * To-Do List:
         * 1. Use NIO for better handling for the connection between service and monitor(Due to time limit)
         * 2. Extend Data Type Compatibility to allow more data types except current String datatype
         * 3. Better handling for graceful timing
         * 4. Combine Service and Monitor into one application.
         */

        /**
         * Design and implement (in java) a service monitoring class. This monitor will be
         * used to monitor the status of multiple services
         */

        /**
         *
         * A service is defined as a host/port combination. To check if a service is up, the
         * monitor will establish a TCP connection to the host on the specified port.
         *
         * Answer: I have divided into two separate program, one is Scheduled Task for Service
         * as client side and another one is the current program as the monitor side. Client side
         * and monitor side use actual TCP for data communication, which has been tested from my
         * laptop.
         *
         */

        /**
         * if a connection is established, the service is up. If the connection is refused, the
         * service is not up.
         *
         * Answer: This is achieved by constantly checking from client side(service) to send periodic
         * info to monitor side. If monitor side doesn't receive heart beat from client side (using timer)
         * Then the service is assumed down
        **/

        /**
         * The monitor will allow callers to register interest in a service, and a polling
         * frequency. The callers will be notified when the service goes up and down.
         *
         * Answer: This is achieved by using design pattern: subscriber design pattern
         */

        /**
         * The monitor should detect multiple callers registering interest in the same service,
         * and should not poll any service more frequently than once a second.
         *
         * Answer: This is achieved by using design pattern: subscriber design pattern and a list
         * is used to store all interested callers
         *
         **/

        /**
         * The monitor should allow callers to register a planned service outage. The caller
         * will specify a start and end time for which no notifications for that service will be
         * delivered.
         *
         * Answer: This is achieved by using design pattern: subscriber design pattern and a hashtable
         * is used to store respecitve caller and its start and end time.
         *
         **/

        /**
         * The monitor should allow callers to define a grace time that applies to all services
         * being monitored. If a service is not responding, the monitor will wait for the grace
         * time to expire before notifying any clients. If the service goes back on line during
         * this grace time, no notification will be sent. If the grace time is less than the
         * polling frequency, the monitor should schedule extra checks of the service
         *
         * Answer: It has been perodically checked to make sure the service and monitor are in coordination
         */

        //Create Three Callers
        ConcreteCaller caller1 = new ConcreteCaller("caller1", 1, 1);
        ConcreteCaller caller2 = new ConcreteCaller("caller2", 2, 1);
        ConcreteCaller caller3 = new ConcreteCaller("caller1", 3, 1);
        ConcreteCaller caller4 = new ConcreteCaller("caller4", 4, 3);

        //Create a monitor
        ConcreteMonitor conMonitor = new ConcreteMonitor();

        //Adding callers to current monitor
        conMonitor.addObservers(caller1,1);
        conMonitor.addObservers(caller2,1);
        conMonitor.addObservers(caller3,1);
        conMonitor.addObservers(caller4,3);
        conMonitor.deleteObservers(caller1,1);

        //conMonitor.notifyAllObservers(("Broadcast Message From Service: Service Number " + 1 + "is UP"),1);

        //Callers Registered for planned service outage
        conMonitor.serviceOutrage(caller2,"12:00","14:00", 1);
        conMonitor.serviceOutrage(caller3,"15:00","19:00", 1);

        //Callers Set Up Grace Time for caller2 and caller3 in Service 1
        conMonitor.addGraceTime(caller2, 1, 1);
        conMonitor.addGraceTime(caller3, 2, 1);

        //Set up TCP Server Socket from Monitor Side and start listening for service TCP connection
        monitorTcpProcess monitor = new monitorTcpProcess(conMonitor);
        monitor.initMonitor();
        monitor.setupTCPListing();


    }
}
