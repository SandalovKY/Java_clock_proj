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

/**
 *
 * @author sanda
 */

@Entity
public class Alarm_sec extends Alarm_def {
    @Column (name = "seconds")
    private int second;

    public Alarm_sec() { super(); }
    public Alarm_sec(int hour, int minute, int second, Model model, String name) {
        super(hour, minute, model, name);
        this.second = second;
    }
    @Override
    public void doWork(IClock clock) {
        if (this.hour == clock.getValue(CArrow.HOUR)
            && this.minute == clock.getValue(CArrow.MINUTE)
            && this.second == clock.getValue(CArrow.SECOND)) {
            new Thread(() -> {
                System.out.println("It's time");
                Msg mes = new Msg(MessageType.SHOW_ALARM,
                clock.getValue(CArrow.HOUR),
                clock.getValue(CArrow.MINUTE),
                clock.getValue(CArrow.SECOND),
                this.name);
                this.model.add(mes);
            }).start();
        }
        else System.out.println("Not time");
    }

    public int getSec() {
        return second;
    }

    public void setSec(int seconds) {
        this.second = seconds;
    }
}
