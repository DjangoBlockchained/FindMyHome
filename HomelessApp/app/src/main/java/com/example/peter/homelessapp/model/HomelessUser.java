package com.example.peter.homelessapp.model;

//import android.os.Parcel;
//import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Homeless User class
 */
public class HomelessUser extends User {
    // private String _name;
    // private String _username;
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference dbRef = database.getReference().child("users");

    /**
     * Creates Homeless User
     */
    public HomelessUser() {
        super();
    }

    /**
     * Creates instance of HomelessUser
     * @param name String name to set
     * @param username String user name to set
     * @param password String password to set
     */
    public HomelessUser(String name, String username, String password) {
        super(name, username);
//        User.addUser(this, password);
        DatabaseReference databaseReference = dbRef.child(username);
        DatabaseReference dataName = databaseReference.child("name");
        dataName.setValue(name);
        DatabaseReference dataPassword = databaseReference.child("password");
        dataPassword.setValue(password);
        DatabaseReference dataType = databaseReference.child("type");
        dataType.setValue("homeless");
        DatabaseReference dataShelter = databaseReference.child("currentShelter");
        dataShelter.setValue(null);

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

//    public static final Parcelable.Creator<HomelessUser> CREATOR
//            = new Parcelable.Creator<HomelessUser>() {
//        @Override
//        public HomelessUser createFromParcel(Parcel in) {
//            return new HomelessUser(in);
//        }
//
//        @Override
//        public HomelessUser[] newArray(int size) {
//            return new HomelessUser[size];
//        }
//    };

//    private HomelessUser(Parcel in) {
//        super(in.readString(), in.readString());
//    }

    private void addUserToMap(String password) {
        User.addUser(this, password);
    }
}
