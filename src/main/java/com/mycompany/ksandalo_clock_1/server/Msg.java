/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ksandalo_clock_1.server;

import com.mycompany.ksandalo_clock_1.clock.CArrow;
import java.util.ArrayList;

/**
 *
 * @author sanda
 */
public class Msg {
    MessageType type;
    int hours;
    int minutes;
    int seconds;
    String name;
    ArrayList<String> keys;
    
    public Msg(MessageType type,
        int hours,
        int minutes,
        int seconds,
        String name) {
        this.type = type;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.name = name;
    }
    
    public MessageType getMsgType() {
        return type;
    }
    
    public int getValue(CArrow name) {
        if (null == name) return -1;
        else switch (name) {
            case HOUR:
                return this.hours;
            case MINUTE:
                return this.minutes;
            case SECOND:
                return this.seconds;
            default:
                return -1;
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public void addAllAlarmKeys(ArrayList<String> arKeys) {
        this.keys = arKeys;
    }
    
    public ArrayList<String> getAllKeys () {
        return this.keys;
    }
}
