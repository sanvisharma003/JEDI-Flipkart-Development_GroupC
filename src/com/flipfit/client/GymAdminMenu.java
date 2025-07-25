package com.flipfit.client;

import com.flipfit.bean.Gym;
import com.flipfit.bean.GymOwner;
import com.flipfit.business.AdminBusiness;
import com.flipfit.business.AdminBusinessInterface;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

/**
 * Provides a command-line interface for the Administrator.
 .*/
public class GymAdminMenu {

    private static final AdminBusinessInterface adminBusiness = new AdminBusiness();
    private static final Scanner scanner = new Scanner(System.in);
    private static final String GET_GYM_APPROVAL = "SELECT * FROM Gym WHERE is_approved = 0";


    public void GAMenu() {
        System.out.println("\nWelcome ADMIN to FlipFit Application");
        while (true) {
            System.out.println("\n====== Admin Menu ======");
            System.out.println("1. Handle Approval Requests");
            System.out.println("2. View a Gym Owner");
            System.out.println("3. View a Gym");
            System.out.println("4. Go Back To Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    handleApprovalMenu();
                    break;
                case 2:
                    adminBusiness.viewOwner();
                    break;
                case 3:
                    //System.out.print("Enter Gym ID to view: ");
                    //int gymId = scanner.nextInt();
                    adminBusiness.viewGym();
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleApprovalMenu()
    {
//        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");
//             PreparedStatement preparedStatement = connection.prepareStatement(GET_GYM_APPROVAL))
//        {
//            ResultSet rs = preparedStatement.executeQuery();
//
//            // If a record is found, map the data to a new Gym object.
//            while (rs.next())
//            {
//                System.out.println("\n--- Gym Details ---");
//                System.out.println("ID: " + rs.getInt("Gym_Id"));
//                System.out.println("Name: " + rs.getString("Gym_Name"));
//                System.out.println("Location: " + rs.getString("Location"));
//                System.out.println("-------------------");
//                //gym.setLocation(rs.getString("location"));
//            }
//        } catch (Exception e) {
//            System.err.println("Invalid Gym ID format. Please provide a numeric ID. " + e.getMessage());
//        }

        adminBusiness.viewGym();

        System.out.println("Enter Gym Id for the gym you want to approve");
        int gymid=scanner.nextInt();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `Gym` SET `is_approved` = '1' WHERE (`Gym_Id` = ?);"))
        {
            preparedStatement.setInt(1, gymid);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.err.println("Invalid Gym ID format. Please provide a numeric ID. " + e.getMessage());
        }
        adminBusiness.viewGym();

    }


}