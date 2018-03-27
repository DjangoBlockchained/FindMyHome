package com.example.peter.homelessapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Peter on 2/16/18.
 */

public class Administer extends User implements Parcelable {

    public Administer(String name, String username, String password) {
        super(name, username);
        User.addUser(this, password);
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(getName());
        out.writeString(getUsername());
    }

    public static final Parcelable.Creator<Administer> CREATOR = new Parcelable.Creator<Administer>() {
        public Administer createFromParcel(Parcel in) {
            return new Administer(in);
        }

        public Administer[] newArray(int size) {
            return new Administer[size];
        }
    };

    private Administer(Parcel in) {
        super(in.readString(), in.readString());
    }
}