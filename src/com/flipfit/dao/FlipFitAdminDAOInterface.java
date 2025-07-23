package com.flipfit.dao;

import com.flipfit.bean.Gym;
import com.flipfit.bean.GymOwner;

import java.util.List;

/**
 * Data Access Object (DAO) interface for handling administrative tasks in the FlipFit system.
 * This interface defines methods related to gym owner and gym center approval.
 */
public interface FlipFitAdminDAOInterface {

    /**
     * Retrieves a list of all gym owners who are awaiting approval.
     * @return A list of FlipFitGymOwner objects with a pending status.
     */
    List<GymOwner> getPendingGymOwners();

    /**
     * Approves or rejects a gym owner's registration request.
     * @param gymOwnerId The unique ID of the gym owner to validate.
     * @param isApproved Set to true to approve, false to reject.
     */
    void validateGymOwner(String gymOwnerId, boolean isApproved);

    /**
     * Approves or rejects a gym center's registration request.
     * @param gymCentreId The unique ID of the gym center to validate.
     * @param isApproved Set to true to approve, false to reject.
     */
    void validateGymCentre(String gymCentreId, boolean isApproved);

    /**
     * Retrieves a list of all gym centers that are awaiting approval.
     * @return A list of FlipFitGymCenter objects with a pending status.
     */
    List<Gym> getPendingGymCentres();
}