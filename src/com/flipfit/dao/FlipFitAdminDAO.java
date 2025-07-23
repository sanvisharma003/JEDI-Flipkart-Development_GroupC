package com.flipfit.dao;

import com.flipfit.bean.Gym;
import com.flipfit.bean.GymOwner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlipFitAdminDAO /* implements FlipFitAdminDAOInterface */ {

    private static List<GymOwner> gymOwnerList = new ArrayList<>();
    private static List<Gym> gymCenterList = new ArrayList<>();

    // WORKAROUND 1: Map to track Gym approval status using GymId as the key.
    private static Map<Integer, Boolean> gymApprovalStatus = new HashMap<>();

    // WORKAROUND 2: Map to track GymOwner approval status using PAN as the unique key.
    private static Map<String, Boolean> ownerApprovalStatus = new HashMap<>();

    static {
        // --- Initialize Gym Owners using PAN as the ID ---
        GymOwner owner1 = new GymOwner();
        owner1.setPAN("APN12345X"); // Using PAN as the unique identifier
        gymOwnerList.add(owner1);
        ownerApprovalStatus.put("APN12345X", false); // Mark as Pending

        GymOwner owner2 = new GymOwner();
        owner2.setPAN("BQN67890Y");
        gymOwnerList.add(owner2);
        ownerApprovalStatus.put("BQN67890Y", false); // Mark as Pending

        GymOwner owner3 = new GymOwner();
        owner3.setPAN("CRZ11223Z");
        gymOwnerList.add(owner3);
        ownerApprovalStatus.put("CRZ11223Z", true); // Mark as Approved

        // --- Initialize Gyms ---
        Gym center1 = new Gym();
        center1.setGymId(101);
        center1.setGymName("Downtown Fitness");
        gymCenterList.add(center1);
        gymApprovalStatus.put(101, true);

        Gym center2 = new Gym();
        center2.setGymId(102);
        center2.setGymName("Uptown Strength");
        gymCenterList.add(center2);
        gymApprovalStatus.put(102, true);

        Gym center3 = new Gym();
        center3.setGymId(103);
        center3.setGymName("Westside Gym");
        gymCenterList.add(center3);
        gymApprovalStatus.put(103, false);
    }

    /**
     * Filters the owner list based on the external approval status map.
     */
    public List<GymOwner> getPendingGymOwners() {
        System.out.println("Fetching all pending gym owners...");
        return gymOwnerList.stream()
                .filter(owner -> {
                    // Check the owner's status in our map using their PAN.
                    boolean isApproved = ownerApprovalStatus.getOrDefault(owner.getPAN(), false);
                    return !isApproved;
                })
                .collect(Collectors.toList());
    }

    /**
     * Validates an owner by updating their status in the map, using PAN as the ID.
     * @param ownerPAN The PAN number of the owner to validate.
     * @param isApproved The new approval status.
     */
    public void validateGymOwner(String ownerPAN, boolean isApproved) {
        if (ownerApprovalStatus.containsKey(ownerPAN)) {
            ownerApprovalStatus.put(ownerPAN, isApproved);
            System.out.println("✅ Gym Owner with PAN " + ownerPAN + " has been " + (isApproved ? "Approved" : "Rejected") + ".");
        } else {
            System.out.println("❌ Gym Owner with PAN " + ownerPAN + " not found.");
        }
    }

    /**
     * Validates a gym by updating its status in the gym-specific map.
     * @param gymCentreId The ID of the gym center to validate.
     * @param isApproved The new approval status.
     */
    public void validateGymCentre(int gymCentreId, boolean isApproved) {
        if (gymApprovalStatus.containsKey(gymCentreId)) {
            gymApprovalStatus.put(gymCentreId, isApproved);
            System.out.println("✅ Gym Centre " + gymCentreId + " has been " + (isApproved ? "Approved" : "Rejected") + ".");
        } else {
            System.out.println("❌ Gym Centre with ID " + gymCentreId + " not found.");
        }
    }

    /**
     * Filters the gym list based on the external approval status map.
     */
    public List<Gym> getPendingGymCentres() {
        System.out.println("Fetching all pending gym centres...");
        return gymCenterList.stream()
                .filter(center -> {
                    boolean isApproved = gymApprovalStatus.getOrDefault(center.getGymId(), false);
                    return !isApproved;
                })
                .collect(Collectors.toList());
    }
}