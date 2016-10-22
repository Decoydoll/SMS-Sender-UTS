package com.miftah.smsuts.model;

import java.io.Serializable;

/**
 * Created by miftah.fathudin on 10/22/2016.
 */
public class ContactData implements Serializable{

    private String number;
    private String displayName;

    public ContactData(String number, String displayName){
        this.number = number;
        this.displayName = displayName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
