package com.flipfit.business;

public class BookingBusiness
{
    public boolean confirm()
    {
        System.out.println("Confirmed slot");
        return true;
    }

    public boolean cancel()
    {
        System.out.println("Cancelled slot");
        return true;
    }

    public boolean waitlist()
    {
        System.out.println("moved to waitlist ");
        return true;
    }
}
