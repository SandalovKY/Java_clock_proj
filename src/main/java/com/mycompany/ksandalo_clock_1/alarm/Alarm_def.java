/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ksandalo_clock_1.alarm;

import com.mycompany.ksandalo_clock_1.clock.CArrow;
import com.mycompany.ksandalo_clock_1.clock.IClock;
import com.mycompany.ksandalo_clock_1.server.MessageType;
import com.mycompany.ksandalo_clock_1.server.Model;
import com.mycompany.ksandalo_clock_1.server.Msg;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

/**
 *
 * @author sanda
 */

@Entity
@Inheritance (strategy = InheritanceType.TABLE_PER_CLASS)
public class Alarm_def implements INamedAlarm{
    @Id
    @Column (name = "alarmId")
    private int alarmId;
    
    @Column (name = "alarmName")
    protected String name;
    
    @Column (name = "hours")
    protected int hour;
    
    @Column (name = "minutes")
    protected int minute;
    
    @Transient
    protected Model model;
    
    public Alarm_def() {}
    public Alarm_def(int hour, int minute, Model m, String name) {
        this.name = name;
        this.alarmId = name.hashCode();
        this.model = m;
        this.hour = hour;
        this.minute = minute;
    }
    @Override
    public void doWork(IClock clock) { 
        if (this.hour == clock.getValue(CArrow.HOUR)
            && this.minute == clock.getValue(CArrow.MINUTE)) {
            new Thread(() -> {
                System.out.println("It's time");
                Msg mes = new Msg(MessageType.SHOW_ALARM,
                clock.getValue(CArrow.HOUR),
                clock.getValue(CArrow.MINUTE),
                -1,
                this.name);
                this.model.add(mes);
            }).start();
        }
        else System.out.println("Not time");
    }
    
    public int getAlarmId() {
        return this.alarmId;
    }
    
    public void setAlarmName(String id) {
        this.name = id;
    }
    
    public int getHours() {
        return this.hour;
    }
    
    public void setHours(int hours) {
        this.hour = hours;
    }
    
    public int getMinutes() {
        return this.minute;
    }
    
    public void setMinutes(int minutes) {
        this.minute = minutes;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
