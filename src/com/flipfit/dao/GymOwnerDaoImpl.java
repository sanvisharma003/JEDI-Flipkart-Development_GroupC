package com.flipfit.dao;

import com.flipfit.bean.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import com.flipfit.client.GymApplicationMenu;

public class GymOwnerDaoImpl implements GymOwnerDaoInterface
{
    private static Map<Integer, GymOwner> gymOwnersDB = new HashMap<>();
    private static Map<Integer, Gym> gymCentersDB = new HashMap<>();
    private static Map<Integer, Slot> gymSlotDB = new HashMap<>();
    private static Map<Integer, GymUser> gymUsersDB = new HashMap<>();
    private static Map<Integer, Booking> gymBookingsDB = new HashMap<>();
    private static final String INSERT_SLOT_SQL = "INSERT INTO gym_slots (Gym_Id, Start_Time, End_Time, Date, Total_Seats, Avail_Seats) VALUES (?, ?, ?, ?, ?, ?)";

    private static Map<Integer, List<Integer>> gymPaymentsDB = new HashMap<>();
    private static GymApplicationMenu gymMenu = new GymApplicationMenu();

    private static final String INSERT_BOOKS_SQL = "INSERT INTO Gym (Gym_Id, Owner_Id, Gym_Name, is_approved) VALUES (?, ?, ?, ?);";

    @Override
    public boolean registerGymCenter(Gym gCenter)
    {
        System.out.println(gCenter.GymId+" "+gCenter.GymName+" "+gymMenu.own_id);
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOKS_SQL))
        {

            preparedStatement.setInt(1, gCenter.GymId );
            preparedStatement.setInt(2, gymMenu.own_id);
            preparedStatement.setString(3, gCenter.GymName);
            preparedStatement.setInt(4, 0);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }



    public boolean addSlot(Slot gSlot)
    {
        if (gSlot == null) {
            return false;
        }

        gymSlotDB.put(gSlot.getSlotId(), gSlot);
        System.out.println("Mock: Registered gym center with slot id:  " + gSlot.getSlotId() + " start time: " + gSlot.getSlotStartTime() + " end time: " + gSlot.getSlotEndTime());
        return true;
    }

    public boolean viewPayments(int GymId) {
        System.out.println("--- Payments for Gym ID: " + GymId + " ---");

        List<Integer> paymentsForGym = gymPaymentsDB.get(GymId);

        if (paymentsForGym != null && !paymentsForGym.isEmpty()) {
            for (int payment : paymentsForGym) {
                System.out.println("Payment: " + payment);
            }
            return true; // Payments were found and displayed
        } else {
            System.out.println("No payments found for Gym ID: " + GymId);
            return false; // No payments found
        }
    }

    public boolean viewUsers(int userId) { // Changed parameter name to 'userId' for clarity
        System.out.println("--- User Data for User ID: " + userId + " ---");

        GymUser user = gymUsersDB.get(userId); // Get the single GymUser object

        if (user != null) {
            // Print details of the single user object
            System.out.println("User ID: " + user.getGymUserId());
            System.out.println("Name: " + user.getGymUserName());
            System.out.println("Email: " + user.getGymUserEmail());
            // System.out.println("Password: " + user.getGymUserPassword()); // Usually don't print passwords
            System.out.println("Phone No: " + user.getPhoneno());
            System.out.println("Role: " + user.getGymUserRole());
            return true; // User was found and data displayed
        } else {
            System.out.println("No user found for User ID: " + userId);
            return false; // No user found
        }
    }
//    public boolean viewBookings(int gymId) { // Changed parameter name to 'bookingId'
//        System.out.println("--- Booking Data for Gym ID: " + gymId + " ---");
//
//        Booking booking = gymBookingsDB.get(gymId); // Get the single Booking object
//
//        if (booking != null) {
//            // Print details of the single booking object
//            System.out.println("Booking ID: " + booking.slotBookingId);
//            System.out.println("User ID: " + booking.userId);
//            System.out.println("Slot ID: " + booking.slotId);
//            System.out.println("Gym ID: " + booking.gymId);
//            System.out.println("Date: " + booking.date);
//            System.out.println("Cancelled: " + booking.isCancelled);
//            // If you had an enum for status, you'd print it here:
//            // System.out.println("Status: " + booking.status);
//            return true; // Booking was found and data displayed
//        } else {
//            System.out.println("No booking found for Booking ID: " + gymId);
//            return false; // No booking found
//        }
//    }

    public boolean viewBookings(int gymId) { // Parameter name is now 'gymId' as requested
        System.out.println("--- All Bookings for Gym ID: " + gymId + " ---");

        List<Booking> bookingsForGym = new ArrayList<>();

        // Iterate through all values (Booking objects) in the gymBookingsDB map
        for (Booking booking : gymBookingsDB.values()) {
            // Check if the current booking's gymId matches the requested gymId
            if (booking.gymId == gymId) {
                bookingsForGym.add(booking); // Add to our list
            }
        }

        if (!bookingsForGym.isEmpty()) {
            System.out.println("Found " + bookingsForGym.size() + " booking(s) for Gym ID " + gymId + ":");
            for (Booking booking : bookingsForGym) {
                System.out.println("------------------------------------");
                System.out.println("Booking ID: " + booking.slotBookingId);
                System.out.println("  User ID: " + booking.userId);
                System.out.println("  Slot ID: " + booking.slotId);
                System.out.println("  Date: " + booking.date);
                System.out.println("  Cancelled: " + booking.isCancelled);
            }
            System.out.println("------------------------------------");
            return true; // Bookings were found and displayed
        } else {
            System.out.println("No bookings found for Gym ID: " + gymId);
            return false; // No bookings found
        }
    }


    @Override
    public GymOwner getGymOwnerDetails(String ownerEmail) {
        for (GymOwner owner : gymOwnersDB.values()) {
            if (owner.getGymUserEmail().equals(ownerEmail)) {
                System.out.println("Mock: Retrieved gym owner details for email: " + ownerEmail);
                return owner;
            }
        }
        System.out.println("Mock: No gym owner found for email: " + ownerEmail);
        return null; // Or throw an exception if not found
    }

    @Override
    public List<Gym> getCenterDetails(int ownerId) {
        List<Gym> gymCenters = new ArrayList<>();
        for (Gym center : gymCentersDB.values()) {
            if (center.getGymOwnerId() == ownerId) {
                gymCenters.add(center);
            }
        }
        System.out.println("Mock: Retrieved " + gymCenters.size() + " centers for owner ID: " + ownerId);
        return gymCenters;
    }


}