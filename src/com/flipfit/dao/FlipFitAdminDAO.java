package com.flipfit.dao;
import com.flipfit.bean.Gym;
import com.flipfit.bean.GymOwner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.regex.*;

/**
 * Data Access Object for Admin functionalities.
 * Manages database interactions for Gyms and GymOwners.
 */
public class FlipFitAdminDAO implements FlipFitAdminDAOInterface {

    // In-memory storage for pending approvals (can be migrated to DB status flags)
    public class EmailValidator {
        public static boolean isValidEmail(String email) {
            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
    }
    private static final Map<String, GymOwner> gymOwners = new HashMap<>();
    private static final Map<String, Gym> gyms = new HashMap<>();
    private static final Map<String, Boolean> ownerApprovalStatus = new HashMap<>();
    private static final Map<String, Boolean> gymApprovalStatus = new HashMap<>();

    // SQL query to fetch a gym's details by its ID
    private static final String GET_GYM_BY_ID_SQL = "SELECT * FROM Gym";
    private static final String GET_PENDING_OWNERS_SQL = "SELECT u.Username, o.* FROM Owner o JOIN User u ON o.user_Id = u.User_Id WHERE o.is_approved = 0";
    private static final String VALIDATE_OWNER_SQL = "UPDATE Owner SET is_approved =? WHERE PAN =?";
    private static final String GET_PENDING_GYMS_SQL = "SELECT * FROM Gym WHERE is_approved = 0";
    private static final String VALIDATE_GYM_SQL = "UPDATE Gym SET is_approved =? WHERE Gym_Id =?";
    private static final String GET_OWNER = "SELECT Owner_Id, Email, Phone, Gym_Id, PAN, Aadhar, GSTIN from Owner";
    //private static final String GET_GYM_BY_ID_SQL = "SELECT Gym_Id, Owner_Id, Gym_Name, location FROM Gym WHERE Gym_Id =?";

    @Override
    public List<GymOwner> getPendingGymOwners() {
        return gymOwners.keySet().stream()
                .filter(ownerId ->!ownerApprovalStatus.getOrDefault(ownerId, true))
                .map(gymOwners::get)
                .collect(Collectors.toList());
    }

    @Override
    public void validateGymOwner(String ownerId, boolean isApproved) {
        if (ownerApprovalStatus.containsKey(ownerId)) {
            ownerApprovalStatus.put(ownerId, isApproved);
            String status = isApproved? "Approved" : "Rejected";
            System.out.println("DAO: Gym Owner with ID " + ownerId + " has been " + status + ".");
        } else {
            System.out.println("DAO: Gym Owner with ID " + ownerId + " not found.");
        }
    }

    @Override
    public List<Gym> getPendingGymCentres() {
        return gyms.keySet().stream()
                .filter(gymId ->!gymApprovalStatus.getOrDefault(gymId, true))
                .map(gyms::get)
                .collect(Collectors.toList());
    }

    @Override
    public void validateGymCentre(String gymCentreId, boolean isApproved) {
        if (gymApprovalStatus.containsKey(gymCentreId)) {
            gymApprovalStatus.put(gymCentreId, isApproved);
            String status = isApproved? "Approved" : "Rejected";
            System.out.println("DAO: Gym Centre with ID " + gymCentreId + " has been " + status + ".");
        } else {
            System.out.println("DAO: Gym Centre with ID " + gymCentreId + " not found.");
        }
    }

//    @Override
//    public GymOwner getOwnerById(String ownerId) {
//        return
//                gymOwners.get(ownerId);
//    }
public GymOwner getOwnerById() {
    GymOwner owner = null;
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");
         PreparedStatement preparedStatement = connection.prepareStatement(GET_OWNER)) {

        ResultSet rs = preparedStatement.executeQuery();

        // If a record is found, map the data to a new Gym object.
        while (rs.next())
        {
            System.out.println("\n--- Owner Details ---");
            System.out.println("ID: " + rs.getInt("Owner_Id"));
            System.out.println("Email: " + rs.getString("Email"));
            System.out.println("Gym_Id: " + rs.getString("Gym_Id"));
            System.out.println("PAN: " + rs.getString("PAN"));
            System.out.println("AAdhar: " + rs.getString("Aadhar"));
            System.out.println("GSTIN: " + rs.getString("GSTIN"));

            System.out.println("-------------------");
        }

    } catch (SQLException e) {
        System.err.println("SQL Exception occurred while fetching owner by ID: " + e.getMessage());
        e.printStackTrace();
    }
    return owner;
}

    @Override
    public void getGymById() {
        //Gym gym = null;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");
             PreparedStatement preparedStatement = connection.prepareStatement(GET_GYM_BY_ID_SQL)) {

            //preparedStatement.setInt(1, gymId);

            ResultSet rs = preparedStatement.executeQuery();

            // If a record is found, map the data to a new Gym object.
            while (rs.next())
            {
                System.out.println("\n--- Gym Details ---");
                System.out.println("ID: " + rs.getInt("Gym_Id"));
                System.out.println("Name: " + rs.getString("Gym_Name"));
                System.out.println("Location: " + rs.getString("Location"));
                System.out.println("Is Approved: " + rs.getBoolean("is_approved"));
                System.out.println("-------------------");
                //gym.setLocation(rs.getString("location"));
            }

        } catch (SQLException e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Invalid Gym ID format. Please provide a numeric ID. " + e.getMessage());
        }

    }
}