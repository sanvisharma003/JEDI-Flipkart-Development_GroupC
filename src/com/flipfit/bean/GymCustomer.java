package com.flipfit.bean;

import java.util.List;

public class GymCustomer extends GymUser {
    private String CustomerAddress;
    private List<String> plans;

    public int getCurrentBalance() {
        return CurrentBalance;
    }

    public void setCurrentBalance(int currentBalance) {
        CurrentBalance = currentBalance;
    }

    public List<String> getPlans() {
        return plans;
    }

    public void setPlans(List<String> plans) {
        this.plans = plans;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    private int CurrentBalance;
}
