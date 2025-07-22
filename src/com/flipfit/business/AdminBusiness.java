package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.GymUser;

public class AdminBusiness
{
    private boolean approval(int GymId)
    {
        System.out.println("GymAdminId = " + GymId);
        return true;
    }

    public void add_gym()
    {
        System.out.println("Adding Gym");
    }

    public void add_owner(int GymUser)
    {
        System.out.println("Adding Owner");
    }

    public void view_gym()
    {
        System.out.println("View Gym");
    }

    public void view_owner()
    {
        System.out.println("View owner");
    }

    public void view_all_payments()
    {
        System.out.println("View all payments");
    }
}
