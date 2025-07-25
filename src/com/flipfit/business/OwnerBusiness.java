package com.flipfit.business;
import com.flipfit.bean.Gym;
import com.flipfit.bean.GymUser;
import com.flipfit.bean.Slot;
import com.flipfit.dao.GymOwnerDaoImpl;


public class OwnerBusiness implements OwnerBusinessInterface
{
    static int gymIdCount=304;
    static int slotIdCount=1;
    private static final GymOwnerDaoImpl gymOwnerDao = new GymOwnerDaoImpl();

    public void add_gym(String GymName, String address)
    {

        Gym gym = new Gym();
        //gym.setGymOwnerId(ownerId);
        gym.setLocation(address);
        gym.setGymName(GymName);

        gym.setGymId(gymIdCount);
        gymIdCount++;

        System.out.println(gymIdCount);

        gymOwnerDao.registerGymCenter(gym);
        System.out.println("Adding gym");
    }

    public void view_payments(int GymId)
    {
        System.out.println("viewing payments");
        gymOwnerDao.viewPayments(GymId);
    }

    public void view_userdata(int GymUserId)
    {
        System.out.println("viewing customers");
        gymOwnerDao.viewUsers(GymUserId);
    }
    public void view_bookings(int GymId)
    {
        System.out.println("viewing bookings");
        gymOwnerDao.viewBookings(GymId);
    }

    public boolean add_slot(int GymId,String starttime,String endTime)
    {
        Slot slot = new Slot();
       slot.setGymId(GymId);
       slot.setSlotEndTime(endTime);
       slot.setSlotStartTime(starttime);

        slot.setSlotId(slotIdCount);
        slotIdCount++;


        gymOwnerDao.addSlot(slot);
        System.out.println("Adding slot");
        return true;
    }

    //Sanvi banayegi yeh
    public boolean loginGymOwner(String userName, String password)
    {
        // Dummy login logic
        return userName.equals("owner") && password.equals("password");
    }



}