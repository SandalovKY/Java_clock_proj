/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ksandalo_clock_1.server;

/**
 *
 * @author sanda
 */
public enum MessageType {
    INIT,
    STOP_CLOCK,
    START_CLOCK,
    PAUSE_CLOCK,
    UPDATE_TIME,
    RESET_TIME,
    CREATE_ALARM,
    DELETE_ALARM,
    SHOW_ALARM,
    ALARM_CREATED,
    ALARM_NOT_CREATED,
    ALARM_DELETED,
    ALARM_NOT_DELETED,
}
