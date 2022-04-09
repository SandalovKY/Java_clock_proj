/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ksandalo_clock_1.clock;

import com.mycompany.ksandalo_clock_1.alarm.IAlarm;
import com.mycompany.ksandalo_clock_1.alarm.INamedAlarm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author sanda
 */
public class Clock implements IClock{

    protected HashMap<String, IAlarm> alarms;
    protected ArrayList<IAlarm> workers;
    protected int hour;
    protected int minute;
    protected String name;
    protected double cost;

    public Clock() {
        this.hour = 0;
        this.minute = 0;
        this.name = null;
        this.cost = 0.0;
        this.alarms = new HashMap<>();
        this.workers = new ArrayList<>();
    }

    public Clock(String name, double cost) {
        this.name = name;
        this.cost = cost;
        this.hour = 0;
        this.minute = 0;
        this.alarms = new HashMap<>();
        this.workers = new ArrayList<>();
    }

    @Override
    public void setValue(int value, CArrow type)
            throws IndexOutOfBoundsException{
        if (type == CArrow.HOUR) {
            if (value > 11 || value < 0)
                throw new IndexOutOfBoundsException("Incorrect hours value");
            this.hour = value;
            alarms.entrySet().forEach(el -> {
                el.getValue().doWork(this);
            });
            workers.forEach(worker -> {
                worker.doWork(this);
            });
            return;
        }
        if (type == CArrow.MINUTE) {
            if (value > 59 || value < 0)
                throw new IndexOutOfBoundsException("Incorrect minutes value");
            this.minute = value;
            if (value != 0) {
                alarms.entrySet().forEach(el -> {
                    el.getValue().doWork(this);
                });
                workers.forEach(worker -> {
                    worker.doWork(this);
                });
            }
            return;
        }
        throw new RuntimeException("Current clock doesn't contain this value");

    }

    @Override
    public int getValue(CArrow type) throws RuntimeException{
        if (type == CArrow.HOUR) {
            return this.hour;
        }
        if (type == CArrow.MINUTE) {
            return this.minute;
        }
        if (type == CArrow.SECOND) {
            return -1;
        }
        throw new RuntimeException("Current clock doesn't contain this value");
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getCost() {
        return this.cost;
    }

    @Override
    public String getCurrentTime() {
        return (this.hour + "h:" + this.minute + "m");
    }

    @Override
    public void addWorker(IAlarm alarm) {
        this.workers.add(alarm);
    }
    
    @Override
    public boolean addAlarm(IAlarm alarm, String name) {
        boolean notContainsClock = !alarms.containsKey(name);
        if (notContainsClock) {
            alarms.put(name, alarm);
        }
        return notContainsClock;
    }

    @Override
    public boolean removeAlarm(String name) {
        IAlarm ret = alarms.remove(name);
        return ret != null;
    }

    @Override
    public void printKeys() {
        alarms.entrySet().forEach(el -> {
            System.out.println(el.getKey());
        });
    }

    @Override
    public void resetTime(IClock other) {
    }

    @Override
    public ArrayList<String> getWorkerNames() {
        ArrayList<String> names = new ArrayList<>();
        this.alarms.entrySet().forEach(el -> {
            names.add(el.getKey());
        });
        return names;
    }

    @Override
    public boolean addAlarms(List<INamedAlarm> alarms) {
        for (INamedAlarm al : alarms) {
             if (!this.alarms.containsKey(al.getName())) {
                 this.alarms.put(al.getName(), al);
             }
        }
        return true;
    }
    
    
}
