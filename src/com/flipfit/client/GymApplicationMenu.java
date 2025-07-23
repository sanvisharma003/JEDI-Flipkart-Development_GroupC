package com.flipfit.client;

//import com.flipfit.exceptions.LoginFailedException;

import java.text.ParseException;
import java.util.Scanner;
import com.flipfit.client.*;


public class GymApplicationMenu {
    public static Scanner scanner = new Scanner(System.in);
    private static GymAdminMenu adminClient = new GymAdminMenu();
    private static GymCustomerMenu customerClient = new GymCustomerMenu();
    private static GymOwnerMenu gymOwnerClient = new GymOwnerMenu();


    private static void mainPage(){
//        System.out.println("WELCOME TO FLIPFIT APPLICATION");
//        System.out.println("Enter your choice:");
        System.out.println("1. Login\n2. Registration\n3. Change Password\n4. Exit");
        int choice = scanner.nextInt();
       while(true)
       {
           switch (choice) {
               case 1:
                   login();
                   break;
               case 2:
                    registration();
                    break;

//            case 3:
//                changePassword();
               case 4:
                   System.out.println("Thanks for visiting!");
                   return;
               default:
                   System.out.println("Invalid choice selected");
                   break;
           }
           mainPage();
       }

    }

    private static void log()
    {
        String userName, password;
        System.out.println("Enter your Username");
        userName = scanner.next();

        System.out.println("Enter your Password");
        password = scanner.next();
    }

    private static void login(){
        try {

            System.out.println("Enter your Role \n 1 Admin \n 2 Customer \n 3 Gym Owner \n 4 Exit");
            int role = scanner.nextInt();

            switch(role)
            {
                case 1:
                    log();
                    GymAdminMenu gam = new  GymAdminMenu();
                    gam.GAMenu();

                    break;

                case 2:

                    log();
                    GymCustomerMenu gcm = new GymCustomerMenu();
                    gcm.GCMenu(1000);
                    break;

                case 3:
                    log();

                    GymOwnerMenu gom = new GymOwnerMenu();
                    gom.OwnerMenu();
                    break;

            }
//
        }
        catch (Exception e) {
//            IllegalArgumentException | ParseException | LoginFailedException e
            System.out.println("Invalid Option Selected");
        }
    }

    private static void registration()
    {
            System.out.println("Enter your role \n1. Owner\n2. Customer");
            int cho= scanner.nextInt();

            switch(cho)
            {
                case 1:
                    System.out.println("Registered as Owner");
                    break;
                case 2:
                    System.out.println("Registered as Customer");
                    break;
            }

    }

    public static void main(String[] args) {
        System.out.println("WELCOME TO FLIPFIT APPLICATION");
        System.out.println("Enter your choice:");
        mainPage();
    }

}