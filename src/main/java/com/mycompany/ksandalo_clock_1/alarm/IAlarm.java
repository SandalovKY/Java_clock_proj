/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ksandalo_clock_1.alarm;

import com.mycompany.ksandalo_clock_1.clock.IClock;

/**
 *
 * @author sanda
 */
@FunctionalInterface
public interface IAlarm {
    void doWork(IClock clock);
}
