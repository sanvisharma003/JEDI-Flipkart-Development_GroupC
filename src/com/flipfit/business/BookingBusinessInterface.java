package com.flipfit.business;

public interface BookingBusinessInterface {
    public boolean confirm(int slotBookingId);
    public boolean cancel(int slotBookingId);
    public boolean waitlist(int slotBookingId);
}
