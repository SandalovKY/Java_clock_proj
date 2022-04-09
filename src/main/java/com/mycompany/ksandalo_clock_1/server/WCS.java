/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ksandalo_clock_1.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.ksandalo_clock_1.clock.CArrow;
import com.mycompany.ksandalo_clock_1.real_time_clock.RTClock;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sanda
 */
public class WCS extends Thread implements IObserver{
    
    Socket cs;
    Model m;
    
    OutputStream os;
    InputStream is;
    DataInputStream dis;
    DataOutputStream dos;
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public WCS(Socket cs, Model m, RTClock initClock) {
        try {
            this.cs = cs;
            os = cs.getOutputStream();
            dos = new DataOutputStream(os);
            
            this.m = m;
            m.addObs(this);
            this.start();
            Msg initMes = new Msg(MessageType.INIT,
                    initClock.getValue(CArrow.HOUR),
                    initClock.getValue(CArrow.MINUTE),
                    initClock.getValue(CArrow.SECOND),
                    "");
            initMes.addAllAlarmKeys(initClock.getWorkerNames());
            send(gson.toJson(initMes));
            
        } catch (IOException ex) {
            Logger.getLogger(WCS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        try {
            is = cs.getInputStream();
            dis = new DataInputStream(is);
            while(true) {
                    String obj = dis.readUTF();
                    Msg msg = gson.fromJson(obj, Msg.class);
                    m.add(msg);
            }
        } catch (IOException ex) {
            Logger.getLogger(WCS.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cs.close();
            } catch (IOException exin) {
                Logger.getLogger(WCS.class.getName()).log(Level.SEVERE, null, exin);
            }
        }
    }
    
    public void send(String s) {
        try {
            dos.writeUTF(s);
        } catch (IOException ex) {
            Logger.getLogger(WCS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void update(Msg message) {
        send(gson.toJson(message));
    }
    
}
