/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ksandalo_clock_1.server;

import com.mycompany.ksandalo_clock_1.clock.CArrow;
import com.mycompany.ksandalo_clock_1.real_time_clock.RTClock;
import java.util.ArrayList;

/**
 *
 * @author sanda
 */
public class Model {
    ArrayList<IObserver> all_o = new ArrayList<>();
    RTClock clock;
    public Model() {
        
    }
    public Model(RTClock clock) {
        this.clock = clock;
    }
    
    void update(Msg message) {
        for (IObserver obs : all_o) {
            obs.update(message);
        }
    }
    
    public void add(Msg msg) {
        switch(msg.type) {
            case START_CLOCK:
                this.clock.start();
                System.out.println("Clock started");
                break;
            case STOP_CLOCK:
                this.clock.stop();
                System.out.println("Clock stopped");
                break;
            case PAUSE_CLOCK:
                this.clock.setPause();
                System.out.println("Clock paused");
                break;
            case RESET_TIME:
                this.clock.resetTime(msg.hours, msg.minutes, msg.seconds);
                System.out.println("Time updated");
                break;
            default:
                if (msg.type == MessageType.CREATE_ALARM) {
                    if (this.clock.addAlarm(msg, this)) {
                        System.out.println("Alarm created");
                        msg = new Msg(MessageType.ALARM_CREATED,
                        0, 0, 0, msg.getName());
                    }
                    else {
                        System.out.println("Alarm not created");
                        msg = new Msg(MessageType.ALARM_NOT_CREATED,
                        0, 0, 0, msg.getName());
                    }
                }
                if (msg.type == MessageType.DELETE_ALARM
                        || msg.type == MessageType.SHOW_ALARM) {
                    if (this.clock.deleteAlarm(msg)) {
                        System.out.println("Alarm deleted");
                        msg = new Msg(MessageType.ALARM_DELETED,
                        msg.getValue(CArrow.HOUR), 0, 0, msg.getName());
                    }
                    else {
                        System.out.println("Alarm not deleted");
                        msg = new Msg(MessageType.ALARM_NOT_DELETED,
                        msg.getValue(CArrow.HOUR), 0, 0, msg.getName());
                    }
                }
                update(msg);
                break;
        }
    }
    
    public void addObs(IObserver obs) {
        all_o.add(obs);
    }
}
