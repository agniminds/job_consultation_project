package com.test.servlet.persistance.dao;


import java.util.List;

import com.test.servlet.entity.Appointment;
import com.test.servlet.entity.Consultant;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.test.servlet.persistance.HibernateUtil;


public class ConsultantDAO {

    /**
     * Save User
     * @param consultant
     */
    public void saveUser(Consultant consultant) {
        Transaction transaction = null;
        Session session ;
        try{
             session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.save(consultant);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Update User
     * @param consultant
     */
    public Consultant updateUser(int consultantId, Consultant consultant) {
        Transaction transaction = null;
        Session session ;
        try{
             session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();

            // getting the existing consultant object
            Consultant existingConsultant = (Consultant) session.get(Consultant.class, consultantId);

            if (existingConsultant != null){
                existingConsultant.setName(consultant.getName());
                existingConsultant.setPassword(consultant.getPassword());
                existingConsultant.setRole(consultant.getRole());
                existingConsultant.setType(consultant.getType());
            }
            else {
                System.out.println("Couldn't find consultant");
            }
            // save the student object
            session.update(existingConsultant);
            // commit transaction
            transaction.commit();
            return existingConsultant;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Delete User
     * @param id
     */
    public void deleteUser(int id) {

        Transaction transaction = null;
        Session session ;
        try{
             session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();

            // Delete a user object
            Consultant consultant = (Consultant)session.get(Consultant.class, id);
            if (consultant != null) {
                session.delete(consultant);
                System.out.println("user is deleted");
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

    /**
     * Get User By ID
     * @param id
     * @return
     */
    public Consultant getUser(int id) {

        Transaction transaction = null;
        Consultant consultant = null;
        Session session ;
        try{
             session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();
            // get an user object
            consultant = (Consultant) session.get(Consultant.class, id);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return consultant;
    }



    /**
     * Get User By ID
     * @param username
     * @return
     */
    public Consultant findUser(String username, String password) {

        Transaction transaction = null;
        Consultant consultant = null;
        Session session ;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();
            String hql = "FROM Consultant E WHERE E.username = '"+username +"' AND E.password  = '" +password+"'";
            Query query = session.createQuery(hql);
            List results = query.list();
            // get an user object
            if(results!=null && !results.isEmpty()) {
                consultant = (Consultant) results.get(0);
                transaction.commit();
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return consultant;
    }

    public Consultant findConsultant(String name) {

        Transaction transaction = null;
        Consultant consultant = null;
        Session session ;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();
            String hql = "FROM Consultant E WHERE E.name = '"+name+"'";
            Query query = session.createQuery(hql);
            List results = query.list();
            // get an user object
            if(results!=null && !results.isEmpty()) {
                consultant = (Consultant) results.get(0);
                transaction.commit();
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return consultant;
    }



    /**
     * Get all Users
     * @return
     */

    public List <Consultant> getAllUser() {

        Transaction transaction = null;
        List <Consultant> listOfConsultants = null;
        Session session ;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();
            // get an user object

            String hql = "FROM Consultant";
            Query query = session.createQuery(hql);
            listOfConsultants = query.list();

            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return listOfConsultants;


    }}

