package com.test.servlet.persistance.dao;

import com.test.servlet.entity.Applicant;
import com.test.servlet.entity.Consultant;
import com.test.servlet.persistance.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ApplicantDAO {

    public List<Applicant> getAll(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.createQuery("SELECT a FROM Applicant a").list();

    }

    public void saveApplicant(Applicant applicant) {
        Transaction transaction = null;
        Session session ;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.save(applicant);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateApplicant(Applicant applicant) {
        Transaction transaction = null;
        Session session ;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.update(applicant);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteApplicant(int id) {

        Transaction transaction = null;
        Session session ;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();

            // Delete a user object
            Applicant applicant = (Applicant) session.get(Applicant.class, id);
            if (applicant != null) {
                session.delete(applicant);
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

    public Applicant findApplicant(String username, String password) {

        Transaction transaction = null;
        Applicant applicant = null;
        Session session ;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            // start a transaction
            transaction = session.beginTransaction();
            String hql = "FROM Applicant E WHERE E.username = '"+username +"' AND E.password  = '" +password+"'";
            Query query = session.createQuery(hql);
            List results = query.list();
            // get an user object
            if(results!=null && !results.isEmpty()) {
                applicant = (Applicant) results.get(0);
                transaction.commit();
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return applicant;
    }



}
