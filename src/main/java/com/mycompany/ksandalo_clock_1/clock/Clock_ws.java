/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ksandalo_clock_1.clock;

/**
 *
 * @author sanda
 */
public class Clock_ws extends Clock {
    protected int seconds;

    public Clock_ws(){
        super();
        this.seconds = 0;
    }

    public Clock_ws(String name, double cost) {
        super(name, cost);
        this.seconds = 0;
    }

    @Override
    public void setValue(int value, CArrow type) throws IndexOutOfBoundsException{
        if (type == CArrow.SECOND) {
            if (value > 59 || value < 0) throw new IndexOutOfBoundsException("Incorrect seconds value");
            this.seconds = value;
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
        super.setValue(value, type);
    }

    @Override
    public int getValue(CArrow type) {
        if (type == CArrow.SECOND) {
            return this.seconds;
        }
        return super.getValue(type);
    }

    @Override
    public String getCurrentTime() {
        return super.getCurrentTime() + ":" + this.seconds + "s";
    }
}
