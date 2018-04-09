package com.example.peter.homelessapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Peter on 2/25/18.
 */

public class HomelessUser extends User implements Parcelable {
    // private String _name;
    // private String _username;
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference dbRef = database.getReference().child("users");
    public HomelessUser() {
        super();
    }
    public HomelessUser(String name, String username, String password) {
        super(name, username);
        User.addUser(this, password);
        dbRef.child(username).child("name").setValue(name);
        dbRef.child(username).child("username").setValue(username);
        dbRef.child(username).child("password").setValue(password);
        dbRef.child(username).child("type").setValue("homeless");
        dbRef.child(username).child("currentShelter").setValue(null);
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

    public static final Parcelable.Creator<HomelessUser> CREATOR = new Parcelable.Creator<HomelessUser>() {
        public HomelessUser createFromParcel(Parcel in) {
            return new HomelessUser(in);
        }

        public HomelessUser[] newArray(int size) {
            return new HomelessUser[size];
        }
    };

    private HomelessUser(Parcel in) {
        super(in.readString(), in.readString());
    }
}
