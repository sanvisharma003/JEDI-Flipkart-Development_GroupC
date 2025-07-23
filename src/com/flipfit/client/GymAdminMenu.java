package com.flipfit.client;

//import com.flipfit.bean.GymAdmin;
//import com.flipfit.bean.Gym;
//import com.flipfit.bean.GymOwner;
//import com.flipfit.bean.Slot;
import com.flipfit.business.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//import static com.flipfit.utils.Util.*;

//FlipFitAdminDAO flipFitAdminDAO = new FlipFitAdminDAO();


public class GymAdminMenu {
//    private static GymAdmin admin = new FlipFitAdmin();
//    private static FlipFitAdminInterface adminService = new FlipFitAdminService();
//    private static FlipFitGymOwnerInterface gymOwnerService = new FlipFitGymOwnerService();
//    private static FlipFitGymCenterInterface gymCenterService = new FlipFitGymCenterService();

//    private static AdminBusiness gymAdminBusiness = new AdminBusiness();
//    private static SlotBusiness gymSlotBusiness = new SlotBusiness();
//    private static OwnerBusiness gymOwnerBusiness = new OwnerBusiness();

private static AdminBusinessInterface gymAdminBusiness;
private static SlotBusinessInterface gymSlotBusiness;
 private static OwnerBusinessInterface gymOwnerBusiness;

 public static Scanner scanner = new Scanner(System.in);



    public boolean adminLogin(String userName, String password) {

        System.out.println("Successfully logged in");
//        adminClientMainPage();
        return true;
    }

    private void handleGymOwnerApprovalRequests(){
        // print the list with indexes from 1
        System.out.println("Admin Approval for a Gym Owner ----------");

        System.out.println("(Press 0 to exit)\nEnter the Id of Gym Owner:");
        String requestGymOwnerId = scanner.next();

        if(requestGymOwnerId.equals("0")) {return;}

        System.out.println("1. Approve the request\n2. Reject the request");
        int choice = scanner.nextInt();
        if(choice == 1){
            gymAdminBusiness.approval(Integer.parseInt(requestGymOwnerId));
            System.out.println("Approved Gym Owner");
        } else if (choice == 2) {
            gymAdminBusiness.approval(Integer.parseInt(requestGymOwnerId));
            System.out.println("Disapproved Gym Owner");
        }

    }
    private void handleGymCenterApprovalRequests(){
        // print the list with indexes from 1
        System.out.println("Press 0 to Exit or Choose the Gym Centre To Modify:");
        String requestGymCenterId = scanner.next();
        if (requestGymCenterId.equals("0")) return;
//            Now Admin will select a request and we will pop up with two
        System.out.println("1. Approve the request\n2. Reject the request\n");
        int choice = scanner.nextInt();
        if(choice == 1){
            gymAdminBusiness.approval(Integer.parseInt(requestGymCenterId));
            System.out.println("Approved Gym Centre");
        } else if (choice == 2) {
            gymAdminBusiness.approval(Integer.parseInt(requestGymCenterId));
            System.out.println("Disapproved Gym Centre");
        }
        //modify the list
//            adminClientMainPage();
    }

    public void GAMenu()
    {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = currentTime.format(myFormat);
        System.out.println("Welcome ADMIN to FlipFit Application\nLogin Time: "+ currentTime);
        while(true)
        {
            System.out.println("1. Approvals\n2. View an Owner\n3. View all Gym\n4. Add an owner\n5. View payments \n6. cancel Approval\n7.Go Back To Previous Menu");
            int pendingChoice = scanner.nextInt();
            switch (pendingChoice) {
                case 1:
                    System.out.println("Enter ID of owner for approvals");
                    break;
                case 2:
                    System.out.println("Enter ID of owner who you want to view");
                    int ownerId = scanner.nextInt();
                    gymAdminBusiness.view_owner(ownerId);
//                    printOwnerList(allGymOwners);
                    break;
                case 3:
                    System.out.println("Enter ID of gym who you want to view");
                    int gymId = scanner.nextInt();
                    gymAdminBusiness.view_gym(gymId);
//                    printGymCentres(allGymCenters);
                    break;
                case 4:
                    //Fetch owner details like username, age, etc from the console and store it in variable ownerDetails
//                    printOwnerList(pendingGymOwners);
//                    gymAdminBusiness.add_owner(ownerDetails);
                    break;

                case 5:
                    gymAdminBusiness.view_all_payments();
                    break;
                case 6:
                    System.out.println("Cancel Approval");
                    break;
                case 7:
                    return;
            }
        }
    }
}