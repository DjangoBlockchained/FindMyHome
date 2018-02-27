package com.example.peter.homelessapp.controllers;

import android.util.Log;

import com.example.peter.homelessapp.model.Shelter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanjanakadiveti on 2/26/18.
 */

public class SimpleModel {
    public static final SimpleModel INSTANCE =  new SimpleModel();
    private List<Shelter> shelterList = new ArrayList<>();
    private SimpleModel() {
        shelterList = new ArrayList<>();
    }
    public void addShelter(Shelter item) {
        shelterList.add(item);
    }

    public List<Shelter> getShelters() {
        return shelterList;
    }
    public List<String> shelterNameList() {
        List<String> ret =  new ArrayList<>();
        for (Shelter s : getShelters()) {
            ret.add(s.getUniqueID() +"  "+ s.getName());
        }
        return ret;
    }

    public Shelter findItemById(int id) {
        for (Shelter d : shelterList) {
            if (d.getUniqueID() == id) return d;
        }
        Log.d("MYAPP", "Warning - Failed to find id: " + id);
        return null;
    }
}

