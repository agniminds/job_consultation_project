package com.test.servlet.persistance.dao;

import com.test.servlet.entity.Appointment;
import com.test.servlet.entity.Slot;
import com.test.servlet.persistance.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SlotDAO {

    public void saveSlot(Slot slot){
        Transaction transaction = null;
        Session session;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();
            // save appoinment object
            session.save(slot);
            // commit transaction
            transaction.commit();

        }catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateSlot(Slot slot) {
        System.out.println(slot.getSlotId());
        Transaction transaction = null;
        Session session ;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.update(slot);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Slot getSlotUpdateAsBooked(int id) {

        Transaction transaction = null;
        Session session;
        Slot slot = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();
            // get an user object
            slot = (Slot) session.get(Slot.class, id);

            slot.setBooked(true);
            // commit transaction
            transaction.commit();
            // session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return slot;
    }


    public void deleteSlot(int id) {

        Transaction transaction = null;
        Session session ;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();

            // Delete a user object
            Slot slot = (Slot) session.get(Slot.class, id);
            if (slot != null) {
                session.delete(slot);
                System.out.println("slot is deleted");
            }

            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Slot getSlot(int id) {

        Transaction transaction = null;
        Session session;
        Slot slot = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();
            // get an user object
            slot = (Slot) session.get(Slot.class, id);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return slot;
    }

    public List<Slot> getAllSlots() {

        Transaction transaction = null;
        List <Slot> listOfSlots = null;
        Session session ;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();
            // get an user object

            String hql = "FROM Slot";
            Query query = session.createQuery(hql);
            listOfSlots = query.list();

            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return listOfSlots;
    }

    public List<Slot> findSlotConsultant(String consultantname) {

        Transaction transaction = null;
        List <Slot> listOfSlots = null;
        Session session ;

        try{
            session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();

            // first getting the consultant id based on his/her name
            String consultantIdHql = "SELECT c.id FROM Consultant c WHERE c.name = :consultantName";
            Query consultantIdQuery = session.createQuery(consultantIdHql);
            consultantIdQuery.setParameter("consultantName",consultantname);
            Integer consultantId = (Integer) consultantIdQuery.uniqueResult();

            System.out.println("-------got consultant id based on name ----------");

            if ( consultantId != null){
                String hql = "FROM Slot E WHERE E.consultant.id = :consultantId";
                Query query = session.createQuery(hql);
                query.setParameter("consultantId", consultantId);
                listOfSlots = query.list();
                System.out.println(" ------------ got slot list based on consultant ot -------------");
            }
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return listOfSlots;
    }
}
