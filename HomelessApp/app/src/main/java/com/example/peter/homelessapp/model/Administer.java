package com.example.peter.homelessapp.model;

//import android.os.Parcel;
//import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Administer class
 */
public class Administer extends User {
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference dbRef = database.getReference().child("users");

    /**
     * @param name String name of administer
     * @param username String user name of administer
     * @param password String password of administer
     */
    public Administer(String name, String username, String password) {
        super(name, username);
        DatabaseReference databaseReference = dbRef.child(username);
        DatabaseReference dataName = databaseReference.child("name");
        dataName.setValue(name);
        DatabaseReference dataPassword = databaseReference.child("password");
        dataPassword.setValue(password);
        DatabaseReference dataType = databaseReference.child("type");
        dataType.setValue("administer");

        addUserToMap(password);
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//    @Override
//    public void writeToParcel(Parcel out, int flags) {
//        out.writeString(getName());
//        out.writeString(getUsername());
//    }

//    public static final Parcelable.Creator<Administer> CREATOR
//            = new Parcelable.Creator<Administer>() {
//        @Override
//        public Administer createFromParcel(Parcel in) {
//            return new Administer(in);
//        }
//
//        @Override
//        public Administer[] newArray(int size) {
//            return new Administer[size];
//        }
//    };

//    private Administer(Parcel in) {
//        super(in.readString(), in.readString());
//    }
    private void addUserToMap(String password) {
        User.addUser(this, password);
    }
}