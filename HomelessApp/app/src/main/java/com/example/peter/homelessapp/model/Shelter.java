package com.example.peter.homelessapp.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Shelter class
 */
@SuppressWarnings("ChainedMethodCall")
public class Shelter {
    private static final DatabaseReference shelterRef = getDatabaseReference();

    private String unique_id;
    private String name;
    private Integer capacity = 0;
    private String restrictions;
    private Double longitude;
    private Double latitude;
    private String address;
    private String special_notes;
    private String number;
    /**
     * Maps user names to the number of beds they occupy.
     */
    private HashMap<String, Integer> checkedInUsers = new HashMap<>();
    private int occupiedBeds = 0;

    /**
     * Default constructor required for DataSnapshot.getValue(Shelter.class);
     */
    public Shelter() {
    }

    /** Creates new instance of Shelter class
     * @param id unique id of shelter
     * @param n name of shelter
     * @param c capacity of shelter
     * @param r shelter restrictions
     * @param longitude longitude of shelter
     * @param latitude latitude of shelter
     * @param address address of shelter
     * @param notes special notes
     * @param phone phone number
     */
    @SuppressWarnings("ConstructorWithTooManyParameters")
    public Shelter(String id, String n, Integer c, String r, Double longitude, Double latitude,
                   String address, String notes, String phone) {
        this.unique_id = id;
        this.capacity = c;
        this.name = n;
        this.restrictions = r;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.special_notes = notes;
        this.number = phone;
        writeNewShelter();
    }

    /**
     * Writes this shelter to the database
     */
    private void writeNewShelter() {
        DatabaseReference shelterReference = shelterRef.child("shelters");
        DatabaseReference specificShelter = shelterReference.child(name);
        DatabaseReference dataName = specificShelter.child("name");
        dataName.setValue(name);
        DatabaseReference dataID = specificShelter.child("unique_id");
        dataID.setValue(unique_id);
        DatabaseReference dataCapacity = specificShelter.child("capacity");
        dataCapacity.setValue(capacity);
        DatabaseReference dataRestrict = specificShelter.child("restrictions");
        dataRestrict.setValue(restrictions);
        DatabaseReference dataLongitude = specificShelter.child("longitude");
        dataLongitude.setValue(longitude);
        DatabaseReference dataLatitude = specificShelter.child("latitude");
        dataLatitude.setValue(latitude);
        DatabaseReference dataAddress = specificShelter.child("address");
        dataAddress.setValue(address);
        DatabaseReference dataSpecial = specificShelter.child("special notes");
        dataSpecial.setValue(special_notes);
        DatabaseReference dataNumber = specificShelter.child("number");
        dataNumber.setValue(number);
        DatabaseReference dataChecked = specificShelter.child("checkedInUsers");
        dataChecked.setValue(checkedInUsers);
        DatabaseReference dataOccupied = specificShelter.child("occupiedBeds");
        dataOccupied.setValue(occupiedBeds);
    }

    /**
     * @return the unique id of this shelter
     */
    public String getUnique_id() {
        return unique_id;
    }

    /**
     * @return the name of this shelter
     */
    public String getName() {
        return name;
    }

    /**
     * @return the capacity of this shelter
     */
    public Integer getCapacity() {
        return capacity;
    }


    /**
     * @return the restrictions of this shelter
     */
    public String getRestrictions() {
        return restrictions;
    }

    /**
     * @return the longitude of this shelter
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @return the latitude of this shelter
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @return the address of this shelter
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the special notes of this shelter
     */
    public String getSpecial_notes() {
        return special_notes;
    }

    /**
     * @return the phone number of this shelter
     */
    public String getNumber() {
        return number;
    }

    /**
     * @return the map of checked in users of this shelter
     */
    public HashMap<String, Integer> getCheckedInUsers() { return checkedInUsers; }

    /**
     * @return the number of occupied beds in this shelter
     */
    public int getOccupiedBeds() { return occupiedBeds; }

    /**
     *
     * @param unique_id sets the shelter's id to the input value
     */
    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    /**
     *
     * @param name Sets name to input name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param capacity Sets capacity of shelter
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    /**
     *
     * @param restrictions Sets restrictions of shelters
     */
    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    /**
     *
     * @param longitude sets longitude of shelter
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @param latitude sets latitude of shelter
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @param address sets the address of shelter
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Sets the special notes of shelter
     *
     * @param special_notes String of special notes to set
     */
    public void setSpecial_notes(String special_notes) {
        this.special_notes = special_notes;
    }

    /**
     * Sets the phone number of shelter
     * @param number String of phone number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Sets the map of checked in users at the shelter
     * @param hash Hash map to set
     */
    public void setCheckedInUsers(HashMap<String, Integer> hash) {
        //noinspection AssignmentToCollectionOrArrayFieldFromParameter
        checkedInUsers = hash;
    }

    /**
     * Sets the number of occupied beds
     * @param i  Integer number of occupied beds
     */
    public void setOccupiedBeds(int i) {
        occupiedBeds = i;
    }

    /**
     *
     * @return number of vacancies (capacity - occupied)
     */
    public int getNumberOfVacancies() {
        return capacity - occupiedBeds;
    }

    /**
     * Returns if the shelter has a certain number of beds available or not
     * @param beds number of desired vacant beds
     * @return true if there are enough beds vacant, false otherwise
     */
    public boolean hasVacancy(int beds) {
        return getNumberOfVacancies() >= beds;
    }

    /**
     * Checks a user into the shelter, taking a certain number of beds
     * @param user The user to check into the shelter
     * @param beds The number of beds the user wants to check in
     */
    public void checkIn(User user, int beds) {
        if ((!hasVacancy(beds)) || !user.checkIn(getName())) {
            return;
        }
        String username = user.getUsername();
        checkedInUsers.put(username, beds);
        occupiedBeds += beds;
        try {
            FirebaseDatabase.getInstance().getReference().child("users").child(username)
                    .child("currentShelter").setValue(name);
            shelterRef.child("shelters").child(name).child("checkedInUsers").setValue(checkedInUsers);
            shelterRef.child("shelters").child(name).child("occupiedBeds").setValue(occupiedBeds);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Attempts to check out a user from the shelter
     * @param user The user attempting to check out from the shelter
     */
    public boolean checkOut(User user) {
        String username = user.getUsername();
        if (checkedInUsers.get(username) == null) {
            return false;
        }
        int beds = checkedInUsers.get(username);
        checkedInUsers.remove(username);
        occupiedBeds -= beds;
        user.checkOut();
        try {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                    .child("users");
            userRef.child("username").child("currentShelter").setValue(null);
            shelterRef.child("shelters").child(name).child("checkedInUsers")
                    .setValue(checkedInUsers);
            shelterRef.child("shelters").child(name).child("occupiedBeds")
                    .setValue(occupiedBeds);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * @param s A description of the shelter's capacity.
     * @return An integer representing the capacity. An empty description will cause the method to
     *         return 0. If the capacity is in the format "a for families, b singles", the value of
     *         b is returned as an integer.
     */
    public static Integer parseCapacity(String s) {
        if (s == null) {
            throw new NullPointerException("parseCapacity method can't parse a null" +
                    " capacity String\n");
        }
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
            try {
                capacity = Integer.parseInt(tokens[0]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Input capacity didn't have integer format");
            }
        }
        //android.util.Log.d("tag", "parseCapacity: " + s + ", " + capacity);
        return capacity;
    }

    private static DatabaseReference getDatabaseReference() {
        try {
            FirebaseDatabase fire =  FirebaseDatabase.getInstance();
            return fire.getReference();
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     * Checks currentUser out from currentShelter, or, if currentShelter has the same name as
     * shelter, checks currentUser out from shelter.
     * @param shelter The shelter where the user is trying to check in.
     * @param currentShelter The shelter where the user is currently checked in.
     * @param currentUser The current user.
     */
    public static void checkOut(Shelter shelter, Shelter currentShelter, User currentUser) {
        if (currentShelter.getName().equals(shelter.getName())) {
            shelter.checkOut(currentUser);
        } else {
            currentShelter.checkOut(currentUser);
        }
    }
}
