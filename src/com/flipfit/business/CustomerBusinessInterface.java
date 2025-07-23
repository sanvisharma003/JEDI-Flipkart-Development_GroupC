package com.flipfit.business;

public interface CustomerBusinessInterface
{
    public boolean book_slot(int userId );
    public void view_bookings(int UserId);
    public void cancel_slots(int userId,int slotBookingId);
    public boolean update_slots(int userId,int slotId);
    public void view_plan();
}
