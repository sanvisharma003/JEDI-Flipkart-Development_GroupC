package com.flipfit.client;

import com.flipfit.dao.FlipFitCustomerDAO;
import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;
import com.flipfit.business.CustomerBusiness;
import com.flipfit.business.CustomerBusinessInterface;
import com.flipfit.dao.FlipFitCustomerDAO;

import java.util.List;
import java.util.Scanner;

public class GymCustomerMenu {

    private static final CustomerBusinessInterface customerService = new CustomerBusiness();
    private static final Scanner scanner = new Scanner(System.in);



//    private void viewMyBookings(int userId) {
//        System.out.println("\n--- Your Bookings ---");
//        List<Booking> myBookings = customerService.getMyBookings(userId);
//        myBookings.forEach(b -> System.out.println(
//                " -> Booking ID: " + b.getSlotBookingId() +
//                        ", Gym ID: " + b.getGymId() +
//                        ", Date: " + b.getDate() +
//                        ", Status: " + (b.isCancelled() ? "Cancelled" : "Confirmed")
//        ));
//    }
//
//    private void cancelBookingWorkflow(int userId) {
//        viewMyBookings(userId); // Show bookings first
//        System.out.print("\nEnter the Booking ID to cancel: ");
//        int bookingId = scanner.nextInt();
//        if (customerService.cancelBooking(bookingId)) {
//            System.out.println(" Booking cancelled successfully.");
//        } else {
//            System.out.println(" Failed to cancel booking.");
//        }
//    }
//
//    private void changeBookingWorkflow(int userId) {
//        System.out.println("\n--- Change Your Slot Booking ---");
//
//        // Step 1: Show current bookings so the user can pick one to change.
//        viewMyBookings(userId);
//        System.out.print("\nEnter the Booking ID you want to change: ");
//        int bookingIdToChange = scanner.nextInt();
//
//        // Step 2: Show available gyms for the new booking.
//        System.out.println("\nSelect a new gym and slot:");
//        List<Gym> gyms = customerService.viewAllGyms();
//        gyms.forEach(gym -> System.out.println(" -> Gym ID: " + gym.getGymId() + ", Name: " + gym.getGymName()));
//        System.out.print("Select a new Gym ID: ");
//        int newGymId = scanner.nextInt();
//
//        // Step 3: Show available slots for the new gym.
//        List<Slot> slots = customerService.viewSlotsForGym(newGymId);
//        slots.forEach(slot -> System.out.println(" -> Slot ID: " + slot.getSlotId() + ", Time: " + slot.getSlotStartTime() + ", Seats Left: " + slot.getAvailableSeats()));
//        System.out.print("Select a new Slot ID: ");
//        int newSlotId = scanner.nextInt();
//
//        // Step 4: Call the business logic to perform the change.
//        if (customerService.changeBooking(bookingIdToChange, userId, newGymId, newSlotId)) {
//            System.out.println(" Booking changed successfully!");
//        } else {
//            System.out.println(" Failed to change booking.");
//        }
//    }

    public void showCustomerDashboard()
    {
        System.out.println("\n--- Customer Menu ---");
        int userId;
        while (true) {
            System.out.println("0. View Gyms");
            System.out.println("1. Create Bookings");
            System.out.println("2. View My Bookings");
            System.out.println("3. Cancel a Booking");
//            System.out.println("4. Change a Booking");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            FlipFitCustomerDAO obj = new FlipFitCustomerDAO();
            switch (choice) {
                case 0:
                    obj.getAllGyms();
                    break;
                case 1:
                    System.out.println("Enter Customer ID");
                    int user_id = scanner.nextInt();
                    System.out.println("Enter Gym ID");
                    int gym_id = scanner.nextInt();
                    System.out.println("Enter Slot ID");
                    int slot_id = scanner.nextInt();
                    obj.createBooking(user_id, gym_id, slot_id);
                case 2:
                    System.out.println("Enter Customer ID");
                    userId = scanner.nextInt();
                    obj.viewMyBookings(userId);
                    break;
                case 3:
                    //cancelBookingWorkflow(userId);
                    System.out.println("Enter Customer ID to view your bookings");
                    userId = scanner.nextInt();
                    obj.viewMyBookings(userId);
                    System.out.println("Enter Booking ID");
                    int bookingId = scanner.nextInt();
                    obj.cancelBooking(bookingId);
                    break;
//                case 4:
//                    //changeBookingWorkflow(userId);
//                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
