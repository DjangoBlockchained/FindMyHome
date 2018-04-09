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
     * Maps usernames to the number of beds they occupy.
     */
    private HashMap<String, Integer> checkedInUsers = new HashMap<>();
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

    public HashMap<String, Integer> getCheckedInUsers() { return checkedInUsers; }

    public int getOccupiedBeds() { return occupiedBeds; }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSpecial_notes(String special_notes) {
        this.special_notes = special_notes;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCheckedInUsers(HashMap<String, Integer> hash) {
        checkedInUsers = hash;
    }
    public void setOccupiedBeds(int i) {
        occupiedBeds = i;
    }
    public int getNumberOfVacancies() {
        android.util.Log.d("tag", "" + capacity);
        android.util.Log.d("tag", "" + occupiedBeds);
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
        checkedInUsers.put(user.getUsername(), beds);
        occupiedBeds += beds;
        user.setCurrentShelter(this.getName());
        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUsername()).child("currentShelter").setValue(name);
        shelterRef.child("shelters").child(name).child("checkedInUsers").setValue(checkedInUsers);
        shelterRef.child("shelters").child(name).child("occupiedBeds").setValue(occupiedBeds);
        return true;
    }

    public boolean checkOut(User user) {
        String username = user.getUsername();
        if (checkedInUsers.get(username) == null) {
            return false;
        }
        int beds = checkedInUsers.get(username);
        checkedInUsers.remove(username);
        occupiedBeds -= beds;
        user.setCurrentShelter(null);
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
        userRef.child("username").child("currentShelter").setValue(null);
        shelterRef.child("shelters").child(name).child("checkedInUsers").setValue(checkedInUsers);
        shelterRef.child("shelters").child(name).child("occupiedBeds").setValue(occupiedBeds);
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
        Integer capacity = 0;
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
        android.util.Log.d("tag", "parseCapacity: " + s + ", " + capacity);
        return capacity;
    }
}
