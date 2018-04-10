package com.example.peter.homelessapp;

import com.example.peter.homelessapp.model.HomelessUser;
import com.example.peter.homelessapp.model.Shelter;
import com.example.peter.homelessapp.model.User;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Sanjana Kadiveti's J-Unit Test
 * Class: User
 * Method: Equals
 */

public class UserUnitTest {
    @Test
    public void testEquals() {
        User a = new HomelessUser();
        User b = new HomelessUser();

        //if both usernames are null, return false
        assertFalse(a.equals(b));

        //different names, null usernames - still false (obv)
        a.setName("Sanjana");
        b.setName("Peter");
        assertFalse((a.equals(b)));

        //same names, null usernames - still false
        b.setName("Sanjana");
        //a.getName() = sanjana
        assertFalse(a.equals(b));

        //same names, one username null - false
        b.setUsername("pzupke3");
        //a.getUsername() = NULL
        assertFalse((a.equals(b)));

        //same names, different non-null usernames - false
        a.setUsername("skadivet6");
        //b.getUsername() = pzupke3
        assertFalse((a.equals(b)));

        //same names, same non-null usernames - true!
        a.setUsername("pzupke3");
        //b.getUsername() = pzupke3
        assertTrue(a.equals(b));

        //different names, same non-null usernames - true!
        b.setName("Peter");
        assertTrue((a.equals(b)));

        //same objects, true!
        b = a;
        assertTrue(a.equals(b));

        //one of the objects is null - false
        b = null;
        assertFalse((a.equals(b)));

        //one of the objects not an instance of user - false
        Shelter x = new Shelter();
        assertFalse((a.equals(x)));
    }

}
