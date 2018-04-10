package com.example.peter.homelessapp;

import com.example.peter.homelessapp.controllers.MapsActivity;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit tests for MapsActivity.
 */
public class MapsUnitTest {

    @Test
    public void testGenderMatches() {
        String g0 = "Any";

        Map s0 = makeShelter("Men");
        assertTrue(MapsActivity.genderMatches(s0, g0));

        Map s1 = makeShelter("Women");
        assertTrue(MapsActivity.genderMatches(s1, g0));

        Map s2 = makeShelter("Anyone");
        assertTrue(MapsActivity.genderMatches(s2, g0));

        Map s3 = makeShelter("");
        assertFalse(MapsActivity.genderMatches(s3, g0));


        String g1 = "";

        Map s4 = makeShelter("");
        assertTrue(MapsActivity.genderMatches(s4, g1));

        Map s5 = makeShelter("Women");
        assertTrue(MapsActivity.genderMatches(s5, g1));


        String g2 = "Women";

        Map s6 = makeShelter("Women");
        assertTrue(MapsActivity.genderMatches(s6, g2));

        Map s7 = makeShelter("");
        assertFalse(MapsActivity.genderMatches(s7, g2));

        Map s8 = makeShelter("Anyone");
        assertTrue(MapsActivity.genderMatches(s8, g2));
    }

    private Map makeShelter(String restrictions) {
        HashMap<String, Object> shelter = new HashMap<>();
        shelter.put("restrictions", restrictions);
        return shelter;
    }

}



