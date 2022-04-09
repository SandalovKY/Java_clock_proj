/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ksandalo_clock_1.server;

import com.mycompany.ksandalo_clock_1.alarm.IAlarm;
import com.mycompany.ksandalo_clock_1.clock.CArrow;
import com.mycompany.ksandalo_clock_1.clock.Clock;
import com.mycompany.ksandalo_clock_1.clock.Clock_ws;
import com.mycompany.ksandalo_clock_1.real_time_clock.RTClock;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sanda
 */
public class ServerClass {
    int port = 3124;
    InetAddress host;
    
    Model m = new Model();
    RTClock clock;
    
    public ServerClass() {
        
        clock = new RTClock(new Clock_ws("Better Rolex", 20000));
        m = new Model(clock);
        
        IAlarm print = (clock1)->{
            System.out.println(clock1.getCurrentTime());
        };
        IAlarm updateView = (clock1)->{
            Msg mes = new Msg(MessageType.UPDATE_TIME,
                clock1.getValue(CArrow.HOUR),
                clock1.getValue(CArrow.MINUTE),
                clock1.getValue(CArrow.SECOND),
                "");
                m.add(mes);
        };
        clock.addWorker(print);
        clock.addWorker(updateView);
        
        try {
            this.host = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerClass.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            ServerSocket ss = new ServerSocket(this.port, 0, this.host);
            System.out.println("Server started");
            
            while (true) {
                Socket cs = ss.accept();
                
                WCS wcs = new WCS(cs, m, clock);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void main(String[] args) {
        ServerClass server = new ServerClass();
    }
}
