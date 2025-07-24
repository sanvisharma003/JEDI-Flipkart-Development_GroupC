package com.flipfit.dao;

import com.flipfit.bean.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlipFitCustomerDAO implements FlipFitCustomerDAOInterface {

    // --- Data Storage for All Entities ---
    private static final List<GymUser> customerList = new ArrayList<>();
    private static final List<Gym> gymList = new ArrayList<>();
    private static final List<Slot> slotList = new ArrayList<>();
    private static final Map<Integer, List<Booking>> userBookings = new HashMap<>();

    // --- Static Initializer Block ---
    static {
        // --- Populate Customers (from your file) ---
        GymUser customer1 = new GymUser();
        customer1.setGymUserId(1); customer1.setGymUserName("sanjay"); customer1.setGymUserPassword("sanjay123");
        customer1.setGymUserEmail("sanjay.k@example.com"); customer1.setPhoneno(987654321); customer1.setGymUserRole("Customer");
        customerList.add(customer1);

        GymUser customer2 = new GymUser();
        customer2.setGymUserId(2); customer2.setGymUserName("priya"); customer2.setGymUserPassword("priya123");
        customer2.setGymUserEmail("priya.s@example.com"); customer2.setPhoneno(876543210); customer2.setGymUserRole("Customer");
        customerList.add(customer2);

        GymUser customer3 = new GymUser();
        customer3.setGymUserId(3); customer3.setGymUserName("amit"); customer3.setGymUserPassword("amit123");
        customer3.setGymUserEmail("amit.g@example.com"); customer3.setPhoneno(765432109); customer3.setGymUserRole("Customer");
        customerList.add(customer3);

        // --- Populate Gyms ---
        gymList.add(new Gym(101, 1, "Koramangala Fitness", "Koramangala"));
        gymList.add(new Gym(102, 2, "Indiranagar Strength", "Indiranagar"));

        // --- Populate Slots ---
        Slot s1 = new Slot();
        s1.setSlotId(1); s1.setGymId(101); s1.setDate("2025-07-25"); s1.setSlotStartTime("06:00"); s1.setTotalSeats(10); s1.setAvailableSeats(10);
        slotList.add(s1);

        Slot s2 = new Slot();
        s2.setSlotId(2); s2.setGymId(101); s2.setDate("2025-07-25"); s2.setSlotStartTime("07:00"); s2.setTotalSeats(5); s2.setAvailableSeats(5);
        slotList.add(s2);
    }

    // --- User Management Methods ---
    @Override
    public void registerUser(String userName, String password, String email, int phoneNumber, String role) {
        for (GymUser user : customerList) {
            if (user.getGymUserName().equalsIgnoreCase(userName)) {
                System.out.println("Registration Failed: Username '" + userName + "' is already taken.");
                return;
            }
        }
        GymUser newUser = new GymUser();
        newUser.setGymUserId(customerList.size() + 1);
        newUser.setGymUserName(userName); newUser.setGymUserPassword(password); newUser.setGymUserEmail(email);
        newUser.setPhoneno(phoneNumber); newUser.setGymUserRole(role);
        customerList.add(newUser);
        System.out.println("✅ User '" + userName + "' registered successfully as a " + role);
    }

    @Override
    public boolean isUserValid(String userName, String password) {
        for (GymUser user : customerList) {
            if (user.getGymUserName().equalsIgnoreCase(userName) && user.getGymUserPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public GymUser getUserByName(String userName) {
        for (GymUser user : customerList) {
            if (user.getGymUserName().equalsIgnoreCase(userName)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<GymUser> getAllCustomers() {
        return new ArrayList<>(customerList);
    }

    // --- Booking, Gym, and Slot Methods ---
    @Override
    public List<Gym> getAllGyms() {
        return new ArrayList<>(gymList);
    }

    @Override
    public List<Slot> getSlotsByGymId(int gymId) {
        return slotList.stream().filter(slot -> slot.getGymId() == gymId).collect(Collectors.toList());
    }

    private Slot getSlotById(int slotId) {
        return slotList.stream().filter(slot -> slot.getSlotId() == slotId).findFirst().orElse(null);
    }

    @Override
    public boolean createBooking(int userId, int gymId, int slotId) {
        Slot selectedSlot = getSlotById(slotId);
        if (selectedSlot == null || selectedSlot.getAvailableSeats() <= 0) {
            return false;
        }
        selectedSlot.setAvailableSeats(selectedSlot.getAvailableSeats() - 1);
        List<Booking> bookingsForUser = userBookings.getOrDefault(userId, new ArrayList<>());
        Booking newBooking = new Booking();
        newBooking.setSlotBookingId((int) System.currentTimeMillis());
        newBooking.setUserId(userId); newBooking.setGymId(gymId); newBooking.setSlotId(slotId);
        newBooking.setDate(selectedSlot.getDate()); newBooking.setCancelled(false);
        bookingsForUser.add(newBooking);
        userBookings.put(userId, bookingsForUser);
        return true;
    }

    @Override
    public List<Booking> getBookingsByUserId(int userId) {
        return userBookings.getOrDefault(userId, new ArrayList<>());
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        for (List<Booking> bookings : userBookings.values()) {
            for (Booking booking : bookings) {
                if (booking.getSlotBookingId() == bookingId && !booking.isCancelled()) {
                    booking.setCancelled(true);
                    Slot bookedSlot = getSlotById(booking.getSlotId());
                    if (bookedSlot != null) {
                        bookedSlot.setAvailableSeats(bookedSlot.getAvailableSeats() + 1);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}