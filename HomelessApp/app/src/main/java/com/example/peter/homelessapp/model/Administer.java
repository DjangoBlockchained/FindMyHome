package com.example.peter.homelessapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Peter on 2/16/18.
 */

public class Administer extends User implements Parcelable {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference dbRef = database.getReference().child("users");

    public Administer(String name, String username, String password) {
        super(name, username);
        User.addUser(this, password);
        dbRef.child(username).child("name").setValue(name);
        dbRef.child(username).child("password").setValue(password);
        dbRef.child(username).child("type").setValue("administer");
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