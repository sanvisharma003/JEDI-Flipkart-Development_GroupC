package com.flipfit.business;

import com.flipfit.bean.Notification;
import com.flipfit.bean.GymUser;

public class NotificationBusiness
{
    public boolean send_noti(int GymUserId, String msg)
    {
        System.out.println("Sending notification");
        return true;
    }
}
