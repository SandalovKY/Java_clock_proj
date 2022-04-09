/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ksandalo_clock_1.gui_view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.ksandalo_clock_1.clock.CArrow;
import com.mycompany.ksandalo_clock_1.clock.Clock;
import com.mycompany.ksandalo_clock_1.clock.Clock_ws;
import com.mycompany.ksandalo_clock_1.clock.IClock;
import com.mycompany.ksandalo_clock_1.server.MessageType;
import com.mycompany.ksandalo_clock_1.server.Msg;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author sanda
 */
public class SecondFrame extends javax.swing.JFrame {
    Socket cs;
    OutputStream os;
    DataOutputStream dos;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    private DefaultListModel model;
    public SecondFrame(Socket cs) {
        try {
            this.clock = null;
            this.model = new DefaultListModel();
            this.cs = cs;
            this.os = cs.getOutputStream();
            this.dos = new DataOutputStream(os);
        } catch (IOException ex) {
            Logger.getLogger(SecondFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void handleMsg(Msg message) {
        switch(message.getMsgType()) {
            case UPDATE_TIME:
                if (clock != null) {
                    if (clock.getValue(CArrow.SECOND) == -1) {
                        clock.setValue(message.getValue(CArrow.HOUR), CArrow.HOUR);
                        clock.setValue(message.getValue(CArrow.MINUTE), CArrow.MINUTE);
                    }
                    else {
                        clock.setValue(message.getValue(CArrow.HOUR), CArrow.HOUR);
                        clock.setValue(message.getValue(CArrow.MINUTE), CArrow.MINUTE);
                        clock.setValue(message.getValue(CArrow.SECOND), CArrow.SECOND);
                    System.out.println("TimeUpdated " + String.valueOf(message.getValue(CArrow.SECOND)));
                    }
                    this.clockPanel1.Update();
                }
                break;
            case SHOW_ALARM:
                new Thread(()->{
                    JOptionPane.showMessageDialog(SecondFrame.this, message.getName());
                }).start();
                break;
            case ALARM_CREATED:
                new Thread(()->{
                    JOptionPane.showMessageDialog(SecondFrame.this, "Alarm \"" + message.getName() +
                            "\" successfully created!");
                }).start();
                this.model.addElement(message.getName());
                break;
            case ALARM_NOT_CREATED:
                new Thread(()->{
                    JOptionPane.showMessageDialog(SecondFrame.this, "Incorrect alarm name");
                }).start();
                break;
            case INIT:
                if (message.getValue(CArrow.SECOND) == -1) {
                    clock = new Clock();
                    clock.setValue(message.getValue(CArrow.HOUR), CArrow.HOUR);
                    clock.setValue(message.getValue(CArrow.MINUTE), CArrow.MINUTE);
                }
                else {
                    clock = new Clock_ws();
                    clock.setValue(message.getValue(CArrow.HOUR), CArrow.HOUR);
                    clock.setValue(message.getValue(CArrow.MINUTE), CArrow.MINUTE);
                    clock.setValue(message.getValue(CArrow.SECOND), CArrow.SECOND);
                }
                initComponents();
                this.model.addAll(message.getAllKeys());
                break;
            case ALARM_DELETED:
                if (model.indexOf(message.getName()) != -1) {
                    new Thread(()->{
                        JOptionPane.showMessageDialog(SecondFrame.this, "Alarm \"" + message.getName() +
                                "\" successfully deleted!");
                    }).start();
                    this.model.remove(message.getValue(CArrow.HOUR));
                }
                break;
            case ALARM_NOT_DELETED:
                if (model.indexOf(message.getName()) != -1) {
                    new Thread(()->{
                        JOptionPane.showMessageDialog(SecondFrame.this, "ERROR: Alarm \"" + message.getName() +
                                "\" not found on server!");
                    }).start();
                    this.model.remove(message.getValue(CArrow.HOUR));
                }
                break;
            default:
                break;
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        clockPanel1 = new com.mycompany.ksandalo_clock_1.gui_view.ClockPanel(this.clock);
        button1 = new java.awt.Button();
        button2 = new java.awt.Button();
        button3 = new java.awt.Button();
        button4 = new java.awt.Button();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        button5 = new java.awt.Button();
        button6 = new java.awt.Button();

        jButton1.setText("Delete");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        javax.swing.GroupLayout clockPanel1Layout = new javax.swing.GroupLayout(clockPanel1);
        clockPanel1.setLayout(clockPanel1Layout);
        clockPanel1Layout.setHorizontalGroup(
            clockPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 199, Short.MAX_VALUE)
        );
        clockPanel1Layout.setVerticalGroup(
            clockPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 149, Short.MAX_VALUE)
        );

        button1.setLabel("Stop");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button2.setLabel("Start");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        button3.setLabel("Pause");
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });

        button4.setLabel("Add Alarm");
        button4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button4ActionPerformed(evt);
            }
        });

        jList1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jList1.setModel(model);
        jScrollPane1.setViewportView(jList1);

        button5.setLabel(" Delete Alarm");
        button5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button5ActionPerformed(evt);
            }
        });

        button6.setLabel("Reset");
        button6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(clockPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(clockPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(button1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(button5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        button4.getAccessibleContext().setAccessibleName("addAlarm");
        button4.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendMsg(Msg mes) {
        try {
            dos.writeUTF(gson.toJson(mes));
        } catch (IOException ex) {
            Logger.getLogger(SecondFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        sendMsg(new Msg(MessageType.START_CLOCK, 0, 0, 0, ""));
    }//GEN-LAST:event_button2ActionPerformed

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        sendMsg(new Msg(MessageType.PAUSE_CLOCK, 0, 0, 0, ""));
    }//GEN-LAST:event_button3ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        sendMsg(new Msg(MessageType.STOP_CLOCK, 0, 0, 0, ""));
    }//GEN-LAST:event_button1ActionPerformed

    private void button4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button4ActionPerformed
        if (this.clock != null) {
            JTextField hours = new JTextField(String.valueOf(this.clock.getValue(CArrow.HOUR)), 10);
            JTextField minutes = new JTextField(String.valueOf(this.clock.getValue(CArrow.MINUTE)), 10);
            if (this.clock.getValue(CArrow.SECOND) >= 0) {
                JTextField seconds = new JTextField(String.valueOf(this.clock.getValue(CArrow.SECOND)), 10);
                JTextField name = new JTextField(1);
                Object[] message = {
                    "Name:", name,
                    "Hours:", hours,
                    "Minutes:", minutes,
                    "Seconds", seconds
                };
                int option = JOptionPane.showConfirmDialog(this,
                        message,
                        "Alarm",
                        JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    sendMsg(new Msg(MessageType.CREATE_ALARM,
                            Integer.parseInt(hours.getText()),
                            Integer.parseInt(minutes.getText()),
                            Integer.parseInt(seconds.getText()),
                            name.getText()));
                }
            }
            else {
                JTextField name = new JTextField(1);
                Object[] message = {
                    "Name:", name,
                    "Hours:", hours,
                    "Minutes:", minutes
                };
                int option = JOptionPane.showConfirmDialog(this,
                        message,
                        "Alarm",
                        JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    sendMsg(new Msg(MessageType.CREATE_ALARM,
                            Integer.parseInt(hours.getText()),
                            Integer.parseInt(minutes.getText()),
                            -1,
                            name.getText()));
                }
            }
            this.clock.printKeys();
        }
    }//GEN-LAST:event_button4ActionPerformed

    private void button5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button5ActionPerformed
        int index = this.jList1.getSelectedIndex();
        if (index < 0) {
            JOptionPane.showMessageDialog(SecondFrame.this, "Никакой будильник не выбран");
        }
        else {
            try {
                String name = this.model.getElementAt(index).toString();
                Msg deleteAlarm = new Msg(MessageType.DELETE_ALARM, index, 0, this.clock.getValue(CArrow.SECOND), name);
                this.dos.writeUTF(gson.toJson(deleteAlarm));
            } catch (IOException ex) {
                Logger.getLogger(SecondFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_button5ActionPerformed

    private void button6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button6ActionPerformed
        if (this.clock != null) {
            JTextField hours = new JTextField(String.valueOf(this.clock.getValue(CArrow.HOUR)), 10);
            JTextField minutes = new JTextField(String.valueOf(this.clock.getValue(CArrow.MINUTE)), 10);
            if (this.clock.getValue(CArrow.SECOND) >= 0) {
                JTextField seconds = new JTextField(String.valueOf(this.clock.getValue(CArrow.SECOND)), 10);
                Object[] message = {
                    "Hours:", hours,
                    "Minutes:", minutes,
                    "Seconds", seconds
                };
                int option = JOptionPane.showConfirmDialog(this,
                        message,
                        "Set new time",
                        JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    sendMsg(new Msg(MessageType.RESET_TIME,
                            Integer.parseInt(hours.getText()),
                            Integer.parseInt(minutes.getText()),
                            Integer.parseInt(seconds.getText()),
                            ""));
                }
            }
            else {
                Object[] message = {
                    "Hours:", hours,
                    "Minutes:", minutes
                };
                int option = JOptionPane.showConfirmDialog(this,
                        message,
                        "Set new time",
                        JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    sendMsg(new Msg(MessageType.RESET_TIME,
                            Integer.parseInt(hours.getText()),
                            Integer.parseInt(minutes.getText()),
                            -1,
                            ""));
                }
            }
        }
    }//GEN-LAST:event_button6ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        try {
            this.cs.close();
        } catch (IOException ex) {
            Logger.getLogger(SecondFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SecondFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SecondFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SecondFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SecondFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
    }
    
    private IClock clock;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button button1;
    private java.awt.Button button2;
    private java.awt.Button button3;
    private java.awt.Button button4;
    private java.awt.Button button5;
    private java.awt.Button button6;
    private com.mycompany.ksandalo_clock_1.gui_view.ClockPanel clockPanel1;
    private javax.swing.JButton jButton1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
