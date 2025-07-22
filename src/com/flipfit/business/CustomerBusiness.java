package com.flipfit.business;
import com.flipfit.bean.Gym;
import com.flipfit.bean.GymUser;
import com.flipfit.bean.Slot;
import com.flipfit.bean.Booking;

public class CustomerBusiness
{
    public boolean book_slot(int GymId, int UserId, int SlotId )
    {
        System.out.println("Booking slot");
        return true;
    }

    public void view_bookings(int GymId, int UserId)
    {
        System.out.println("viewing bookings");
    }

    public void cancel_slots(int slotBookingId)
    {
        System.out.println("canceling slots");
    }

    public boolean update_slots()
    {
        //add cust id
        System.out.println("Updating slots");
        return true;
    }

    public void view_plan()
    {
        //date
        System.out.println("viewing plan");
    }

    public boolean payment(int amount)
    {
        System.out.println("payment "+amount);
        return true;
    }
}
