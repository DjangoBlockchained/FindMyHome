package com.example.peter.homelessapp;

import com.example.peter.homelessapp.model.HomelessUser;
import com.example.peter.homelessapp.model.Shelter;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Valentine Wilson on 4/9/18.
 */
public class checkOutTest {
    @Test
    public void test() throws Exception {
        HomelessUser test = new HomelessUser();
        Shelter s  = new Shelter();
        s.setCapacity(1);
        s.checkIn(test,1);
        assertTrue(s.checkOut(test));
        assertFalse(s.checkOut(test));
    }
}
