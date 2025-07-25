package com.flipfit.dao;

import com.flipfit.bean.*;
import com.flipfit.utils.*;

import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import com.flipfit.client.GymApplicationMenu;
import com.flipfit.dao.FlipFitAdminDAO;
import java.util.regex.*;


public class GymOwnerDaoImpl implements GymOwnerDaoInterface
{
    public class EmailValidator {
        public static boolean isValidEmail(String email) {
            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
    }
    private static Map<Integer, GymOwner> gymOwnersDB = new HashMap<>();
    private static Map<Integer, Gym> gymCentersDB = new HashMap<>();
    private static Map<Integer, Slot> gymSlotDB = new HashMap<>();
    private static Map<Integer, GymUser> gymUsersDB = new HashMap<>();
    private static Map<Integer, Booking> gymBookingsDB = new HashMap<>();
//    private static final String INSERT_SLOT_SQL = "INSERT INTO gym_slots (Gym_Id, Start_Time, End_Time, Date, Total_Seats, Avail_Seats) VALUES (?, ?, ?, ?, ?, ?)";

    private static Map<Integer, List<Integer>> gymPaymentsDB = new HashMap<>();
    private static GymApplicationMenu gymMenu = new GymApplicationMenu();

    private static final String INSERT_GYM_SQL = "INSERT INTO Gym (Gym_Id, Owner_Id, Gym_Name, is_approved) VALUES (?, ?, ?, ?);";
    private static final String get_owner_id = "SELECT Owner_Id FROM Owner where User_Id = ?;";

    private static final String VIEW_PAYMENTS_SQL = "SELECT Payment_Id, Customer_Id, Balance, Gym_Id, Slot_Booking_Id FROM Payment WHERE Gym_Id = ?";

    private static final String SELECT_BOOKINGS_BY_GYM_ID_SQL = "SELECT Slot_Booking_Id, Customer_Id, Slot_Id, Gym_Id, Date, Is_Cancelled FROM Booking WHERE Gym_Id = ?";
    private static final String SELECT_USER_BY_ID_SQL = "SELECT * FROM user WHERE Role_Id = 2";
//    private static final String INSERT_SLOT_SQL = "INSERT INTO Gym_Slot ( Slot_Id,Start_Time, End_Time, Date, Total_Seats, Avail_Seat, Gym_Id,) VALUES (?,?,?,?,?,?,?)";
private static final String INSERT_SLOT_SQL = "INSERT INTO Gym_Slot (Slot_Id, Start_Time, End_Time, Date, Total_Seats, Avail_Seats, Gym_Id) VALUES (?,?,?,?,?,?,?)";
    public int getLatestId() {
        int latestId = -1; // Default value if no record is found
        String sql = "SELECT Slot_Id FROM Flipfit_Schema.Gym_Slot ORDER BY Slot_Id DESC LIMIT 1";

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

    public boolean addSlot(Slot gSlot) {
        // Basic validation for essential slot details before DB interaction

        if (gSlot == null || gSlot.getGymId() == 0 || gSlot.getSlotStartTime() == null || gSlot.getSlotEndTime() == null ) {
            System.err.println("Error: Cannot add slot. Missing Gym ID, Start Time, End Time");
            return false;
        }



        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish connection to MySQL using DriverManager (as in registerGymCenter)
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");

            // Prepare statement to insert data into 'Gym_Slot' table.
            // Statement.RETURN_GENERATED_KEYS is NOT used here for Slot_Id because it's not AUTO_INCREMENT.
            preparedStatement = connection.prepareStatement(INSERT_SLOT_SQL);

            // Set parameters for the SQL query.
            // The order and types MUST match INSERT_SLOT_SQL columns:
            // (Slot_Id, Gym_Id, Start_Time, End_Time, Date, Total_Seats, Avail_Seats)

            int slotId = getLatestId();
            slotId++;
            preparedStatement.setInt(1, slotId);// Provide the Slot_Id
            preparedStatement.setInt(7, gSlot.getGymId());  // Provide the Gym_Id

            // Start_Time and End_Time are VARCHAR(45) in your DDL, so send as String
            preparedStatement.setString(2, gSlot.getSlotStartTime().toString()); // Convert LocalTime to String
            preparedStatement.setString(3, gSlot.getSlotEndTime().toString());   // Convert LocalTime to String

            // Date is DATE in your DDL, so convert String (YYYY-MM-DD) to java.sql.Date
            String hardcodedDateString = "2024-07-24";
            // Convert the string to java.sql.Date
            java.sql.Date sqlDate = java.sql.Date.valueOf(hardcodedDateString);
            preparedStatement.setDate(4, sqlDate);
            preparedStatement.setInt(5, 40);
            preparedStatement.setInt(6, 12);



            int rowsAffected = preparedStatement.executeUpdate(); // Execute the INSERT query

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) inserted for Slot. ID: " + gSlot.getSlotId()); // Match example print
                return true;
            } else {
                System.err.println("DB Error: No rows affected. Failed to add slot.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("DB Error adding slot: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
            return false;
        }
    }


//    public boolean addSlot(Slot gSlot)
//    {
//        if (gSlot == null) {
//            return false;
//        }
//
//        gymSlotDB.put(gSlot.getSlotId(), gSlot);
//        System.out.println("Mock: Registered gym center with slot id:  " + gSlot.getSlotId() + " start time: " + gSlot.getSlotStartTime() + " end time: " + gSlot.getSlotEndTime());
//        return true;
//    }

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

    public void viewUsers(int GymUserId) {
        System.out.println("--- User Data for User ID: " + GymUserId + " ---");

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        GymUser user = null; // Initialize user as null

        try {
            // Get connection (using DBUtils as intended, or DriverManager for direct demo)
            // Using DriverManager directly here as per your snippet's DB_URL/USER/PASSWORD
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");

            statement = connection.prepareStatement(SELECT_USER_BY_ID_SQL);
//            statement.setInt(1, GymUserId); // Set the user_id parameter

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // User found, populate GymUser bean
                user = new GymUser();
                user.setGymUserId(resultSet.getInt("User_Id")); // Map DB column 'user_id' to GymUserId
                user.setGymUserName(resultSet.getString("Username")); // Map DB column 'username'
                user.setGymUserRole(resultSet.getString("Role_Id")); // Map DB column 'GymUserRole'
                user.setGymUserPassword(resultSet.getString("Password")); // Map DB column 'GymUserPassword'
                user.setGymUserEmail(resultSet.getString("Email")); // Map DB column 'GymUserEmail'
                user.setPhoneno(resultSet.getInt("PhoneNo"));

                // Print details of the user object
                System.out.println("GymUserID: " + user.getGymUserId());
                System.out.println("Name: " + user.getGymUserName());
                System.out.println("Email: " + user.getGymUserEmail());
                System.out.println("Role: " + user.getGymUserRole());
                System.out.println("Phone No: " + user.getPhoneno());


//                return true; // User was found and data displayed
            }

        } catch (SQLException e) {
            System.err.println("Database error retrieving user: " + e.getMessage());
            //e.printStackTrace();
//            return false;
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Ensure the JAR is in your classpath.");
            //e.printStackTrace();
//            return false;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while viewing user: " + e.getMessage());
            //e.printStackTrace();
//            return false;
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
                booking.isCancelled = !resultSet.getBoolean("Is_Cancelled");

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