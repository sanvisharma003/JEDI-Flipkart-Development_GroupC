package com.flipfit.client;
import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;
import com.flipfit.business.*;
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

    public static void addGym() {
        gymOwnerBusiness.add_gym();
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
    public static void addSlot(int GymId,int SlotId) {
        gymOwnerBusiness.add_slot(GymId,SlotId);
    }
    /// /////////////////////////////////



    public void OwnerMenu()
    {
        Scanner scanner = new Scanner(System.in);

        while(true)
        {
            System.out.println("\n====== Gym Owner Menu ======");
            System.out.println("1. Add Gym");
            System.out.println("2. View Bookings");
            System.out.println("3. View Payments");
            System.out.println("4. View User Data");
            System.out.println("5. Add Slot");
            System.out.println("6. Edit Profile");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addGym();
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
                    System.out.print("Enter Gym User ID to view user data: ");
                    int gymUserId = scanner.nextInt();
                    viewUserData(gymUserId);
                    break;

                case 5:
                    System.out.print("Enter Gym ID: ");
                    int gymIdSlot = scanner.nextInt();
                    System.out.print("Enter Slot ID: ");
                    int slotId = scanner.nextInt();
                    addSlot(gymIdSlot, slotId);
                    break;

                case 6:
                    System.out.print("Edit profile");
                    break;

                case 0:
                    System.out.println("Exiting menu.");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
