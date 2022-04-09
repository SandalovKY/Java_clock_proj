/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ksandalo_clock_1.real_time_clock;

import com.mycompany.ksandalo_clock_1.alarm.Alarm_def;
import com.mycompany.ksandalo_clock_1.alarm.Alarm_sec;
import com.mycompany.ksandalo_clock_1.alarm.IAlarm;
import com.mycompany.ksandalo_clock_1.alarm.INamedAlarm;
import com.mycompany.ksandalo_clock_1.clock.CArrow;
import com.mycompany.ksandalo_clock_1.clock.IClock;
import com.mycompany.ksandalo_clock_1.hibernate.HibernateSessionFactory;
import com.mycompany.ksandalo_clock_1.server.Model;
import com.mycompany.ksandalo_clock_1.server.Msg;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author sanda
 */
public class RTClock {
    private IClock clock;
    private Thread workerThr;
    private AtomicBoolean isBusy;
    private Runnable run;

    public RTClock(IClock clock){
        this.clock = clock;
        this.isBusy = new AtomicBoolean(false);
        this.run = null;
        
        Session session = HibernateSessionFactory.
                getSessionFactory(clock).openSession();
        List<INamedAlarm> al_list = null;
        if (clock.getValue(CArrow.SECOND) == -1) {
            al_list = (List<INamedAlarm>)session.
                    createQuery("from Alarm_def").list();
        }
        else {
            al_list = (List<INamedAlarm>)session.
                    createQuery("from Alarm_sec").list();
        }
        this.clock.addAlarms(al_list);
        session.close();
    }

    public void start() {
        synchronized (this.isBusy) {
            if(this.run == null) {
                if (this.clock.getValue(CArrow.SECOND) >= 0)
                    this.run = new ThreadWorkerWithSeconds(this.clock, this.isBusy);
                else
                    this.run = new ThreadWorker(this.clock, this.isBusy);
                workerThr = new Thread(run);
                workerThr.start();
            }
        }
    }
    public void stop() {
        synchronized (this.isBusy) {
            if (this.run != null) {
                workerThr.interrupt();
                this.run = null;
                this.isBusy.set(false);
            }
        }
    }
    private void suspend() {
        if (this.run != null) {
            this.isBusy.set(true);
        }
    }
    private void proceed() {
        if (this.run != null) {
            this.isBusy.set(false);;
            this.isBusy.notifyAll();
            System.out.println("NOTIFY called");
        }
    }
    
    public void setPause() {
        synchronized (this.isBusy) {
            if (this.isBusy.get()) {
                this.proceed();
            }
            else {
                this.suspend();
            }
        }
    }
    public void resetTime(int hours, int minutes, int seconds) {
        synchronized (this.isBusy) {
            if (seconds == -1 || this.clock.getValue(CArrow.SECOND) == -1) {
                if (this.clock.getValue(CArrow.SECOND) != seconds)
                    System.err.println("INCOMPATIBLE TIME FORMATS");
                else {
                    this.clock.setValue(minutes, CArrow.MINUTE);
                    this.clock.setValue(hours, CArrow.HOUR);
                }
            }
            else {
                this.clock.setValue(seconds, CArrow.SECOND);
                this.clock.setValue(minutes, CArrow.MINUTE);
                this.clock.setValue(hours, CArrow.HOUR);
            }
        }
    }
    
    public void addWorker(IAlarm worker) {
        synchronized (this.isBusy) {
        this.clock.addWorker(worker);
        }
    }
    
    public boolean addAlarm(Msg mes, Model m) {
        synchronized (this.isBusy) {
            boolean res = false;
            Session session = HibernateSessionFactory.
            getSessionFactory(clock).openSession();
            Transaction tr1 = session.beginTransaction();
            if (mes.getValue(CArrow.SECOND) == -1 ||
                this.clock.getValue(CArrow.SECOND) == -1)
                {
                if (this.clock.getValue(CArrow.SECOND) != mes.getValue(CArrow.SECOND)) {
                    System.err.println("INCOMPATIBLE TIME FORMATS");
                }
                else {
                    Query<Alarm_def> q = session.createQuery("from Alarm_def where id=:id", Alarm_def.class);
                    q.setParameter("id", mes.getName().hashCode());
                    Alarm_def defAl = q.uniqueResult();
                    if (defAl == null) {
                        defAl = new Alarm_def(mes.getValue(CArrow.HOUR),
                            mes.getValue(CArrow.MINUTE),
                            m,
                            mes.getName());
                        res = this.clock.addAlarm(defAl, mes.getName());
                        session.save(defAl);
                    }
                    
                }
            }
            else {
                Query<Alarm_sec> q = session.createQuery("from Alarm_sec where id=:id", Alarm_sec.class);
                    q.setParameter("id", mes.getName().hashCode());
                    Alarm_sec secAl = q.uniqueResult();
                    if (secAl == null) {
                        secAl = new Alarm_sec(mes.getValue(CArrow.HOUR),
                            mes.getValue(CArrow.MINUTE),
                            mes.getValue(CArrow.SECOND),    
                            m,
                            mes.getName());
                        res = this.clock.addAlarm(secAl, mes.getName());
                        session.save(secAl);
                    }
            }
            tr1.commit();
            session.close();
            return res;
        }
    }
    
    public boolean deleteAlarm(Msg mes) {
        synchronized (this.isBusy) {
            boolean res = this.clock.removeAlarm(mes.getName());;
            
            Session session = HibernateSessionFactory.
            getSessionFactory(clock).openSession();
            Transaction tr1 = session.beginTransaction();
            
            if (mes.getValue(CArrow.SECOND) == -1 ||
                this.clock.getValue(CArrow.SECOND) == -1)
                {
                if (this.clock.getValue(CArrow.SECOND) != mes.getValue(CArrow.SECOND)) {
                    System.err.println("INCOMPATIBLE TIME FORMATS");
                }
                else {
                    Alarm_def defAl = new Alarm_def(mes.getValue(CArrow.HOUR),
                        mes.getValue(CArrow.MINUTE),
                        new Model(),
                        mes.getName());
                    if (res) {
                        session.delete(defAl);
                    }
                }
            }
            else {
                Alarm_def secAl = new Alarm_sec(mes.getValue(CArrow.HOUR),
                    mes.getValue(CArrow.MINUTE),
                    mes.getValue(CArrow.SECOND),
                    new Model(),
                    mes.getName());
                if (res) {
                    session.delete(secAl);
                }
            }
            tr1.commit();
            session.close();
            
            return res;
        }
    }
    
    public ArrayList<String> getWorkerNames() {
        synchronized (this.isBusy) {
            return this.clock.getWorkerNames();
        }
    }
    
    public int getValue(CArrow type) {
        return this.clock.getValue(type);
    }
}
