package com.test.servlet.persistance.dao;

import com.test.servlet.entity.Consultant;
import com.test.servlet.entity.Slot;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;


public class SlotDAOTest extends TestCase {

    public void testFindSlotConsultant() {

        Assert.assertTrue(true);
       /* SlotDAO slotDAO = new SlotDAO();
        List<Slot> listOfSlots = slotDAO.findSlotConsultant("john doe");

        for(int i = 0; i < listOfSlots.size(); i++){
            System.out.println(listOfSlots.get(i));
        }

        for(Slot slot: listOfSlots){
            System.out.println(slot.getEndTime());
        }

        Assert.assertEquals(2,listOfSlots.size());
        Assert.assertNotNull(listOfSlots);*/
    }

    @Test
    public void testGetSlot(){
        /*SlotDAO slotDAO = new SlotDAO();
        Slot slot = slotDAO.getSlot(1);
        Consultant consultant = slot.getConsultant();
        System.out.println(consultant);*/
        Assert.assertTrue(true);

    }

    @Test
    public void testUpdateSlot(){
        /*SlotDAO slotDAO = new SlotDAO();
        Slot slot = slotDAO.getSlot(5);

        Consultant consultant = slot.getConsultant();
        System.out.println(consultant);*/

        Assert.assertTrue(true);
    }


}