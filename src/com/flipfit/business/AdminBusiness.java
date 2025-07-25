//package com.flipfit.business;
//
//import com.flipfit.bean.Gym;
//import com.flipfit.bean.GymOwner;
//import com.flipfit.bean.GymUser;
//
////Checking for changes
//public class AdminBusiness
//{
//    public boolean approval(int GymId)
//    {
//        System.out.println("GymAdminId = " + GymId);
//        return true;
//    }
//
//    public void add_gym()
//    {
//        System.out.println("Adding Gym");
//    }
//
//    public void add_owner(int GymUser)
//    {
//        System.out.println("Adding Owner");
//    }
//
//    public void view_gym(int GymId)
//    {
//        System.out.println("View Gym");
//    }
//
//    public void view_owner(int OwnerId)
//    {
//        System.out.println("View owner");
//    }
//
//    public void view_all_payments()
//    {
//        System.out.println("View all payments");
//    }
//.}
package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.bean.GymOwner;
import com.flipfit.dao.FlipFitAdminDAO;
import com.flipfit.dao.FlipFitAdminDAOInterface;
import java.util.List;

/**
 * Implements the business logic for administrator actions.
 */
public class AdminBusiness implements AdminBusinessInterface {

    private final FlipFitAdminDAOInterface adminDao = new FlipFitAdminDAO();

    @Override
    public List<GymOwner> getPendingOwners() {
        System.out.println("BUSINESS: Fetching list of pending gym owners.");
        return adminDao.getPendingGymOwners();
    }

    @Override
    public void approveOwnerRequest(String ownerId) {
        System.out.println("BUSINESS: Approving owner with ID: " + ownerId);
        adminDao.validateGymOwner(ownerId, true);
    }

    @Override
    public void rejectOwnerRequest(String ownerId) {
        System.out.println("BUSINESS: Rejecting owner with ID: " + ownerId);
        adminDao.validateGymOwner(ownerId, false);
    }

    @Override
    public List<Gym> getPendingGyms() {
        System.out.println("BUSINESS: Fetching list of pending gyms.");
        return adminDao.getPendingGymCentres();
    }

    @Override
    public void approveGymRequest(String gymId) {
        System.out.println("BUSINESS: Approving gym with ID: " + gymId);
        adminDao.validateGymCentre(gymId, true);
    }

    @Override
    public void rejectGymRequest(String gymId) {
        System.out.println("BUSINESS: Rejecting gym with ID: " + gymId);
        adminDao.validateGymCentre(gymId, false);
    }

    @Override
    public void viewOwner() {
        adminDao.getOwnerById();

    }

    @Override
    public void viewGym()
    {
        adminDao.getGymById();

    }
}