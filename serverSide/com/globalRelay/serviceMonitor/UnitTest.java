package com.globalRelay.serviceMonitor;
import static org.junit.Assert.assertEquals;
/**
 * Created by jasonsun on 11/02/2018.
 */
public class UnitTest {

    public void testConcreteCaller(){
        ConcreteCaller concreteCaller = new ConcreteCaller("Caller1", 1, 1);
        assertEquals("Caller1", concreteCaller.getName());
    }

    public void testConcreteMonitorAddObserver(){
        ConcreteMonitor concreteMonitor = new ConcreteMonitor();
        ConcreteCaller caller1 = new ConcreteCaller("caller1", 1, 1);
        concreteMonitor.addObservers(caller1,1);
        assertEquals(1, concreteMonitor.getIdObserver().size());
    }


    public void testConcreteMonitorDeleteObserver() {
        ConcreteMonitor concreteMonitor = new ConcreteMonitor();
        ConcreteCaller caller1 = new ConcreteCaller("caller1", 1, 1);
        concreteMonitor.addObservers(caller1,1);
        concreteMonitor.deleteObservers(caller1,1);
        assertEquals(0, concreteMonitor.getIdObserver().size());

    }

    public void testConcreteMonitorServiceOutrage(){
        ConcreteMonitor concreteMonitor = new ConcreteMonitor();
        ConcreteCaller caller1 = new ConcreteCaller("caller1", 1, 1);
        concreteMonitor.addObservers(caller1,1);
        concreteMonitor.serviceOutrage(caller1, "12:00", "15:00", 1);
        assertEquals(1, concreteMonitor.getOutrageTime().size());
    }

    public void testConcreteMonitorAddGraceTime(){
        ConcreteMonitor concreteMonitor = new ConcreteMonitor();
        ConcreteCaller caller1 = new ConcreteCaller("caller1", 1, 1);
        concreteMonitor.addObservers(caller1,1);
        concreteMonitor.addGraceTime(caller1,1,1);
        assertEquals(1, concreteMonitor.getGraceTimeMap().size());
    }





}
