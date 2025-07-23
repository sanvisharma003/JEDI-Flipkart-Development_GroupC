package com.flipfit.business;

public interface SlotBusinessInterface
{
    public int decrease_seats(int availableSeats);
    public int increase_seats(int availableSeats);
    public boolean is_avail(int availableSeats);

}
