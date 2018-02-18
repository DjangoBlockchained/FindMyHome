package com.example.peter.homelessapp.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter on 2/16/18.
 */

public class User {
    private static Map<String, String> _userMap = new HashMap<>();
    private String _name;
    private String _username;
    public User(String name, String username, String password) {
        _name = name;
        _username = username;
        _userMap.put(username, password);
    }
    public String getName() {
        return _name;
    }
    public String getUsername() {
        return _username;
    }
    public static boolean checkUsername(String uname) {
        return !(_userMap.containsKey(uname));
    }
    public static boolean checkLogin(String name, String pass) {
        if (_userMap.containsKey(name) && (_userMap.get(name).equals(pass))) {
            return true;
        } else {
            return false;
        }
    }
}
