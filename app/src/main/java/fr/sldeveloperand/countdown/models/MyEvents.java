package fr.sldeveloperand.countdown.models;

import java.util.ArrayList;
import java.util.List;

public class MyEvents {

    private List<MyEvent> myEvents;

    public MyEvents() {
        myEvents = new ArrayList<>();
    }

    public List<MyEvent> getEvents() {
        return myEvents;
    }

    public void addEvent(MyEvent e){
        myEvents.add(e);
    }
    public void removeEvent(MyEvent e){
        myEvents.remove(e);
    }
}
