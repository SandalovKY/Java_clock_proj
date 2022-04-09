/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ksandalo_clock_1.hibernate;

import com.mycompany.ksandalo_clock_1.alarm.Alarm_def;
import com.mycompany.ksandalo_clock_1.alarm.Alarm_sec;
import com.mycompany.ksandalo_clock_1.clock.CArrow;
import com.mycompany.ksandalo_clock_1.clock.IClock;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author sanda
 */
public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;
    
    private HibernateSessionFactory() {}
    
    public static SessionFactory getSessionFactory(IClock clock) {
        Class cls;
        if (clock.getValue(CArrow.SECOND) == -1) {
            cls = Alarm_def.class;
        }
        else {
            cls = Alarm_sec.class;
        }
        if (sessionFactory == null) {
            try {
                Configuration config = new Configuration().configure();
                config.addAnnotatedClass(cls);
                StandardServiceRegistryBuilder builder =
                        new StandardServiceRegistryBuilder().
                                applySettings(config.getProperties());
                sessionFactory = config.buildSessionFactory(builder.build());
                System.out.println("Session created");
            }
            catch(Exception ex) {
                System.out.println(ex);
            }
        }
        return sessionFactory;
    }
}
