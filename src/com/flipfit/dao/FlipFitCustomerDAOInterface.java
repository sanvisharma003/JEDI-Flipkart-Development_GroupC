package com.flipfit.dao;

//abcc
import com.flipfit.bean.GymUser;
import java.util.List;
import com.flipfit.bean.Booking;
import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;

/**
 * Data Access Object (DAO) interface for handling user-centric operations,
 * primarily for customer registration and login.
 */
public interface FlipFitCustomerDAOInterface
{
    public void getAllGyms();
    public boolean createBooking(int userId, int gymId, int slotId);
    public void viewMyBookings(int userId);
    public boolean cancelBooking(int bookingId);

}


