

package com.flipfit.client;

//import com.flipfit.bean.GymUser;
//import com.flipfit.bean.Gym;
//import com.flipfit.bean.Payment;
//import com.flipfit.bean.Booking;
//import com.flipfit.bean.GymOwner;

import com.flipfit.business.CustomerBusiness;

import java.util.*;
        import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class GymCustomerMenu {

    public static CustomerBusiness customer = new CustomerBusiness();

    public static void getCustomerMenu() {
        System.out.println("Welcome to Customer Menu");
    }

    public static void updateSlotBooking(int userId,int slotId)
    {
        boolean b = customer.update_slots(userId,slotId);
        System.out.println(b);
    }

    public static void viewBooking(int userId)
    {
        customer.view_bookings(userId);
        //add useid and gym id
    }
    public static void cancleSlotBooking(int userId,int slotId)
    {
        customer.cancel_slots(userId,slotId);
        //userid and slotid
    }

    public static void bookSlot(int userId){
        customer.book_slot(userId);
        //userid slotid
        //take some fixed amount
        customer.payment();

    }


    public void GCMenu(int userId) throws ParseException
    {
//    public static void main(String args[]){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = currentTime.format(myFormat);
//        String userName ="sanvi";
        Scanner sc = new Scanner(System.in);

        getCustomerMenu();

        System.out.println("WELCOME " + userId + " !!\nPlease choose among the following options\nLogin TIME: " + currentTime);
        while (true) {
            System.out.println("1. Book a slot in a Gym  \n2. Update Slot Booking \n3. View Bookings\n4. Cancel Bookings\n5. Go Back to previous menu");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    bookSlot(userId);
                    break;
                case 2:

                    List<Integer> lt = new ArrayList<>();
                    System.out.println("Select slotId");

                    int slotId = sc.nextInt();

                    updateSlotBooking(userId,slotId);
                    break;
                case 3:
                    viewBooking(userId);
                    break;
                case 4:
                    List<Integer> lt1 = new ArrayList<>();
                    System.out.println("Select slotId");
                    int slotId1 = sc.nextInt();
                    cancleSlotBooking(userId,slotId1);
                    break;
                case 5:
                    System.out.println("Taking back to previous menu");
                    return;
                default:
                    System.out.println("Not a valid choice");
                    break;
            }
        }
    }
}

