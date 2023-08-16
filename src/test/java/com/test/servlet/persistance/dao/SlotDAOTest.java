package com.test.servlet.persistance.dao;

import com.test.servlet.entity.Slot;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.List;

public class SlotDAOTest extends TestCase {

    public void testFindSlotConsultant() {
        SlotDAO slotDAO = new SlotDAO();
        List<Slot> listOfSlots = slotDAO.findSlotConsultant("john doe");

      /*  for(int i = 0; i < listOfSlots.size()){
            System.out.println(listOfSlots.get(i));
        }*/

        for(Slot slot: listOfSlots){
            System.out.println(slot.getEndTime());
        }

        Assert.assertEquals(2,listOfSlots.size());
        Assert.assertNotNull(listOfSlots);
    }

}