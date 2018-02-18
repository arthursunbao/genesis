# Functionality
The application will be improved in the following aspects if more time permitted:
1. More thorough Unit Test and Integration Test Converage
2. Service to Master side NIO implementation for high-volume traffic under real circumstance (Netty)
3. Generic Data transmission between service to monitor and monitor to caller
4. Optimizing the subscriber design pattern used in monitor to caller
5. Optimizing the TCP transmission 

# Introduction:
The project is divided into two parts:
1. Client Side: Client side mocks the action of multiple services to send requests to a central monitor with multi-threaded programming via local TCP connection(localhost:127.0.0.1, port:20006)

2. Master Side: Master side mocks the monitor as well as caller to ask for service queue for its specific interested services.

3. Please run the Main class in monitor side first to initiate TCP Socket connection to the monitor service and then start the Main class in client side to accept simulated multiple service requests as well as initite multiple callers from monitor side.

Architecture:
Client Side(Simulated Service)   <---->      Master Side(Monitor)       <---->        Callers  Queue                                             TCP Mutli-Threaded                Subscriber Design Pattern


The core data structure used is three concurrentHashMap to store the following records:
1. ConcurrentHashMap(serviceId, interestedCallerQueue)
2. ConcurrentHashMap(callerObject, graceTime)
3. ConcurrentHashMap(callerObject, List(Outage Time))

# File Details:
## Master Side:
    1. AbstractCaller: Caller Interface
    2. ConcreteCaller: Implemention of Caller Class
    3. AbstractMonitor: Monitor Interface
    4. ConcreteMonitor: Implemention of Monitor Class
    5. monitorTcpProcess: Server-side TCP Handling Logic
    6. tcpHandlingThread: The multi-threaded handling logic for TCP connection
    7. UnitTest: unit test
    8. Main: starting function with example

## Client Side:
    1. AbstractService: Service Interface
    2. ConcreteService: Implemention of Service Class
    3. Main: starting function with example
    4. ScheduledTask: Multi-threaded program to send periodic service requests to maste
