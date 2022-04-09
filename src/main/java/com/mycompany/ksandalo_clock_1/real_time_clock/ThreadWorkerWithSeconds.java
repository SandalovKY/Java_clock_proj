/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ksandalo_clock_1.real_time_clock;

import com.mycompany.ksandalo_clock_1.clock.CArrow;
import com.mycompany.ksandalo_clock_1.clock.IClock;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author sanda
 */
public class ThreadWorkerWithSeconds implements Runnable{
    private IClock clock;
    private AtomicBoolean isBusy;

    ThreadWorkerWithSeconds(IClock clock, AtomicBoolean status) {
        this.clock = clock;
        this.isBusy = status;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(1000);
                while (this.isBusy.get()) {
                    synchronized (this.isBusy) {
                        System.out.println("WAIT called");
                        this.isBusy.wait();
                    }
                }
                if (clock.getValue(CArrow.SECOND) == 59) {
                    clock.setValue(0, CArrow.SECOND);
                    if (clock.getValue(CArrow.MINUTE) == 59) {
                        clock.setValue(0, CArrow.MINUTE);
                        if (clock.getValue(CArrow.HOUR) == 11) {
                            clock.setValue(0, CArrow.HOUR);
                        } else {
                            clock.setValue(clock.getValue(CArrow.HOUR) + 1, CArrow.HOUR);
                        }
                    } else {
                        clock.setValue(clock.getValue(CArrow.MINUTE) + 1, CArrow.MINUTE);
                    }
                } else {
                    clock.setValue(clock.getValue(CArrow.SECOND) + 1, CArrow.SECOND);
                }
            }
        } catch (InterruptedException ex) {
            System.out.println("Clock execution stopped");
        }
    }
}
