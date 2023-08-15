package com.test.servlet.persistance.dao;

import com.test.servlet.entity.Appointment;
import com.test.servlet.entity.Consultant;
import com.test.servlet.persistance.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AppoinmentDAO {

    public void saveAppoinment(Appointment appointment){
        Transaction transaction = null;
        Session session;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();
            // save appoinment object
            session.save(appointment);
            // commit transaction
            transaction.commit();

        }catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateAppoinment(Appointment appointment) {
        Transaction transaction = null;
        Session session ;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.merge(appointment);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteAppoinment(int id) {

        Transaction transaction = null;
        Session session ;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();

            // Delete a user object
            Appointment appointment = (Appointment) session.get(Appointment.class, id);
            if (appointment != null) {
                session.delete(appointment);
                System.out.println("appoinment is deleted");
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

    public Appointment getAppoinment(int id) {

        Transaction transaction = null;
        Session session;
        Appointment appoinment = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();
            // get an user object
            appoinment = (Appointment) session.get(Appointment.class, id);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return appoinment;
    }

    // appoinment date search possible ?


    public List<Appointment> findAppoinmentConsultant(String consultantname) {

        Transaction transaction = null;
        List <Appointment> listOfAppoinment = null;
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

            if ( consultantId != null){

                String hql = "FROM Appoinment E WHERE E.consultant_id = :consultantId";
                Query query = session.createQuery(hql);
                query.setParameter("consultantId", consultantId);
                listOfAppoinment = query.list();

            }
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return listOfAppoinment;
    }

    // creating the query for getting all appoinments

    public List <Appointment> getAllAppoinments() {

        Transaction transaction = null;
        List <Appointment> listOfAppoinment = null;
        Session session ;
        try{
             session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();
            // get an user object

            String hql = "FROM Appoinment";
            Query query = session.createQuery(hql);
            listOfAppoinment = query.list();

            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return listOfAppoinment;
    }







}
