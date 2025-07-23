package com.flipfit.dao;
import com.flipfit.bean.*;
import java.util.*;





public interface GymOwnerDaoInterface {


    public GymOwner getGymOwnerDetails(String ownerEmail);

    public List<Gym> getCenterDetails(int ownerId);



    public boolean registerGymCenter(Gym gCenter);

}
