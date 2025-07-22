package com.flipfit.bean;

import java.util.List;

public class GymOwner extends GymUser{
    private List<String> AssociatedGyms;
    private String PAN;
    private String Aadhar;
    private String GSTIN;

    public List<String> getAssociatedGyms() {
        return AssociatedGyms;
    }

    public void setAssociatedGyms(List<String> associatedGyms) {
        AssociatedGyms = associatedGyms;
    }

    public String getPAN() {
        return PAN;
    }

    public void setPAN(String PAN) {
        this.PAN = PAN;
    }

    public String getAadhar() {
        return Aadhar;
    }

    public void setAadhar(String aadhar) {
        Aadhar = aadhar;
    }

    public String getGSTIN() {
        return GSTIN;
    }

    public void setGSTIN(String GSTIN) {
        this.GSTIN = GSTIN;
    }
}
