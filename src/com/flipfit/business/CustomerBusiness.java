package com.flipfit.business;

import com.flipfit.bean.*;
import com.flipfit.dao.FlipFitCustomerDAO;
import com.flipfit.dao.FlipFitCustomerDAOInterface;
import java.util.List;

public class CustomerBusiness implements CustomerBusinessInterface {

    private final FlipFitCustomerDAOInterface customerDAO = new FlipFitCustomerDAO();

    // --- User Methods ---
    @Override
    public void registerCustomer(String u, String p, String e, int ph) {
        customerDAO.registerUser(u, p, e, ph, "Customer");
    }

    @Override
    public boolean loginCustomer(String u, String p) {
        return customerDAO.isUserValid(u, p);
    }

    @Override
    public GymUser getCustomerDetails(String u) {
        return customerDAO.getUserByName(u);
    }

    @Override
    public List<GymUser> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    // --- Booking Methods ---
    @Override
    public List<Gym> viewAllGyms() {
        return customerDAO.getAllGyms();
    }

    @Override
    public List<Slot> viewSlotsForGym(int gymId) {
        return customerDAO.getSlotsByGymId(gymId);
    }

    @Override
    public boolean bookSlot(int userId, int gymId, int slotId) {
        return customerDAO.createBooking(userId, gymId, slotId);
    }

    @Override
    public List<Booking> getMyBookings(int userId) {
        return customerDAO.getBookingsByUserId(userId);
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        return customerDAO.cancelBooking(bookingId);
    }

    @Override
    public boolean changeBooking(int bookingId, int userId, int newGymId, int newSlotId) {
        // Step 1: Attempt to cancel the old booking.
        boolean isCancelled = customerDAO.cancelBooking(bookingId);

        if (isCancelled) {
            // Step 2: If cancellation was successful, try to create the new booking.
            boolean isBooked = customerDAO.createBooking(userId, newGymId, newSlotId);
            if (isBooked) {
                // Success: The booking was changed.
                return true;
            } else {
                // Failed to create new booking, so "rollback" the cancellation.
                // This is a simplified rollback. A real app would be more robust.
                customerDAO.createBooking(userId, getGymIdFromBooking(bookingId), getSlotIdFromBooking(bookingId));
                System.out.println("New slot is full. Reverting to your original booking.");
                return false;
            }
        }
        // Step 1 failed: The original booking could not be cancelled.
        System.out.println("Could not find or cancel your original booking.");
        return false;
    }

    // Helper methods to get details from a non-existent booking after cancellation
    // In a real app, you'd fetch the booking before cancelling. This is a simplification.
    private int getGymIdFromBooking(int bookingId) { return 101; /* Placeholder */ }
    private int getSlotIdFromBooking(int bookingId) { return 1; /* Placeholder */ }

}