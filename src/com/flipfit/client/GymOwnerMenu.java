package com.flipfit.client;
import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;
import com.flipfit.business.*;
import com.flipfit.dao.FlipFitAdminDAO;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//import static com.flipfit.client.FlipFitApplication.Scanner;
//import static com.flipfit.utils.Util.*;
import java.util.Scanner;

public class GymOwnerMenu {


    private Gym gym =new Gym();
    static OwnerBusiness gymOwnerBusiness = new OwnerBusiness();
    static SlotBusiness gymSlotBusiness = new SlotBusiness();
    static GymBusiness gymCentreBusiness = new GymBusiness();

    public static boolean gymOwnerLogin(String userName, String password) {
        if (gymOwnerBusiness.loginGymOwner(userName, password)) {
            System.out.println("Successfully logged in");

            System.out.println("Navigating to gym owner main page for: " + userName);

        } else {
            return false;
        }
        return true;
    }

    public static void addGym( String GymName, String address) {
        gymOwnerBusiness.add_gym( GymName, address);
        //System.out.println("Gym Owner Added");
    }

    public static void viewBookings(int GymId) {
        gymOwnerBusiness.view_bookings(GymId);
        // System.out.println("Gym Owner View Bookings");
    }
    public static void viewPayments(int GymId) {
        gymOwnerBusiness.view_payments(GymId);
        //System.out.println("Gym Owner View Payments");
    }
    public static void viewUserData(int GymUserId){
        gymOwnerBusiness.view_userdata(GymUserId);
    }
    public static void addSlot(int GymId,String starttime,String endTime) {
        gymOwnerBusiness.add_slot(GymId,starttime,endTime);
    }



    public void OwnerMenu()
    {
        Scanner scanner = new Scanner(System.in);
        String gymName,address,starttime,endTime;
        int gymId,ownerId;


        while(true)
        {
            System.out.println("\n====== Gym Owner Menu ======");
            System.out.println("1. Add Gym");
            System.out.println("2. View Bookings");
            System.out.println("3. View Payments");
            System.out.println("4. View User Data");
            System.out.println("5. Add Slot");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Gym Name: ");
                    gymName = scanner.nextLine();
                    System.out.print("Enter Gym Address: ");
                    address = scanner.nextLine();
//                    System.out.print("Enter Gym OwnerID: ");
//                    ownerId = scanner.nextInt();

                    addGym(gymName,address);
                    break;

                case 2:
                    System.out.print("Enter Gym ID to view bookings: ");
                    int gymIdBookings = scanner.nextInt();
                    viewBookings(gymIdBookings);
                    break;

                case 3:
                    System.out.print("Enter Gym ID to view payments: ");
                    int gymIdPayments = scanner.nextInt();
                    viewPayments(gymIdPayments);
                    break;

                case 4:
//                    System.out.print("Enter Gym User ID to view user data: ");
                    int gymUserId = 0;
                    viewUserData(gymUserId);
                    break;

                case 5:
                    FlipFitAdminDAO flipFitAdminDAO = new FlipFitAdminDAO();
                    flipFitAdminDAO.getGymById();
                    System.out.print("Enter Gym Id: ");
                    gymId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Start time: ");
                    starttime= scanner.nextLine();

                    System.out.print("Enter End time: ");
                    endTime = scanner.nextLine();

                    addSlot(gymId,starttime,endTime);
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
