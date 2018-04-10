package com.example.peter.homelessapp;

import com.example.peter.homelessapp.model.Shelter;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Peter Zupke's JUnit tests
 */
public class ParseCapacityUnitTest {

    @Test(expected = NullPointerException.class)
    public void nullParameterTest() throws Exception{
        Shelter.parseCapacity(null);
    }

    @Test
    public void emptyStringTest() throws Exception{
        assertEquals((int) Shelter.parseCapacity(""), 0);
    }

    @Test(expected = RuntimeException.class)
    public void nonsenseStringTest() throws Exception{
        Shelter.parseCapacity("hello");
    }

    @Test(expected = RuntimeException.class)
    public void nonsenseStringTest2() throws Exception{
        Shelter.parseCapacity("hello for families, no singles");
    }

    @Test
    public void simpleIntegerStringsTest() throws Exception{
        assertEquals((int) Shelter.parseCapacity("0"), 0);
        assertEquals((int) Shelter.parseCapacity("1"), 1);
        assertEquals((int) Shelter.parseCapacity("15"), 15);
        assertEquals((int) Shelter.parseCapacity("112"), 112);
    }

    @Test
    public void singlesStringTest() throws Exception{
        assertEquals((int) Shelter.parseCapacity("0 singles"), 0);
        assertEquals((int) Shelter.parseCapacity("hello 17 singles"), 17);
    }

    @Test
    public void familiesAndSinglesTest() throws Exception{
        assertEquals((int) Shelter.parseCapacity("0 for families, 0 singles"), 0);
        assertEquals((int) Shelter.parseCapacity("1 for families, 3 singles"), 3);
        assertEquals((int) Shelter.parseCapacity("no for families, 7 singles"), 7);
        assertEquals((int) Shelter.parseCapacity("3 for families 13 singles"), 13);
    }
}