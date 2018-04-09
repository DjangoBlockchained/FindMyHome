package com.example.peter.homelessapp.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter on 2/16/18.
 */

public abstract class User {
    private static Map<String, String> _passwordMap = new HashMap<>();
    private static Map<String, User> _userMap = new HashMap<>();
    private String _name;
    private String _username;
    // The name of the current shelter
    private String _currentShelter;
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference dbRef = database.getReference().child("users");
    public User(){

    }
    public User(String name, String username) {
        _name = name;
        _username = username;
    }

    public String getName() {
        return _name;
    }
    public String getUsername() {
        return _username;
    }
    public void setName(String name) {
        _name = name;
    }
    public void setUsername(String name) {
        _username = name;
    }
    public static boolean checkUsername(String uName) {
        return !(_passwordMap.containsKey(uName));
    }
    public static boolean checkLogin(String username, String pass) {
        if (_passwordMap.containsKey(username) && (_passwordMap.get(username).equals(pass))) {
            return true;
        } else {
            return false;
        }
    }
    public static User getUser(String uName) {
        return _userMap.get(uName);
    }

    public String getCurrentShelter() { return _currentShelter; }
    protected void setCurrentShelter(String currentShelter) {
        _currentShelter = currentShelter;
    }

    public static void addUser(User in, String password) {
        _userMap.put(in.getUsername(), in);
        _passwordMap.put(in.getUsername(), password);
    }

    @Override
    public int hashCode() {
        return _username.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { return true; }
        if (null == other) { return false; }
        if (!(other instanceof User)) { return false; }
        User that = (User) other;
        return this._username.equals(that._username);
    }
}