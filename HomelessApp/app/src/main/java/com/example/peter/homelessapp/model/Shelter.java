package com.example.peter.homelessapp.model;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by sanjanakadiveti on 2/26/18.
 */

public class Shelter {
    private static FirebaseDatabase Shelterdatabase = FirebaseDatabase.getInstance();
    private static DatabaseReference shelterRef = Shelterdatabase.getReference();

    private String unique_id;
    private String name;
    private String capacity;
    private String restrictions;
    private String longitude;
    private String latitude;
    private String address;
    private String special_notes;
    private String number;
    public Shelter(String id, String n, String c, String r, String longi, String lati, String addr, String notes, String phone) {
        unique_id = id;
        capacity = c;
        name = n;
        restrictions = r;
        longitude = longi;
        latitude = lati;
        address = addr;
        special_notes = notes;
        number = phone;
        writeNewUser();
    }
    public void writeNewUser() {
        shelterRef.child("shelters").child(name).child("name").setValue(name);
        shelterRef.child("shelters").child(name).child("unique_id").setValue(unique_id);
        shelterRef.child("shelters").child(name).child("capacity").setValue(capacity);
        shelterRef.child("shelters").child(name).child("restrictions").setValue(restrictions);
        shelterRef.child("shelters").child(name).child("longitute").setValue(longitude);
        shelterRef.child("shelters").child(name).child("latitude").setValue(latitude);
        shelterRef.child("shelters").child(name).child("address").setValue(address);
        shelterRef.child("shelters").child(name).child("special notes").setValue(special_notes);
        shelterRef.child("shelters").child(name).child("number").setValue(number);
    }
}
