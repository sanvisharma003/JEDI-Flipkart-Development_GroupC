package com.flipfit.bean;

public class Gym {
    public int GymId;
    public int GymOwnerId;
    public String GymName;
    public Gym() {
    }

    public Gym(int gymId, int gymOwnerId, String gymName, String location) {
        this.GymId = gymId;
        this.GymOwnerId = gymOwnerId;
        this.GymName = gymName;
        this.location = location;
    }
    public int getGymId() {
        return GymId;
    }

    public void setGymId(int gymId) {
        GymId = gymId;
    }

    public int getGymOwnerId() {
        return GymOwnerId;
    }

    public void setGymOwnerId(int gymOwnerId) {
        GymOwnerId = gymOwnerId;
    }

    public String getGymName() {
        return GymName;
    }

    public void setGymName(String gymName) {
        GymName = gymName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String location;

}
