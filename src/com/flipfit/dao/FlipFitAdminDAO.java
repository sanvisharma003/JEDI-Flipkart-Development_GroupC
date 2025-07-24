package com.flipfit.dao;

import com.flipfit.bean.Gym;
import com.flipfit.bean.GymOwner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * .In-memory implementation of the admin DAO.
 * Uses Maps to simulate a database for storing and managing approvals.
 */
public class FlipFitAdminDAO implements FlipFitAdminDAOInterface {

    private static final Map<String, GymOwner> gymOwners = new HashMap<>();
    private static final Map<String, Gym> gyms = new HashMap<>();
    private static final Map<String, Boolean> ownerApprovalStatus = new HashMap<>();
    private static final Map<String, Boolean> gymApprovalStatus = new HashMap<>();

    // Static block for hardcoded data
    static {
        GymOwner owner1 = new GymOwner();
        owner1.setGymUserId(101);
        owner1.setPAN("APN12345X"); // Using PAN as a unique String ID
        owner1.setGymUserName("owner_pending_1");
        gymOwners.put(owner1.getPAN(), owner1);
        ownerApprovalStatus.put(owner1.getPAN(), false); // Pending

        GymOwner owner2 = new GymOwner();
        owner2.setGymUserId(102);
        owner2.setPAN("BQN67890Y");
        owner2.setGymUserName("owner_pending_2");
        gymOwners.put(owner2.getPAN(), owner2);
        ownerApprovalStatus.put(owner2.getPAN(), false); // Pending

        GymOwner owner3 = new GymOwner();
        owner3.setGymUserId(103);
        owner3.setPAN("CRZ11223Z");
        owner3.setGymUserName("owner_approved_1");
        gymOwners.put(owner3.getPAN(), owner3);
        ownerApprovalStatus.put(owner3.getPAN(), true); // Approved

        Gym gym1 = new Gym();
        gym1.setGymId(201);
        gym1.setGymName("Downtown Fitness (Approved)");
        gyms.put(String.valueOf(gym1.getGymId()), gym1);
        gymApprovalStatus.put(String.valueOf(gym1.getGymId()), true); // Approved

        Gym gym2 = new Gym();
        gym2.setGymId(202);
        gym2.setGymName("Uptown Strength (Pending)");
        gyms.put(String.valueOf(gym2.getGymId()), gym2);
        gymApprovalStatus.put(String.valueOf(gym2.getGymId()), false); // Pending
    }

    @Override
    public List<GymOwner> getPendingGymOwners() {
        return gymOwners.keySet().stream()
                .filter(ownerId ->!ownerApprovalStatus.getOrDefault(ownerId, true))
                .map(gymOwners::get)
                .collect(Collectors.toList());
    }

    @Override
    public void validateGymOwner(String ownerId, boolean isApproved) {
        if (ownerApprovalStatus.containsKey(ownerId)) {
            ownerApprovalStatus.put(ownerId, isApproved);
            String status = isApproved? "Approved" : "Rejected";
            System.out.println("DAO: Gym Owner with ID " + ownerId + " has been " + status + ".");
        } else {
            System.out.println("DAO: Gym Owner with ID " + ownerId + " not found.");
        }
    }

    @Override
    public List<Gym> getPendingGymCentres() {
        return gyms.keySet().stream()
                .filter(gymId ->!gymApprovalStatus.getOrDefault(gymId, true))
                .map(gyms::get)
                .collect(Collectors.toList());
    }

    @Override
    public void validateGymCentre(String gymCentreId, boolean isApproved) {
        if (gymApprovalStatus.containsKey(gymCentreId)) {
            gymApprovalStatus.put(gymCentreId, isApproved);
            String status = isApproved? "Approved" : "Rejected";
            System.out.println("DAO: Gym Centre with ID " + gymCentreId + " has been " + status + ".");
        } else {
            System.out.println("DAO: Gym Centre with ID " + gymCentreId + " not found.");
        }
    }

    @Override
    public GymOwner getOwnerById(String ownerId) {
        return gymOwners.get(ownerId);
    }

    @Override
    public Gym getGymById(String gymId) {
        return gyms.get(gymId);
    }
}