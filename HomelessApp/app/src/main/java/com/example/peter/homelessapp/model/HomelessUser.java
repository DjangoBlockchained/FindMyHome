package com.example.peter.homelessapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Peter on 2/25/18.
 */

public class HomelessUser extends User implements Parcelable {
    private String _name;
    private String _username;
    public HomelessUser(String name, String username, String password) {
        _name = name;
        _username = username;
        User.addUser(this, password);
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
    public void setUserName(String name) {
        _username = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(_name);
        out.writeString(_username);
    }

    public static final Parcelable.Creator<HomelessUser> CREATOR = new Parcelable.Creator<HomelessUser>() {
        public HomelessUser createFromParcel(Parcel in) {
            return new HomelessUser(in);
        }

        public HomelessUser[] newArray(int size) {
            return new HomelessUser[size];
        }
    };

    private HomelessUser(Parcel in) {
        _name = in.readString();
        _username = in.readString();
    }
}