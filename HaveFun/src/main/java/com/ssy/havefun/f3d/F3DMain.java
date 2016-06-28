/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssy.havefun.f3d;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jsun
 */
public class F3DMain {
    private static SessionFactory sessionFactory;
    private static F3DDao f3DDao = null;
    private static F3DService service = null;
    public static void main(String [] args) throws IOException{
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//        sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        f3DDao = new F3DDaoImpl(session);
//        service = new F3DServiceImpl();
//        List<F3DEntity> list = importData.doImport();
//        for(F3DEntity f3d:list){
//            session.save(f3d);
//        }
//        List<F3DEntity> list = f3DDao.findAll();
//        List<F3DEntity> list = f3DDao.findAllInRange("20150101", "20151231");
//        List<F3DEntity> list = f3DDao.findAllInRange("20160101", "20161231");
//        Map<Integer,Integer> map = service.findFreqOfEveryNumber(list);
//        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {  
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());    
//        }
//        System.out.println("---------------------------------------------------------->");
//        Map<String,Integer> freqForEveryStageIgnorOrder = service.findFreqOfEveryStageIgnorOrder(list);
//        for (Map.Entry<String, Integer> entry : freqForEveryStageIgnorOrder.entrySet()) {  
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());    
//        }
//        System.out.println("has appeared: "+freqForEveryStageIgnorOrder.size());
//        System.out.println("------------------------------------------------------------>>");
//        
//        List<String> hasNotAppeared = service.findConbinationHasNotAppeared(service.getAllConbination(), freqForEveryStageIgnorOrder);
//        for(String s:hasNotAppeared){
//            System.out.println(s);
//        }
//        System.out.println("has not appeared: "+hasNotAppeared.size());
//        System.out.println("total size: "+service.getAllConbination().size());
//        System.out.println("----------------------------------------------------------->>>");
//        session.getTransaction().commit();
//        session.close();
    }
}
