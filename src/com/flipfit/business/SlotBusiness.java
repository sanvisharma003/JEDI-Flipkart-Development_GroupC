package com.flipfit.business;

public class SlotBusiness
{
    public int decrease_seats()
    {
        //availableseats--;
        System.out.println("Decreasing seats");
        //return availableSeats;
        return 0;
    }

    public int increase_seats(int availableseats)
    {
        availableseats--;
        System.out.println("increasing seats");
        return availableseats;
    }

    public boolean is_avail(int availableseats)
    {
        System.out.println("Is avail " + availableseats);
        return true;
    }

}
