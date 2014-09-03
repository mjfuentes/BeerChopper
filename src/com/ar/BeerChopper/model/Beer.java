
package com.ar.BeerChopper.model;

import com.google.gson.annotations.SerializedName;

public class Beer {

    @SerializedName("beerId")
    private Long beerId;

    @SerializedName("name")
    private String name;

    @SerializedName("erased")
    private boolean erased = false;

    public Beer(){}

    public Beer(String aName){
        this.setName(aName);
    }

    public Beer(Long aBeerID, String aName){
        this.setBeerId(aBeerID);
        this.setName(aName);
    }

    public Long getBeerId() {
        return beerId;
    }
    public void setBeerId(Long beerId) {
        this.beerId = beerId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isErased() {
        return erased;
    }
    public void setErased(boolean erased) {
        this.erased = erased;
    }
    @Override
    public String toString(){
        return this.getName();
    }

}