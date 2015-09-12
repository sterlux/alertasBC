package com.parse.starter;

/**
 * Created by Sterlux on 9/12/2015.
 */
public class City {
    private String Name;
    private String ID;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return getName();
    }
}
