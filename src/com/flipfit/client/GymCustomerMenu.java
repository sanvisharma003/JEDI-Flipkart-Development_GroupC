package com.flipfit.client;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Gym;
import com.flipfit.bean.Slot;
import com.flipfit.business.CustomerBusiness;
import com.flipfit.business.CustomerBusinessInterface;
import java.util.List;
import java.util.Scanner;

public class GymCustomerMenu {

    private static final CustomerBusinessInterface customerService = new CustomerBusiness();
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * This new method guides the user through the entire booking process.
     * @param userId The ID of the logged-in user.
     */
    private void bookSlotWorkflow(int userId) {
        System.out.println("\n--- Book a Slot ---");

        // 1. Show available gyms
        System.out.println("Available Gyms:");
        List<Gym> gyms = customerService.viewAllGyms();
        gyms.forEach(gym -> System.out.println(" -> ID: " + gym.getGymId() + ", Name: " + gym.getGymName()));
        System.out.print("Select a Gym ID: ");
        int gymId = scanner.nextInt();

        // 2. Show available slots for the chosen gym
        System.out.println("\nAvailable Slots for Gym " + gymId + ":");
        List<Slot> slots = customerService.viewSlotsForGym(gymId);
        if(slots.isEmpty()){
            System.out.println("No available slots for this gym.");
            return;
        }
        slots.forEach(slot -> System.out.println(" -> Slot ID: " + slot.getSlotId() + ", Time: " + slot.getSlotStartTime() + ", Seats Left: " + slot.getAvailableSeats()));
        System.out.print("Select a Slot ID: ");
        int slotId = scanner.nextInt();

        // 3. Attempt to book the slot using the correct method with all 3 arguments
        boolean booked = customerService.bookSlot(userId, gymId, slotId);
        if (booked) {
            System.out.println(" Slot booked successfully!");
        } else {
            System.out.println(" Failed to book slot. It might be full or the ID is incorrect.");
        }
    }

    private void viewMyBookings(int userId) {
        System.out.println("\n--- Your Bookings ---");
        List<Booking> myBookings = customerService.getMyBookings(userId);
        myBookings.forEach(b -> System.out.println(
                " -> Booking ID: " + b.getSlotBookingId() +
                        ", Gym ID: " + b.getGymId() +
                        ", Date: " + b.getDate() +
                        ", Status: " + (b.isCancelled() ? "Cancelled" : "Confirmed")
        ));
    }

    private void cancelBookingWorkflow(int userId) {
        viewMyBookings(userId); // Show bookings first
        System.out.print("\nEnter the Booking ID to cancel: ");
        int bookingId = scanner.nextInt();
        if (customerService.cancelBooking(bookingId)) {
            System.out.println(" Booking cancelled successfully.");
        } else {
            System.out.println(" Failed to cancel booking.");
        }
    }

    private void changeBookingWorkflow(int userId) {
        System.out.println("\n--- Change Your Slot Booking ---");

        // Step 1: Show current bookings so the user can pick one to change.
        viewMyBookings(userId);
        System.out.print("\nEnter the Booking ID you want to change: ");
        int bookingIdToChange = scanner.nextInt();

        // Step 2: Show available gyms for the new booking.
        System.out.println("\nSelect a new gym and slot:");
        List<Gym> gyms = customerService.viewAllGyms();
        gyms.forEach(gym -> System.out.println(" -> Gym ID: " + gym.getGymId() + ", Name: " + gym.getGymName()));
        System.out.print("Select a new Gym ID: ");
        int newGymId = scanner.nextInt();

        // Step 3: Show available slots for the new gym.
        List<Slot> slots = customerService.viewSlotsForGym(newGymId);
        slots.forEach(slot -> System.out.println(" -> Slot ID: " + slot.getSlotId() + ", Time: " + slot.getSlotStartTime() + ", Seats Left: " + slot.getAvailableSeats()));
        System.out.print("Select a new Slot ID: ");
        int newSlotId = scanner.nextInt();

        // Step 4: Call the business logic to perform the change.
        if (customerService.changeBooking(bookingIdToChange, userId, newGymId, newSlotId)) {
            System.out.println(" Booking changed successfully!");
        } else {
            System.out.println(" Failed to change booking.");
        }
    }

    public void showCustomerDashboard(int userId) {
        System.out.println("\n--- Customer Menu ---");
        while (true) {
            System.out.println("\n1. Book a Slot");
            System.out.println("2. View My Bookings");
            System.out.println("3. Cancel a Booking");
            System.out.println("4. Change a Booking");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // Call the new workflow method
                    bookSlotWorkflow(userId);
                    break;
                case 2:
                    viewMyBookings(userId);
                    break;
                case 3:
                    cancelBookingWorkflow(userId);
                    break;
                case 4:
                    changeBookingWorkflow(userId);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
