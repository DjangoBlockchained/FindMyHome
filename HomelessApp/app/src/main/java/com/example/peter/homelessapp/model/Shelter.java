package com.example.peter.homelessapp.model;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Created by sanjanakadiveti on 2/26/18.
 */

public class Shelter {
    private static FirebaseDatabase Shelterdatabase = FirebaseDatabase.getInstance();
    private static DatabaseReference shelterRef = Shelterdatabase.getReference();

    private String unique_id;
    private String name;
    private Integer capacity;
    private String restrictions;
    private Double longitude;
    private Double latitude;
    private String address;
    private String special_notes;
    private String number;
    /**
     * Maps users to the number of beds they occupy.
     */
    private HashMap<User, Integer> checkedInUsers;
    private int occupiedBeds;
    public Shelter() {
        // Default constructor required for DataSnapshot.getValue(Shelter.class);
    }
    public Shelter(String id, String n, Integer c, String r, Double longi, Double lati, String addr, String notes, String phone) {
        unique_id = id;
        capacity = c;
        name = n;
        restrictions = r;
        longitude = longi;
        latitude = lati;
        address = addr;
        special_notes = notes;
        number = phone;
        writeNewShelter();
    }
    public void writeNewShelter() {
        shelterRef.child("shelters").child(name).child("name").setValue(name);
        shelterRef.child("shelters").child(name).child("unique_id").setValue(unique_id);
        shelterRef.child("shelters").child(name).child("capacity").setValue(capacity);
        shelterRef.child("shelters").child(name).child("restrictions").setValue(restrictions);
        shelterRef.child("shelters").child(name).child("longitute").setValue(longitude);
        shelterRef.child("shelters").child(name).child("latitude").setValue(latitude);
        shelterRef.child("shelters").child(name).child("address").setValue(address);
        shelterRef.child("shelters").child(name).child("special notes").setValue(special_notes);
        shelterRef.child("shelters").child(name).child("number").setValue(number);
        shelterRef.child("shelters").child(name).child("checkedInUsers").setValue(checkedInUsers);
        shelterRef.child("shelters").child(name).child("occupiedBeds").setValue(occupiedBeds);
    }

    public String getUnique_id() {
        return unique_id;
    }

    public String getName() {
        return name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
    }

    public String getSpecial_notes() {
        return special_notes;
    }

    public String getNumber() {
        return number;
    }


    public int getNumberOfVacancies() {
        return capacity - occupiedBeds;
    }

    public boolean hasVacancy(int beds) {
        return getNumberOfVacancies() >= beds;
    }

    public boolean hasVacancy() {
        return hasVacancy(1);
    }

    public boolean checkIn(User user, int beds) {
        if (!hasVacancy(beds) || user.getCurrentShelter() != null) {
            return false;
        }
        checkedInUsers.put(user, beds);
        occupiedBeds += beds;
        user.setCurrentShelter(this);
        return true;
    }

    public boolean checkOut(User user) {
        if (checkedInUsers.get(user) == null) {
            return false;
        }
        int beds = checkedInUsers.get(user);
        checkedInUsers.remove(user);
        occupiedBeds -= beds;
        user.setCurrentShelter(null);
        return true;
    }

    /**
     * @param s A description of the shelter's capacity.
     * @return An integer representing the capacity. An empty description will cause the method to
     *         return 0. If the capacity is in the format "a for families, b singles", the value of
     *         b is returned as an integer.
     */
    public static Integer parseCapacity(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        Integer capacity;
        try {
            capacity = Integer.parseInt(s);
        } catch (Exception ex) {
            Pattern pattern = Pattern.compile("\\d+ singles");
            Matcher matcher = pattern.matcher(s);
            String substring;
            if (matcher.find()) {
                substring = matcher.group();
            } else {
                substring = s;
            }
            String[] tokens = substring.split(" ");
            capacity = Integer.parseInt(tokens[0]);
        }
        return capacity;
    }
}
