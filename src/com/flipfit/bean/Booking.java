package com.flipfit.bean;

public class Booking {
    public int slotBookingId;
    public int userId;
    public int slotId;
    public int gymId;
    public String date;
    //public enum status
    public boolean isCancelled;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSlotBookingId() {
        return slotBookingId;
    }

    public void setSlotBookingId(int slotBookingId) {
        this.slotBookingId = slotBookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getGymId() {
        return gymId;
    }

    public void setGymId(int gymId) {
        this.gymId = gymId;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }


}
