//package com.flipfit.client;
//
////import com.flipfit.bean.GymAdmin;
////import com.flipfit.bean.Gym;
////import com.flipfit.bean.GymOwner;
////import com.flipfit.bean.Slot;
//import com.flipfit.business.*;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
////import static com.flipfit.utils.Util.*;
//
////FlipFitAdminDAO flipFitAdminDAO = new FlipFitAdminDAO();
//
//
//public class GymAdminMenu {
////    private static GymAdmin admin = new FlipFitAdmin();
////    private static FlipFitAdminInterface adminService = new FlipFitAdminService();
////    private static FlipFitGymOwnerInterface gymOwnerService = new FlipFitGymOwnerService();
////    private static FlipFitGymCenterInterface gymCenterService = new FlipFitGymCenterService();
//
////    private static AdminBusiness gymAdminBusiness = new AdminBusiness();
////    private static SlotBusiness gymSlotBusiness = new SlotBusiness();
////    private static OwnerBusiness gymOwnerBusiness = new OwnerBusiness();
//
//private static AdminBusinessInterface gymAdminBusiness;
//private static SlotBusinessInterface gymSlotBusiness;
// private static OwnerBusinessInterface gymOwnerBusiness;
//
// public static Scanner scanner = new Scanner(System.in);
//
//
//
//    public boolean adminLogin(String userName, String password) {
//
//        System.out.println("Successfully logged in");
////        adminClientMainPage();
//        return true;
//    }
//
//    private void handleGymOwnerApprovalRequests(){
//        // print the list with indexes from 1
//        System.out.println("Admin Approval for a Gym Owner ----------");
//
//        System.out.println("(Press 0 to exit)\nEnter the Id of Gym Owner:");
//        String requestGymOwnerId = scanner.next();
//
//        if(requestGymOwnerId.equals("0")) {return;}
//
//        System.out.println("1. Approve the request\n2. Reject the request");
//        int choice = scanner.nextInt();
//        if(choice == 1){
//            gymAdminBusiness.approval(Integer.parseInt(requestGymOwnerId));
//            System.out.println("Approved Gym Owner");
//        } else if (choice == 2) {
//            gymAdminBusiness.approval(Integer.parseInt(requestGymOwnerId));
//            System.out.println("Disapproved Gym Owner");
//        }
//
//    }
//    private void handleGymCenterApprovalRequests(){
//        // print the list with indexes from 1
//        System.out.println("Press 0 to Exit or Choose the Gym Centre To Modify:");
//        String requestGymCenterId = scanner.next();
//        if (requestGymCenterId.equals("0")) return;
////            Now Admin will select a request and we will pop up with two
//        System.out.println("1. Approve the request\n2. Reject the request\n");
//        int choice = scanner.nextInt();
//        if(choice == 1){
//            gymAdminBusiness.approval(Integer.parseInt(requestGymCenterId));
//            System.out.println("Approved Gym Centre");
//        } else if (choice == 2) {
//            gymAdminBusiness.approval(Integer.parseInt(requestGymCenterId));
//            System.out.println("Disapproved Gym Centre");
//        }
//        //modify the list
////            adminClientMainPage();
//    }
//
//    public void GAMenu()
//    {
//        LocalDateTime currentTime = LocalDateTime.now();
//        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//        String formattedDate = currentTime.format(myFormat);
//        System.out.println("Welcome ADMIN to FlipFit Application\nLogin Time: "+ currentTime);
//        while(true)
//        {
//            System.out.println("1. Approvals\n2. View an Owner\n3. View all Gym\n4. Add an owner\n5. View payments \n6. cancel Approval\n7.Go Back To Previous Menu");
//            int pendingChoice = scanner.nextInt();
//            switch (pendingChoice) {
//                case 1:
//                    System.out.println("Enter ID of owner for approvals");
//                    break;
//                case 2:
//                    System.out.println("Enter ID of owner who you want to view");
//                    int ownerId = scanner.nextInt();
//                    gymAdminBusiness.view_owner(ownerId);
////                    printOwnerList(allGymOwners);
//                    break;
//                case 3:
//                    System.out.println("Enter ID of gym who you want to view");
//                    int gymId = scanner.nextInt();
//                    gymAdminBusiness.view_gym(gymId);
////                    printGymCentres(allGymCenters);
//                    break;
//                case 4:
//                    //Fetch owner details like username, age, etc from the console and store it in variable ownerDetails
////                    printOwnerList(pendingGymOwners);
////                    gymAdminBusiness.add_owner(ownerDetails);
//                    break;
//
//                case 5:
//                    gymAdminBusiness.view_all_payments();
//                    break;
//                case 6:
//                    System.out.println("Cancel Approval");
//                    break;
//                case 7:
//                    return;
//            }
//        }
//    }
//}
package com.flipfit.client;

import com.flipfit.bean.Gym;
import com.flipfit.bean.GymOwner;
import com.flipfit.business.AdminBusiness;
import com.flipfit.business.AdminBusinessInterface;
import java.util.List;
import java.util.Scanner;

/**
 * Provides a command-line interface for the Administrator.
 .*/
public class GymAdminMenu {

    private static final AdminBusinessInterface adminBusiness = new AdminBusiness();
    private static final Scanner scanner = new Scanner(System.in);

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
                    System.out.print("Enter Owner ID (PAN) to view: ");
                    String ownerId = scanner.nextLine();
                    adminBusiness.viewOwner(ownerId);
                    break;
                case 3:
                    System.out.print("Enter Gym ID to view: ");
                    String gymId = scanner.nextLine();
                    adminBusiness.viewGym(gymId);
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleApprovalMenu() {
        System.out.println("\n--- Approval Sub-Menu ---");
        System.out.println("1. Approve/Reject Gym Owners");
        System.out.println("2. Approve/Reject Gyms");
        System.out.println("3. Back to Admin Menu");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                handleGymOwnerApprovalRequests();
                break;
            case 2:
                handleGymCenterApprovalRequests();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void handleGymOwnerApprovalRequests() {
        List<GymOwner> pendingOwners = adminBusiness.getPendingOwners();
        if (pendingOwners.isEmpty()) {
            System.out.println("\nNo pending gym owner requests.");
            return;
        }

        System.out.println("\n--- Pending Gym Owner Approvals ---");
        for (GymOwner owner : pendingOwners) {
            System.out.println("ID (PAN): " + owner.getPAN() + " | Name: " + owner.getGymUserName());
        }

        System.out.print("\nEnter the ID (PAN) of the owner to process (or type 'back'): ");
        String ownerId = scanner.nextLine();
        if (ownerId.equalsIgnoreCase("back")) return;

        System.out.println("1. Approve");
        System.out.println("2. Reject");
        System.out.print("Choose action: ");
        int action = scanner.nextInt();
        scanner.nextLine();

        if (action == 1) {
            adminBusiness.approveOwnerRequest(ownerId);
        } else if (action == 2) {
            adminBusiness.rejectOwnerRequest(ownerId);
        } else {
            System.out.println("Invalid action.");
        }
    }

    private void handleGymCenterApprovalRequests() {
        List<Gym> pendingGyms = adminBusiness.getPendingGyms();
        if (pendingGyms.isEmpty()) {
            System.out.println("\nNo pending gym center requests.");
            return;
        }

        System.out.println("\n--- Pending Gym Center Approvals ---");
        for (Gym gym : pendingGyms) {
            System.out.println("ID: " + gym.getGymId() + " | Name: " + gym.getGymName());
        }

        System.out.print("\nEnter the ID of the gym to process (or type 'back'): ");
        String gymId = scanner.nextLine();
        if (gymId.equalsIgnoreCase("back")) return;

        System.out.println("1. Approve");
        System.out.println("2. Reject");
        System.out.print("Choose action: ");
        int action = scanner.nextInt();
        scanner.nextLine();

        if (action == 1) {
            adminBusiness.approveGymRequest(gymId);
        } else if (action == 2) {
            adminBusiness.rejectGymRequest(gymId);
        } else {
            System.out.println("Invalid action.");
        }
    }
}