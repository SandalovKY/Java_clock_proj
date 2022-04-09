package com.mycompany.ksandalo_clock_1.real_time_clock;

import com.mycompany.ksandalo_clock_1.clock.CArrow;
import com.mycompany.ksandalo_clock_1.clock.IClock;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author sanda
 */
public class ThreadWorker implements Runnable{
    private IClock clock;
    private AtomicBoolean isBusy;
    
    public ThreadWorker(IClock clock, AtomicBoolean status) {
        this.clock = clock;
        this.isBusy = status;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(60000);
                while (this.isBusy.get()) {
                    synchronized (this.isBusy) {
                        System.out.println("WAIT called");
                        this.isBusy.wait();
                    }
                }
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
            }
        } catch (InterruptedException ex) {
            System.out.println("Clock execution stopped");
        }
    }
}
