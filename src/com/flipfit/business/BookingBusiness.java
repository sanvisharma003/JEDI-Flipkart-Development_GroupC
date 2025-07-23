package com.flipfit.business;

public class BookingBusiness
{
    public boolean confirm(int slotBookingId)
    {
        System.out.println("Confirmed slot");
        return true;
    }

    public boolean cancel(int slotBookingId)
    {
        System.out.println("Cancelled slot");
        return true;
    }

    public boolean waitlist(int slotBookingId)
    {
        System.out.println("moved to waitlist ");
        return true;
    }
}
