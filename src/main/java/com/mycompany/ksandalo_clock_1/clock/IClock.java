/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ksandalo_clock_1.clock;
import com.mycompany.ksandalo_clock_1.alarm.IAlarm;
import com.mycompany.ksandalo_clock_1.alarm.INamedAlarm;
import java.util.ArrayList;
import java.util.List;

public interface IClock {
    void setValue(int value, CArrow type) throws IndexOutOfBoundsException;
    int getValue(CArrow type) throws RuntimeException;
    String getName();
    double getCost();
    String getCurrentTime();
    void addWorker(IAlarm worker);
    boolean addAlarm(IAlarm alarm, String name);
    boolean addAlarms(List<INamedAlarm> alarms);
    boolean removeAlarm(String name);
    void printKeys();
    void resetTime(IClock other);
    ArrayList<String> getWorkerNames();
}
