package com.flipfit.business;

public class SlotBusiness
{
    public int decrease_seats(int availableSeats)
    {
        availableSeats--;
        System.out.println("Decreasing seats");
        return availableSeats;

    }

    public int increase_seats(int availableSeats)
    {
        availableSeats--;
        System.out.println("increasing seats");
        return availableSeats;
    }

    public boolean is_avail(int availableSeats)
    {
        System.out.println("Is avail " + availableSeats);
        return true;
    }

}
