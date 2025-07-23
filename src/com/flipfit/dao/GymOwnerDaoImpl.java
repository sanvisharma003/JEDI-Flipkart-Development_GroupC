package com.flipfit.dao;

import com.flipfit.bean.Gym;
import com.flipfit.bean.GymOwner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class GymOwnerDaoImpl implements GymOwnerDaoInterface {

    // Mock database using HashMap for Gym_Owner objects
    private static Map<Integer, GymOwner> gymOwnersDB = new HashMap<>();
    // Mock database using HashMap for Gym_Center objects
    private static Map<Integer, Gym> gymCentersDB = new HashMap<>();

    // For generating unique IDs for mock data
    private static AtomicInteger ownerIdCounter = new AtomicInteger(1);
    private static AtomicInteger centerIdCounter = new AtomicInteger(1);

    // Static block to pre-populate some mock data for demonstration
    static {
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


    }

    @Override
    public boolean registerGymCenter(Gym gCenter) {
        if (gCenter == null) {
            return false;
        }
        // Assign a new ID to the gym center
        gCenter.setGymId(centerIdCounter.getAndIncrement());
        // By default, a newly registered center is not approved
        //gCenter.setApproved(false);
        gymCentersDB.put(gCenter.getGymId(), gCenter);
        System.out.println("Mock: Registered gym center: " + gCenter.getGymName() + " with ID: " + gCenter.getGymId());
        return true;
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