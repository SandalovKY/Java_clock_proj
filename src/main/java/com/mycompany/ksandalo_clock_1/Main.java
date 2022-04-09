package com.mycompany.ksandalo_clock_1;

import com.mycompany.ksandalo_clock_1.alarm.Alarm_def;
import com.mycompany.ksandalo_clock_1.alarm.Alarm_sec;
import com.mycompany.ksandalo_clock_1.alarm.IAlarm;
import com.mycompany.ksandalo_clock_1.alarm.INamedAlarm;
import com.mycompany.ksandalo_clock_1.clock.Clock;
import com.mycompany.ksandalo_clock_1.clock.Clock_ws;
import com.mycompany.ksandalo_clock_1.clock.IClock;
import com.mycompany.ksandalo_clock_1.hibernate.HibernateSessionFactory;
import com.mycompany.ksandalo_clock_1.server.Model;
import java.util.List;

import javax.swing.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class Main {
    public static void main(String[] argv) throws ClassNotFoundException,
            UnsupportedLookAndFeelException, InstantiationException,
            IllegalAccessException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        IClock cl1 = new Clock("Rolex", 2000);
        IClock cl2 = new Clock_ws("Better Rolex", 20000);

        System.out.println("First watch: " + cl1.getName() + " with cost: " +
                cl1.getCost());
        System.out.println("Second watch: " + cl2.getName() + " with cost: " +
                cl2.getCost());

        IAlarm int1 = (clock1)->{System.out.println(clock1.getCurrentTime());};
        cl2.addWorker(int1);
        try {
            SessionFactory sf = HibernateSessionFactory.getSessionFactory(cl1);
            Session session = sf.openSession();
            Transaction tr1 = session.beginTransaction();
                IAlarm al1 = new Alarm_def(1, 2, new Model(), "Name1");
                session.save(al1);
            tr1.commit();
            Transaction tr2 = session.beginTransaction();
                IAlarm al2 = new Alarm_def(1, 2, new Model(), "Name");
                session.save(al2);
            tr2.commit();
            
            List<INamedAlarm> al_list;
            al_list = (List<INamedAlarm>)session.createQuery("from Alarm_def").list();
            for (INamedAlarm al : al_list) {
                System.out.println(al.getName());
            }
            Transaction tr3 = session.beginTransaction();
                Query q = session.createQuery("delete Alarm_def where id=:id");
                q.setParameter("id", new String("Name").hashCode());
                
                q.executeUpdate();
            tr3.commit();
            for (INamedAlarm al : al_list) {
                System.out.println(al.getName());
            }
            session.close();
        }
        catch (NullPointerException ex) {
            ex.printStackTrace();
        }
//        java.awt.EventQueue.invokeLater(() -> {
//            SecondFrame frame = new SecondFrame(new RTClock(cl1));
//            frame.setVisible(true);
//            
//            IAlarm int3 = (clock1)->{ frame.updateView(); };
//            cl1.addWorker(int3, "clock_updating");
//        });
    }
}
