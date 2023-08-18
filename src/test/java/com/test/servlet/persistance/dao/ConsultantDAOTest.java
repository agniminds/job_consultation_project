package com.test.servlet.persistance.dao;

import com.test.servlet.entity.Consultant;
import org.junit.Test;

// import static org.junit.jupiter.api.Assertions.*;

public class ConsultantDAOTest {

    @Test
    public void testGetUser(){
        ConsultantDAO consultantDAO = new ConsultantDAO();
        Consultant consultant = consultantDAO.getUser(1);

        System.out.println(consultant.getName());

    }


}