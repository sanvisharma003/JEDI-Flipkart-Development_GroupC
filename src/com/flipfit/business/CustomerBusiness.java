package com.flipfit.business;
import com.flipfit.bean.Gym;
import com.flipfit.bean.GymUser;
import com.flipfit.bean.Slot;
import com.flipfit.bean.Booking;

public class CustomerBusiness
{
    int amount=1000;
    public boolean book_slot(int userId ) // ask for other id like gymid and slotid
    {
        System.out.println("Select slot and centre (input given)");

        return true;
    }

    public void view_bookings(int UserId)
    {
        System.out.println("viewing bookings");
    }

    public void cancel_slots(int userId,int slotBookingId)
    {

        System.out.println("canceling slots");
    }

    public boolean update_slots(int userId,int slotId) // ask for other id like gymid and slotid
    {
        //add cust id
        System.out.println("Select Slot");




        return true;
    }

    public void view_plan()
    {
        //date
        System.out.println("viewing plan");
    }

    public boolean payment()
    {
        System.out.println("payment "+amount);
        return true;
    }
}