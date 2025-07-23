package com.flipfit.business;
import com.flipfit.bean.Gym;
import com.flipfit.bean.GymUser;
import com.flipfit.bean.Slot;

public class OwnerBusiness
{
    public void add_gym()
    {
        System.out.println("Adding gym");
    }

    public void view_bookings(int GymId)
    {
        //date time
        System.out.println("viewing bookings ");
    }

    public void view_payments(int GymId)
    {
        System.out.println("viewing payments");
    }

    public void view_userdata(int GymUserId)
    {
        System.out.println("viewing customers");
    }

    public boolean add_slot(int GymId, int slotId)
    {
        System.out.println("Adding slot");
        return true;
    }
    public boolean loginGymOwner(String userName, String password) {
        // Dummy login logic
        return userName.equals("owner") && password.equals("password");
    }



}