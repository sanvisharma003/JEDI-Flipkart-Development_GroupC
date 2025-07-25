//package com.flipfit.dao;
//
//import com.flipfit.bean.*;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//import com.flipfit.dao.FlipFitAdminDAO;
//
//public class FlipFitCustomerDAO implements FlipFitCustomerDAOInterface
//{
//
//    private static final String GET_USER_BY_NAME_SQL = "SELECT u.User_Id, u.Username, u.Password, u.Role_Id, c.Email, c.Phone, c.Address FROM User u JOIN Customer c ON u.User_Id = c.User_id WHERE u.Username =?";
//    private static final String GET_ALL_GYMS_SQL = "SELECT * FROM Gym WHERE is_approved = 1";
//    private static final String GET_SLOTS_BY_GYM_ID_SQL = "SELECT * FROM Gym_Slot WHERE Gym_Id =? AND Avail_Seats > 0";
//    private static final String GET_SLOT_BY_ID_FOR_UPDATE_SQL = "SELECT * FROM Gym_Slot WHERE Slot_Id =? FOR UPDATE";
//    private static final String CREATE_BOOKING_SQL = "INSERT INTO Booking (Slot_Booking_Id, Customer_Id, Slot_Id, Gym_Id, Date, Is_Cancelled) VALUES (?,?,?,?,?, 0)";
//    private static final String DECREMENT_SLOT_SEATS_SQL = "UPDATE Gym_Slot SET Avail_Seats = Avail_Seats - 1 WHERE Slot_Id =?";
//    private static final String GET_BOOKINGS_BY_USER_ID_SQL = "SELECT b.Booking_Id, b.Gym_Id, b.Slot_Id, b.date, b.is_cancelled FROM Booking b WHERE b.User_Id =?";
//    private static final String GET_BOOKING_FOR_CANCELLATION_SQL = "SELECT Slot_Id, is_cancelled FROM Booking WHERE Booking_Id =? FOR UPDATE";
//    private static final String CANCEL_BOOKING_SQL = "UPDATE Booking SET is_cancelled = 1 WHERE Booking_Id =?";
//    private static final String GET_GYMS_SQL = "SELECT * FROM Gym WHERE is_approved = 1";
//    public static int Slot_bookingId = 504;
//
//    public void getAllGyms()
//    {
//        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");
//             PreparedStatement preparedStatement = connection.prepareStatement(GET_GYMS_SQL)) {
//
//            //preparedStatement.setInt(1, gymId);
//
//            ResultSet rs = preparedStatement.executeQuery();
//
//            // If a record is found, map the data to a new Gym object.
//            while (rs.next())
//            {
//                System.out.println("\n--- Gym Details ---");
//                System.out.println("ID: " + rs.getInt("Gym_Id"));
//                System.out.println("Name: " + rs.getString("Gym_Name"));
//                System.out.println("Location: " + rs.getString("Location"));
//                //System.out.println("Is Approved: " + rs.getBoolean("is_approved"));
//                System.out.println("-------------------");
//                //gym.setLocation(rs.getString("location"));
//            }
//
//        } catch (SQLException e) {
//            System.err.println("SQL Exception occurred: " + e.getMessage());
//            e.printStackTrace();
//        } catch (NumberFormatException e) {
//            System.err.println("Invalid Gym ID format. Please provide a numeric ID. " + e.getMessage());
//        }
//    }
//
//    @Override
//    public boolean createBooking(int userId, int gymId, int slotId) {
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");
//            connection.setAutoCommit(false); // Start transaction
//
//            // Lock the slot row and check availability
//            Slot selectedSlot;
//            try (PreparedStatement selectStmt = connection.prepareStatement(GET_SLOT_BY_ID_FOR_UPDATE_SQL)) {
//                selectStmt.setInt(1, slotId);
//                ResultSet rs = selectStmt.executeQuery();
//                if (rs.next()) {
//                    selectedSlot = new Slot();
//                    selectedSlot.setAvailableSeats(rs.getInt("Avail_Seats"));
//                    selectedSlot.setDate(rs.getString("Date"));
//                } else {
//                    connection.rollback();
//                    return false; // Slot not found
//                }
//            }
//
//            if (selectedSlot.getAvailableSeats() <= 0) {
//                connection.rollback();
//                return false; // No available seats
//            }
//
//            // Decrement available seats
//            try (PreparedStatement updateStmt = connection.prepareStatement(DECREMENT_SLOT_SEATS_SQL)) {
//                updateStmt.setInt(1, slotId);
//                updateStmt.executeUpdate();
//            }
//
//            // Create the new booking
//            try (PreparedStatement insertStmt = connection.prepareStatement(CREATE_BOOKING_SQL)) {
//                insertStmt.setInt(1, Slot_bookingId);
//                insertStmt.setInt(2, userId);
//                insertStmt.setInt(3, slotId);
//                insertStmt.setInt(4, gymId);
//                insertStmt.setString(5, selectedSlot.getDate());
//                insertStmt.executeUpdate();
//            }
//
//            connection.commit();
//            Slot_bookingId++; // Commit transaction
//            return true;
//
//        } catch (SQLException e) {
//            System.err.println("DAO: SQL Exception during booking creation: " + e.getMessage());
//            if (connection!= null) {
//                try {
//                    connection.rollback();
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
//            }
//            e.printStackTrace();
//            return false;
//        } finally {
//            if (connection!= null) {
//                try {
//                    connection.setAutoCommit(true);
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//}

package com.flipfit.dao;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Slot; // Assuming you have a Slot bean with getAvailableSeats and getDate methods
import com.flipfit.bean.Gym; // Assuming you have a Gym bean for getAllGyms method context
// Ensure other necessary beans are imported (e.g., User, Customer if used elsewhere)

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class FlipFitCustomerDAO implements FlipFitCustomerDAOInterface
{

    public class EmailValidator {
        public static boolean isValidEmail(String email) {
            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
    }

    private static final String CREATE_BOOKING_SQL = "INSERT INTO `booking` (Slot_Booking_Id, Customer_Id, Slot_Id, Gym_Id, Date, Is_Cancelled) VALUES (?,?,?,?,?, 0)";
    private static final String GET_SLOT_BY_ID_FOR_UPDATE_SQL = "SELECT Avail_Seats, Date FROM `gym_slot` WHERE Slot_Id =? FOR UPDATE";

    private static final String GET_USER_BY_NAME_SQL = "SELECT u.User_Id, u.Username, u.Password, u.Role_Id, c.Email, c.Phone, c.Address FROM `user` u JOIN `customer` c ON u.User_Id = c.user_Id WHERE u.Username =?";
    private static final String GET_ALL_GYMS_SQL = "SELECT * FROM `gym` WHERE is_approved = 1"; // Redundant with GET_GYMS_SQL
    private static final String GET_SLOTS_BY_GYM_ID_SQL = "SELECT * FROM `gym_slot` WHERE Gym_Id =? AND Avail_Seats > 0";
    private static final String DECREMENT_SLOT_SEATS_SQL = "UPDATE `gym_slot` SET Avail_Seats = Avail_Seats - 1 WHERE Slot_Id =?";
    private static final String GET_BOOKINGS_BY_USER_ID_SQL = "SELECT Slot_Booking_Id, Slot_Id,Gym_Id,Date, Is_Cancelled FROM Booking WHERE Customer_Id =? AND Is_Cancelled =0"; // Assuming Customer_Id
    private static final String GET_BOOKING_FOR_CANCELLATION_SQL = "SELECT Slot_Id, Is_Cancelled FROM `booking` WHERE Slot_Booking_Id =? FOR UPDATE";
    private static final String CANCEL_BOOKING_SQL = "UPDATE `booking` SET Is_Cancelled = 1 WHERE Slot_Booking_Id =?";
//    private static final String GET_BOOKING_FOR_CANCELLATION_SQL = "SELECT Slot_Id, is_cancelled FROM Booking WHERE Booking_Id =? FOR UPDATE";
//    private static final String CANCEL_BOOKING_SQL = "UPDATE Booking SET is_cancelled = 1 WHERE Booking_Id =?";
    private static final String INCREMENT_SLOT_SEATS_SQL = "UPDATE Gym_Slot SET Avail_Seats = Avail_Seats + 1 WHERE Slot_Id =?";
    private static final String GET_GYMS_SQL = "SELECT * FROM `gym` WHERE is_approved = 1";
    public static int slot_bookingId = 506;// This one is used in getAllGyms()

    @Override
    public void getAllGyms() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");
             PreparedStatement preparedStatement = connection.prepareStatement(GET_GYMS_SQL)) {

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    System.out.println("\n--- Gym Details ---");
                    System.out.println("ID: " + rs.getInt("Gym_Id"));
                    System.out.println("Name: " + rs.getString("Gym_Name"));
                    // >>> CAUTION: Check if 'Location' column actually exists in your 'Gym' table. <<<
                    // >>> If not, the line below will cause a SQLException. <<<
                    // System.out.println("Location: " + rs.getString("Location"));
                    System.out.println("-------------------");
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while fetching gyms: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) { // This catch block is unlikely to be hit here
            System.err.println("Invalid format encountered: " + e.getMessage());
        }
    }
    public int getLatestId() {
        int latestId = -1; // Default value if no record is found
        String sql = "SELECT Slot_Booking_Id FROM Flipfit_Schema.Booking ORDER BY Slot_Booking_Id DESC LIMIT 1";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            // Check if a result was returned
            if (rs.next()) {
                latestId = rs.getInt(1); // Get the integer from the first column of the result
                // Or you can use the column name: latestId = rs.getInt("your_int_column");
            }

        } catch (SQLException e) {
            System.err.println("DAO: SQL Exception while fetching the latest ID: " + e.getMessage());
            e.printStackTrace();
        }
        return latestId;
    }

    @Override
    public boolean createBooking(int customerId, int gymId, int slotId) { // Renamed userId to customerId for clarity
        Connection connection = null; // Declare connection outside try-block for finally access
        slot_bookingId = getLatestId();
        slot_bookingId++;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");
            connection.setAutoCommit(false); // Start transaction

            Slot selectedSlot = null; // Initialize to null
            // Lock the slot row and check availability
            try (PreparedStatement selectStmt = connection.prepareStatement(GET_SLOT_BY_ID_FOR_UPDATE_SQL)) {
                selectStmt.setInt(1, slotId);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        selectedSlot = new Slot();
                        selectedSlot.setAvailableSeats(rs.getInt("Avail_Seats"));
                        selectedSlot.setDate(rs.getString("Date")); // Get Date as String
                    } else {
                        System.err.println("DAO: Slot with ID " + slotId + " not found.");
                        connection.rollback(); // Rollback if slot not found
                        return false; // Slot not found
                    }
                }
            }

            if (selectedSlot.getAvailableSeats() <= 0) {
                System.err.println("DAO: No available seats for Slot ID " + slotId + ".");
                connection.rollback(); // Rollback if no seats
                return false; // No available seats
            }

            // Decrement available seats
            try (PreparedStatement updateStmt = connection.prepareStatement(DECREMENT_SLOT_SEATS_SQL)) {
                updateStmt.setInt(1, slotId);
                int updatedRows = updateStmt.executeUpdate();
                if (updatedRows == 0) {
                    System.err.println("DAO: Failed to decrement seats for Slot ID " + slotId + ". Possibly concurrent booking.");
                    connection.rollback(); // Rollback on update failure
                    return false; // Failed to update, likely concurrent access
                }
            }

            // Create the new booking
            // Use Statement.RETURN_GENERATED_KEYS to get the auto-generated Slot_Booking_Id
            try (PreparedStatement insertStmt = connection.prepareStatement(CREATE_BOOKING_SQL, Statement.RETURN_GENERATED_KEYS)) {

                insertStmt.setInt(1, slot_bookingId);
                insertStmt.setInt(2, customerId);         // Parameter 1: Customer_Id
                insertStmt.setInt(3, slotId);             // Parameter 2: Slot_Id (CORRECTED)
                insertStmt.setInt(4, gymId);              // Parameter 3: Gym_Id (CORRECTED)
                insertStmt.setString(5, selectedSlot.getDate()); // Parameter 4: Date
                // Is_Cancelled is hardcoded to 0 in SQL (Parameter 5 is implicit '0')

                int rowsAffected = insertStmt.executeUpdate();

                if (rowsAffected > 0) {
                    // Optionally retrieve the auto-generated Slot_Booking_Id if needed
                    try (ResultSet rsGeneratedKeys = insertStmt.getGeneratedKeys()) {
                        if (rsGeneratedKeys.next()) {
                            long bookingId = rsGeneratedKeys.getLong(1);
                            System.out.println("Booking created successfully with ID: " + bookingId);
                            // If you need to return this bookingId, change method signature
                        }
                    }
                } else {
                    System.err.println("DAO: No rows affected during booking insert. Registration failed.");
                    connection.rollback(); // Rollback if insert didn't affect rows
                    return false; // Insert failed
                }


            }

            connection.commit();
            // Commit transaction if all steps succeeded
            return true; // Indicate successful booking

        } catch (SQLException e) {
            System.err.println("DAO: SQL Exception during booking creation: " + e.getMessage());
            if (connection != null) {
                try {
                    connection.rollback(); // Always rollback on SQL error
                    System.err.println("DAO: Transaction rolled back.");
                } catch (SQLException ex) {
                    System.err.println("DAO: Rollback failed: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            // Ensure connection is closed and auto-commit is reset in the finally block
            if (connection != null) {
                try {
                    connection.setAutoCommit(true); // Reset to default auto-commit behavior
                    connection.close(); // Close the connection
                } catch (SQLException e) {
                    System.err.println("DAO: Error closing connection or resetting auto-commit: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void viewMyBookings(int userId) {

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BOOKINGS_BY_USER_ID_SQL)) {
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                //Booking booking = new Booking();
                System.out.println("BookingId is " + rs.getInt("Slot_Booking_Id"));
                System.out.println("Your Customer Id " +userId);
                System.out.println("SlotId is " + rs.getInt("Slot_Id"));
                System.out.println("Gym Id is " + rs.getInt("Gym_Id"));
                System.out.println("Date is " + rs.getString("Date"));
                System.out.println("Status of your booking " + !rs.getBoolean("Is_Cancelled"));

            }
        } catch (SQLException e) {
            System.err.println("DAO: SQL Exception while fetching bookings by user ID: " + e.getMessage());
            e.printStackTrace();
        }

    }
    @Override
    public boolean cancelBooking(int bookingId) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");
            connection.setAutoCommit(false); // Start transaction

            // Get booking details and lock the row
            int slotId = -1;
            boolean isAlreadyCancelled = true;
            try (PreparedStatement selectStmt = connection.prepareStatement(GET_BOOKING_FOR_CANCELLATION_SQL)) {
                selectStmt.setInt(1, bookingId);
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    slotId = rs.getInt("Slot_Id");
                    isAlreadyCancelled = rs.getBoolean("Is_Cancelled");

                } else {
                    connection.rollback();
                    return false; // Booking not found
                }
            }

            if (isAlreadyCancelled) {
                connection.rollback();
                return false; // Already cancelled
            }

            // Mark booking as cancelled
            try (PreparedStatement cancelStmt = connection.prepareStatement(CANCEL_BOOKING_SQL)) {
                cancelStmt.setInt(1, bookingId);
                cancelStmt.executeUpdate();
            }

            // Increment available seats for the corresponding slot
            try (PreparedStatement updateStmt = connection.prepareStatement(INCREMENT_SLOT_SEATS_SQL)) {
                updateStmt.setInt(1, slotId);
                updateStmt.executeUpdate();
            }
            System.out.println("Booking Cancelled Successfully");
            connection.commit(); // Commit transaction
            return true;

        } catch (SQLException e) {
            System.err.println("DAO: SQL Exception during booking cancellation: " + e.getMessage());
            if (connection!= null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (connection!= null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}