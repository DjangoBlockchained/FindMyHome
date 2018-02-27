package com.example.peter.homelessapp.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sanjanakadiveti on 2/26/18.
 */

public class Shelter {
    private static Map<Integer, Shelter> shelterMap = new HashMap<>();
    private int unique_id;
    private String name;
    private String capacity;
    private String restrictions;
    private String longitude;
    private String latitude;
    private String address;
    private String special_notes;
    private String number;
    public Shelter(int id, String n, String c, String r, String longi, String lati, String addr, String notes, String phone) {
        unique_id = id;
        capacity = c;
        name = n;
        restrictions = r;
        longitude = longi;
        latitude = lati;
        address = addr;
        special_notes = notes;
        number = phone;
        shelterMap.put(id, this);
    }
    public int getUniqueID() {
        return unique_id;
    }
    public String getName() {
        return name;
    }
    public static Shelter getShelter(int id) {
        return shelterMap.get(id);
    }
    public String toString() {
        String ret = "";
        ret = "Name: " + name + "\n"
                + "Capacity: " + capacity + "\n"
                + "Restrictions: " + restrictions + "\n"
                + "Location " + longitude + ", " + latitude + "\n"
                + "Address: " + address + "\n"
                + "Special Notes: " + special_notes + "\n"
                + "Number: " + number + "\n";
        return ret;

    }
}
