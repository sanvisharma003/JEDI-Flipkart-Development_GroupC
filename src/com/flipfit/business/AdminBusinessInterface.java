//package com.flipfit.business;
//
//public interface AdminBusinessInterface {
//    public boolean approval(int GymId);
//    public void add_gym();
//    public void add_owner(int GymUser);
//    public void view_gym(int GymId);
//    public void view_owner(int OwnerId);
//    public void view_all_payments();
//.}
package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.bean.GymOwner;
import java.util.List;

/**
 * Interface defining the business logic for administrator actions.
 * This contract ensures modularity and separates the client from the implementation.
 */
public interface AdminBusinessInterface {

    /**
     * Retrieves a list of all gym owners who are awaiting approval.
     * @return A list of GymOwner objects with a pending status.
     */
    List<GymOwner> getPendingOwners();

    /**
     * Approves a gym owner's registration request.
     * @param ownerId The unique ID (e.g., PAN) of the gym owner to approve.
     */
    void approveOwnerRequest(String ownerId);

    /**
     * Rejects a gym owner's registration request.
     * @param ownerId The unique ID (e.g., PAN) of the gym owner to reject.
     */
    void rejectOwnerRequest(String ownerId);

    /**
     * Retrieves a list of all gym centers that are awaiting approval.
     * @return A list of Gym objects with a pending status.
     */
    List<Gym> getPendingGyms();

    /**
     * Approves a gym center's registration request.
     * @param gymId The unique ID of the gym center to approve.
     */
    void approveGymRequest(String gymId);

    /**
     * Rejects a gym center's registration request.
     * @param gymId The unique ID of the gym center to reject.
     */
    void rejectGymRequest(String gymId);

    /**
     * Displays the details of a specific gym owner.
     * @param ownerId The unique ID of the owner to view.
     */
    void viewOwner(String ownerId);

    /**
     * Displays the details of a specific gym.
     * @param gymId The unique ID of the gym to view.
     */
    void viewGym(String gymId);
}