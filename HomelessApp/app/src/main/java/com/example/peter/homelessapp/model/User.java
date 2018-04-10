package com.example.peter.homelessapp.model;

import java.util.HashMap;
import java.util.Map;

/**
 * User class
 */
public abstract class User {
    private static final Map<String, String> _passwordMap = new HashMap<>();
    private static final Map<String, User> _userMap = new HashMap<>();
    private String _name;
    private String _username;
    // The name of the current shelter
    private String _currentShelter = "";

    /**
     * Default user constructor
     */
    User(){
    }

    /**
     * Creates instance of User class
     * @param name String name of user
     * @param username String user name of user
     */
    User(String name, String username) {
        _name = name;
        _username = username;
    }

    /**
     * Gets name of user
     * @return String name of user
     */
    String getName() {
        return _name;
    }

    /**
     * Gets user name of user
     * @return String user name
     */
    public String getUsername() {
        return _username;
    }

    /**
     * Sets name of user
     * @param name String name to set
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * Sets user name of user
     * @param name String user name to set
     */
    public void setUsername(String name) {
        _username = name;
    }

    /**
     * Checks if a user name is in our user map or not
     * @param userName String user name to check
     * @return True if the user is in the password map, false otherwise
     */
    public static boolean checkUsername(String userName) {
        return !(_passwordMap.containsKey(userName));
    }

//    /**
//     * Checks if a user name and password match the records
//     * @param username String user name to check
//     * @param pass String password to check
//     * @return True if the user name and password match, false otherwise
//     */
//    public static boolean checkLogin(String username, String pass) {
//        if (_passwordMap.containsKey(username)) {
//            String password = _passwordMap.get(username);
//            return password.equals(pass);
//        }
//        return false;
//    }

    /**
     * Returns an instance of the User class corresponding to a user name
     * @param userName User name to find the user of
     * @return instance of the User class with matching user name
     */
    public static User getUser(String userName) {
        return _userMap.get(userName);
    }

    /**
     * Gets the shelter the user is currently staying at
     * @return instance of Shelter class the user is staying at
     */
    public String getCurrentShelter() { return _currentShelter; }

    /**
     * Sets the User's current shelter to null.
     */
    void checkOut() {
        _currentShelter = "";
    }

    public boolean checkIn(String currentShelter) {
        if (!"".equals(_currentShelter)) { return false; }
        _currentShelter = currentShelter;
        return true;
    }

    /**
     * Adds user to static maps
     * @param in Instance of User class to add
     * @param password String password to add to map
     */
    static void addUser(User in, String password) {
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
        if ((this._username == null) || (that._username == null)) {return false;}
        return this._username.equals(that._username);
    }
}