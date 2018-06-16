package fr.sldeveloperand.countdown.models;

import java.util.Date;
import java.util.Observable;
import java.util.Optional;

import fr.sldeveloperand.countdown.R;
import fr.sldeveloperand.countdown.helpers.DateHelper;

public class MyEvent extends Observable{

    private String name;
    private Date deadline;

    public MyEvent() {
        super();
    }

    public MyEvent(String name, Date deadline) {
        this();
        this.name = name;
        this.deadline = deadline;
        this.setChanged();
         if(this.deadline != null && this.name != null)this.notifyObservers();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.setChanged();
        if(this.deadline != null)this.notifyObservers();
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
        this.setChanged();
       if(this.name != null) this.notifyObservers();

    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", deadline=" + deadline +
                '}';
    }





}
