package com.example.peter.homelessapp.model;

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
    private Shelter _currentShelter;

    public String getName() {
        return _name;
    }
    public String getUsername() {
        return _username;
    }
    public void setName(String name) {
        _name = name;
    }
    public void setUserName(String name) {
        _username = name;
    }
    public static boolean checkUsername(String uname) {
        return !(_passwordMap.containsKey(uname));
    }
    public static boolean checkLogin(String username, String pass) {
        if (_passwordMap.containsKey(username) && (_passwordMap.get(username).equals(pass))) {
            return true;
        } else {
            return false;
        }
    }
    public static User getUser(String uname) {
        return _userMap.get(uname);
    }

    public Shelter getCurrentShelter() { return _currentShelter; }
    protected void setCurrentShelter(Shelter currentShelter) { _currentShelter = currentShelter; }

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
