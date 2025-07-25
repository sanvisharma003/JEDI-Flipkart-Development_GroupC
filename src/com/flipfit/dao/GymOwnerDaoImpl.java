package com.flipfit.dao;

import com.flipfit.bean.*;
import com.flipfit.utils.*;

import java.sql.*;
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

    private static final String INSERT_GYM_SQL = "INSERT INTO Gym (Gym_Id, Owner_Id, Gym_Name, is_approved) VALUES (?, ?, ?, ?);";
    private static final String get_owner_id = "SELECT Owner_Id FROM Owner where User_Id = ?;";

    private static final String VIEW_PAYMENTS_SQL = "SELECT Payment_Id, Customer_Id, Balance, Gym_Id, Slot_Booking_Id FROM Payment WHERE Gym_Id = ?";

    private static final String SELECT_BOOKINGS_BY_GYM_ID_SQL = "SELECT Slot_Booking_Id, Customer_Id, Slot_Id, Gym_Id, Date, Is_Cancelled FROM Booking WHERE Gym_Id = ?";

    public boolean registerGymCenter(Gym gCenter)
    {
        int ownerId = 0;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");
             PreparedStatement preparedStatement = connection.prepareStatement(get_owner_id)) {

            preparedStatement.setInt(1, gymMenu.user_id);


            try (ResultSet resultSet = preparedStatement.executeQuery())
            {
                if (resultSet.next()) {
                    ownerId = resultSet.getInt("Owner_Id");
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GYM_SQL)) {

            preparedStatement.setInt(1, gCenter.GymId);
            preparedStatement.setInt(2, ownerId);
            preparedStatement.setString(3, gCenter.GymName);
            preparedStatement.setInt(4, 0);


            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println( gymMenu.user_id+" "+ gCenter.GymId);
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

    public boolean viewPayments(int GymId)
    {
        System.out.println("\n--- Payments for Gym ID: " + GymId + " ---");

        List<Payment> paymentsFound = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load JDBC driver
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");

            statement = connection.prepareStatement(VIEW_PAYMENTS_SQL); // <-- ADDED THIS CRUCIAL LINE
            statement.setInt(1, GymId);

            resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                Payment payment = new Payment();
                payment.setPaymentId(resultSet.getInt("Payment_Id"));
                payment.setGymUserId(resultSet.getInt("Customer_Id")); // Note: Check case of GymUserId in your bean if it's different (e.g., setGymUserId)
                payment.setCurrentBalance(resultSet.getInt("Balance")); // Assuming double
                payment.setAmount(resultSet.getInt("Gym_Id"));               // Assuming double
                payment.setSlotBookingId(resultSet.getInt("Slot_Booking_Id"));
                paymentsFound.add(payment);
            }

            if (!paymentsFound.isEmpty())
            {
                // Prepare data for tabular display using TableFormatter
                String[] headers = {"Payment ID", "User ID", "Balance", "Amount", "Booking ID"};
                List<String[]> data = new ArrayList<>();

                for (Payment payment : paymentsFound) {
                    data.add(new String[]{
                            String.valueOf(payment.getPaymentId()),
                            String.valueOf(payment.getGymUserId()),
                            String.format("%.2f", payment.getCurrentBalance()), // Format balance to 2 decimal places
                            String.format("%.2f", payment.getAmount()),         // Format amount to 2 decimal places
                            String.valueOf(payment.getSlotBookingId())
                    });
                }
                TableFormatter.printTable(headers, data); // Use your TableFormatter

                return true; // Payments were found and displayed
            } else {
                System.out.println("No payments found for Gym ID: " + GymId + ".");
                return false; // No payments found
            }

        } catch (SQLException e) {
            System.err.println("Database error retrieving payments: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while viewing payments: " + e.getMessage());
            e.printStackTrace();
            return false;
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


    public boolean viewBookings(int gymId)
    {
        System.out.println("--- All Bookings for Gym ID: " + gymId + " ---");

        List<Booking> bookingsFound = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");

            statement = connection.prepareStatement(SELECT_BOOKINGS_BY_GYM_ID_SQL);
            statement.setInt(1, gymId);

            resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                   //Slot_Booking_Id, Customer_Id, Slot_Id, Gym_Id, Date, Is_Cancelled
                Booking booking = new Booking();
                booking.slotBookingId = resultSet.getInt("Slot_Booking_Id"); // Direct assignment (if public) or use setter
                booking.userId = resultSet.getInt("Customer_Id");
                booking.slotId = resultSet.getInt("Slot_Id");
                booking.gymId = resultSet.getInt("Gym_Id");
                // The 'date' column in your DB is DATETIME, so retrieve as Timestamp or String
                // If Booking.date is String, retrieve as String:
                booking.date = resultSet.getString("Date");
                // If Booking.date is LocalDateTime, retrieve as Timestamp and convert:
                // booking.date = resultSet.getTimestamp("date").toLocalDateTime().toString(); // Or store as LocalDateTime
                booking.isCancelled = resultSet.getBoolean("Is_Cancelled");

                bookingsFound.add(booking); // Add the populated Booking object to the list
            }

            if (!bookingsFound.isEmpty()) {
                // Prepare data for tabular display using TableFormatter
                String[] headers = {"Booking ID", "User ID", "Slot ID", "Gym ID", "Date", "Cancelled"};
                List<String[]> data = new ArrayList<>();

                for (Booking booking : bookingsFound) {
                    data.add(new String[]{
                            String.valueOf(booking.slotBookingId),
                            String.valueOf(booking.userId),
                            String.valueOf(booking.slotId),
                            String.valueOf(booking.gymId),
                            booking.date, // Use the date string directly
                            String.valueOf(booking.isCancelled)
                    });
                }
                TableFormatter.printTable(headers, data); // Use your TableFormatter

                return true; // Bookings were found and displayed
            } else {
                System.out.println("No bookings found for Gym ID: " + gymId + ".");
                return false; // No bookings found
            }

        } catch (SQLException e) {
            System.err.println("Database error retrieving bookings: " + e.getMessage());
            //e.printStackTrace();
            //return new ArrayList<>(); // Return empty list on error
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Ensure the JAR is in your classpath.");
            e.printStackTrace();
            // return new ArrayList<>(); // Return empty list on error
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while retrieving bookings: " + e.getMessage());
            e.printStackTrace();
            // return new ArrayList<>(); // Return empty list on error
        }
        return true;
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