package com.flipfit.business;

public interface OwnerBusinessInterface
{
    public void add_gym(int ownerId, String GymName, String address);
    public void view_bookings(int GymId);
    public void view_payments(int GymId);
    public void view_userdata(int GymUserId);
    public boolean add_slot(int GymId, String starttime, String endtime);
    public boolean loginGymOwner(String userName, String password);
}
