package com.flipfit.dao;

import com.flipfit.bean.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList; // Needed if you want mutable lists as values
import java.util.Arrays;


public class GymOwnerDaoImpl implements GymOwnerDaoInterface {

    // Mock database using HashMap for Gym_Owner objects
    private static Map<Integer, GymOwner> gymOwnersDB = new HashMap<>();
    // Mock database using HashMap for Gym_Center objects
    private static Map<Integer, Gym> gymCentersDB = new HashMap<>();
    private static Map<Integer, Slot> gymSlotDB = new HashMap<>();
    //private static List<Integer> gymPaymentsDB = new ArrayList<>();
    private static Map<Integer, GymUser> gymUsersDB = new HashMap<>();
    private static Map<Integer, Booking> gymBookingsDB = new HashMap<>();



    // For generating unique IDs for mock data
    private static AtomicInteger ownerIdCounter = new AtomicInteger(1);
    private static AtomicInteger centerIdCounter = new AtomicInteger(1);
//    int[] payments= new int[]{2000, 1000, 5000};
    private static Map<Integer, List<Integer>> gymPaymentsDB = new HashMap<>();

    // Populate the map

    static {
        //sample payments
        gymPaymentsDB.put(123, new ArrayList<>(Arrays.asList(100, 200, 300))); // Use ArrayList for mutable lists
        gymPaymentsDB.put(456, new ArrayList<>(Arrays.asList(400, 500, 600)));
        gymPaymentsDB.put(789, new ArrayList<>(Arrays.asList(700, 800, 900)));
        // Static block to pre-populate some mock data for demonstration
        // Sample Gym Owners
        GymOwner owner1 = new GymOwner();
        owner1.setGymUserId(234);
        owner1.setGymUserEmail("owner1@example.com");
        owner1.setGymUserPassword("password123");
        owner1.setAadhar("111122223333");
        //owner1.setApproved(true);
        owner1.setPAN("9876543210");
        owner1.setGSTIN("GSTIN0001");
        owner1.setGymUserName("Jane Smith ");
        owner1.setGymUserRole("ADMIN");
        owner1.setPhoneno(777777777);
        owner1.setGymName("Jane Smith Fitness");
        owner1.setGymOwnerId(12);
        owner1.setLocation("Bellandur");
        //owner1.setAssociatedGyms();
        owner1.setGymId(1);
        gymOwnersDB.put(owner1.getGymUserId(), owner1);

        GymOwner owner2= new GymOwner();
        owner2.setGymUserId(456);
        owner2.setGymUserEmail("owner1@example.com");
        owner2.setGymUserPassword("password123");
        owner2.setAadhar("111122223333");
        //owner1.setApproved(true);
        owner2.setPAN("9876543210");
        owner2.setGSTIN("GSTIN0001");
        owner2.setGymUserName("Jane Smith ");
        owner2.setGymUserRole("ADMIN");
        owner2.setPhoneno(777777777);
        owner2.setGymName("Jane Smith Fitness");
        owner2.setGymOwnerId(12);
        owner2.setLocation("Bellandur");
        //owner1.setAssociatedGyms();
        owner2.setGymId(1);
        gymOwnersDB.put(owner2.getGymUserId(), owner2);

        // Sample Gym Centers for owner1
        Gym center1 = new Gym();
        center1.setGymId(centerIdCounter.getAndIncrement());
        center1.setGymOwnerId(owner1.getGymOwnerId());
        center1.setGymName("FitZone City Center");
        center1.setLocation("123 Main St, Anytown");
//        center1.setNo_of_slots(10);
//        center1.setApproved(true);
        gymCentersDB.put(center1.getGymId(), center1);

        Gym center2 = new Gym();
        center2.setGymId(centerIdCounter.getAndIncrement());
        center2.setGymOwnerId(owner1.getGymOwnerId());
        center2.setGymName("FitZone City Center");
        center2.setLocation("123 Main St, Anytown");
//        center1.setNo_of_slots(10);
//        center1.setApproved(true);
        gymCentersDB.put(center2.getGymId(), center2);

        //GYM USER data

        GymUser user1 = new GymUser();
        user1.setGymUserId(1); // Use the shared user ID counter
        user1.setGymUserName("Alice Customer");
        user1.setGymUserEmail("alice@example.com");
        user1.setGymUserPassword("userpass1");
        user1.setPhoneno(912); // Using L for long literal
        user1.setGymUserRole("CUSTOMER");
        gymUsersDB.put(user1.getGymUserId(), user1);

        GymUser user2 = new GymUser();
        user2.setGymUserId(2);
        user2.setGymUserName("David User");
        user2.setGymUserEmail("david@example.com");
        user2.setGymUserPassword("userpass2");
        user2.setPhoneno(987);
        user2.setGymUserRole("CUSTOMER");
        gymUsersDB.put(user2.getGymUserId(), user2);

//Bookings dummy data
        Booking booking1 = new Booking();
        booking1.slotBookingId = 1; // 1
        booking1.userId = 11; // 1003
        booking1.slotId = 111; // 1
        booking1.gymId = 1111; // 100
        booking1.date = "2025-07-25";
        booking1.isCancelled = false;
        gymBookingsDB.put(booking1.slotBookingId, booking1);

        // Booking 2: User 1004 (David), Slot 2, Gym 100, Date "2025-07-25", Not Cancelled
        // Using the provided dummy data values for this specific booking
        Booking booking2 = new Booking();
        booking2.slotBookingId = 2; // Fixed ID as per your dummy data
        booking2.userId = 22; // User ID as per your dummy data
        booking2.slotId = 222; // Slot ID as per your dummy data
        booking2.gymId = 1111; // Gym ID as per your dummy data
        booking2.date = "2025-07-25";
        booking2.isCancelled = false;
        gymBookingsDB.put(booking2.slotBookingId, booking2);

    }

    @Override
    public boolean registerGymCenter(Gym gCenter) {
        if (gCenter == null) {
            return false;
        }
        // Assign a new ID to the gym center
        //gCenter.setGymId(centerIdCounter.getAndIncrement());
        // By default, a newly registered center is not approved
        //gCenter.setApproved(false);
        gymCentersDB.put(gCenter.getGymId(), gCenter);
        System.out.println("Mock: Registered gym center: " + gCenter.getGymName() + " with gym ID: " + gCenter.getGymId() + " with gym owner: " + gCenter.getGymOwnerId()+ " Address "+gCenter.getLocation());
        return true;
    }
    public boolean addSlot(Slot gSlot) {
        if (gSlot == null) {
            return false;
        }
        // Assign a new ID to the gym center
        //gCenter.setGymId(centerIdCounter.getAndIncrement());
        // By default, a newly registered center is not approved
        //gCenter.setApproved(false);
        gymSlotDB.put(gSlot.getSlotId(), gSlot);
        System.out.println("Mock: Registered gym center with slot id:  " + gSlot.getSlotId() + " start time: " + gSlot.getSlotStartTime() + " end time: " + gSlot.getSlotEndTime());
        return true;
    }

    public boolean viewPayments(int GymId) {
        System.out.println("--- Payments for Gym ID: " + GymId + " ---");

        List<Integer> paymentsForGym = gymPaymentsDB.get(GymId);

        if (paymentsForGym != null && !paymentsForGym.isEmpty()) {
            for (int payment : paymentsForGym) {
                System.out.println("Payment: " + payment);
            }
            return true; // Payments were found and displayed
        } else {
            System.out.println("No payments found for Gym ID: " + GymId);
            return false; // No payments found
        }
    }

    public boolean viewUsers(int userId) { // Changed parameter name to 'userId' for clarity
        System.out.println("--- User Data for User ID: " + userId + " ---");

        GymUser user = gymUsersDB.get(userId); // Get the single GymUser object

        if (user != null) {
            // Print details of the single user object
            System.out.println("User ID: " + user.getGymUserId());
            System.out.println("Name: " + user.getGymUserName());
            System.out.println("Email: " + user.getGymUserEmail());
            // System.out.println("Password: " + user.getGymUserPassword()); // Usually don't print passwords
            System.out.println("Phone No: " + user.getPhoneno());
            System.out.println("Role: " + user.getGymUserRole());
            return true; // User was found and data displayed
        } else {
            System.out.println("No user found for User ID: " + userId);
            return false; // No user found
        }
    }
//    public boolean viewBookings(int gymId) { // Changed parameter name to 'bookingId'
//        System.out.println("--- Booking Data for Gym ID: " + gymId + " ---");
//
//        Booking booking = gymBookingsDB.get(gymId); // Get the single Booking object
//
//        if (booking != null) {
//            // Print details of the single booking object
//            System.out.println("Booking ID: " + booking.slotBookingId);
//            System.out.println("User ID: " + booking.userId);
//            System.out.println("Slot ID: " + booking.slotId);
//            System.out.println("Gym ID: " + booking.gymId);
//            System.out.println("Date: " + booking.date);
//            System.out.println("Cancelled: " + booking.isCancelled);
//            // If you had an enum for status, you'd print it here:
//            // System.out.println("Status: " + booking.status);
//            return true; // Booking was found and data displayed
//        } else {
//            System.out.println("No booking found for Booking ID: " + gymId);
//            return false; // No booking found
//        }
//    }

    public boolean viewBookings(int gymId) { // Parameter name is now 'gymId' as requested
        System.out.println("--- All Bookings for Gym ID: " + gymId + " ---");

        List<Booking> bookingsForGym = new ArrayList<>();

        // Iterate through all values (Booking objects) in the gymBookingsDB map
        for (Booking booking : gymBookingsDB.values()) {
            // Check if the current booking's gymId matches the requested gymId
            if (booking.gymId == gymId) {
                bookingsForGym.add(booking); // Add to our list
            }
        }

        if (!bookingsForGym.isEmpty()) {
            System.out.println("Found " + bookingsForGym.size() + " booking(s) for Gym ID " + gymId + ":");
            for (Booking booking : bookingsForGym) {
                System.out.println("------------------------------------");
                System.out.println("Booking ID: " + booking.slotBookingId);
                System.out.println("  User ID: " + booking.userId);
                System.out.println("  Slot ID: " + booking.slotId);
                System.out.println("  Date: " + booking.date);
                System.out.println("  Cancelled: " + booking.isCancelled);
            }
            System.out.println("------------------------------------");
            return true; // Bookings were found and displayed
        } else {
            System.out.println("No bookings found for Gym ID: " + gymId);
            return false; // No bookings found
        }
    }


    @Override
    public GymOwner getGymOwnerDetails(String ownerEmail) {
        for (GymOwner owner : gymOwnersDB.values()) {
            if (owner.getGymUserEmail().equals(ownerEmail)) {
                System.out.println("Mock: Retrieved gym owner details for email: " + ownerEmail);
                return owner;
            }
        }
        System.out.println("Mock: No gym owner found for email: " + ownerEmail);
        return null; // Or throw an exception if not found
    }

    @Override
    public List<Gym> getCenterDetails(int ownerId) {
        List<Gym> gymCenters = new ArrayList<>();
        for (Gym center : gymCentersDB.values()) {
            if (center.getGymOwnerId() == ownerId) {
                gymCenters.add(center);
            }
        }
        System.out.println("Mock: Retrieved " + gymCenters.size() + " centers for owner ID: " + ownerId);
        return gymCenters;
    }

    //@Override
//    public boolean isApprovedOwner(int gymOwnerId) {
////        GymOwner owner = gymOwnersDB.get(gymOwnerId);
////        if (owner != null) {
////            System.out.println("Mock: Approval status for owner ID " + gymOwnerId + ": " + owner.isApproved());
////            return owner.isApproved();
////        }
////        System.out.println("Mock: Owner with ID " + gymOwnerId + " not found for approval check.");
////        return false;
//    }

 //   @Override
//    public boolean isApprovedCenter(int gymCenterId) {
//        Gym center = gymCentersDB.get(gymCenterId);
//        if (center != null) {
//            System.out.println("Mock: Approval status for center ID " + gymCenterId + ": " + center.isApproved());
//            return center.isApproved();
//        }
//        System.out.println("Mock: Center with ID " + gymCenterId + " not found for approval check.");
//        return false;
//    }

//    // You could add methods here to simulate updating owner/center approval status
//    public static void approveGymOwner(int ownerId, boolean isApproved) {
//        GymOwner owner = gymOwnersDB.get(ownerId);
//        if (owner != null) {
//            owner.setApproved(isApproved);
//            System.out.println("Mock: Owner " + ownerId + " approval status set to " + isApproved);
//        }
//    }
//
//    public static void approveGymCenter(int centerId, boolean isApproved) {
//        Gym_Center center = gymCentersDB.get(centerId);
//        if (center != null) {
//            center.setApproved(isApproved);
//            System.out.println("Mock: Center " + centerId + " approval status set to " + isApproved);
//        }
//    }
}