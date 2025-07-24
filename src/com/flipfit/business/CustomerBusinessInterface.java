package com.flipfit.business;

import com.flipfit.bean.*;
import java.util.List;

public interface CustomerBusinessInterface {

    // --- User Methods ---
    void registerCustomer(String u, String p, String e, int ph);
    boolean loginCustomer(String u, String p);
    GymUser getCustomerDetails(String u);
    List<GymUser> getAllCustomers();

    // --- Booking Methods ---
    List<Gym> viewAllGyms();
    List<Slot> viewSlotsForGym(int gymId);
    boolean bookSlot(int userId, int gymId, int slotId);
    List<Booking> getMyBookings(int userId);
    boolean cancelBooking(int bookingId);
    boolean changeBooking(int bookingId, int userId, int newGymId, int newSlotId);
}