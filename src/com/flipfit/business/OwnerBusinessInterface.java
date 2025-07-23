package com.flipfit.business;

public interface OwnerBusinessInterface
{
    public void add_gym();
    public void view_bookings(int GymId);
    public void view_payments(int GymId);
    public void view_userdata(int GymUserId);
    public boolean add_slot(int GymId, int slotId);
    public boolean loginGymOwner(String userName, String password);
}
