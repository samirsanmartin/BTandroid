package com.example.android.lightcontrol;

import java.io.Serializable;

/**
 * Created by samirsanmartin on 6/12/16.
 */
public class PairedDevices implements Serializable {
    private String name, address;


    public PairedDevices(String name, String address) {
        this.name = name;
        this.address = address;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
