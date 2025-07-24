package com.flipfit.dao;

import com.flipfit.bean.Gym;
import com.flipfit.bean.GymOwner;
import java.util.List;

/**
 * DAO interface for handling administrative tasks.
 * Defines methods for gym owner and gym center approval and retrieval.
 */
public interface FlipFitAdminDAOInterface {

    List<GymOwner> getPendingGymOwners();
    void validateGymOwner(String ownerId, boolean isApproved);
    List<Gym> getPendingGymCentres();
    void validateGymCentre(String gymCentreId, boolean isApproved);
    GymOwner getOwnerById(String ownerId);
    Gym getGymById(String gymId);
}