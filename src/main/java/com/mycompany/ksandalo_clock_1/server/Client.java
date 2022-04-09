/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ksandalo_clock_1.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.ksandalo_clock_1.gui_view.SecondFrame;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sanda
 */

public class Client {
    int port = 3124;
    InetAddress host;
    
    Socket cs;
    
    InputStream is;
    DataInputStream dis;
    SecondFrame frame;
    
    Thread t;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public Client() {
        try {
            
            host = InetAddress.getLocalHost();
            
            cs = new Socket(host, port);
            
            frame = new SecondFrame(cs);
            frame.setVisible(true);
                
            t = new Thread() {
                @Override
                public void run() {
                    try {
                        is = cs.getInputStream();
                        dis = new DataInputStream(is);
                        
                        while(true) {
                            String s = dis.readUTF();
                            Msg mes = gson.fromJson(s, Msg.class);
                            frame.handleMsg(mes);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            t.start();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        Client client = new Client();
    }
}
