package com.flipfit.bean;

public class Payment {
    private int paymentId;
    private int GymUserId;
    private double currentBalance;

    public int getSlotBookingId() {
        return slotBookingId;
    }

    public void setSlotBookingId(int slotBookingId) {
        this.slotBookingId = slotBookingId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public int getGymUserId() {
        return GymUserId;
    }

    public void setGymUserId(int gymUserId) {
        GymUserId = gymUserId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    private double amount;
    private int slotBookingId;
    //private enum[Credit,Debit] type;
    //private enum status

}
