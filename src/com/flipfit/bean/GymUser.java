package com.flipfit.bean;

public class GymUser extends Gym {
    public int GymUserId;
    public String GymUserName;
    public String GymUserRole;
    public String GymUserEmail;
    public String GymUserPassword;
    public int Phoneno;

    public int getGymUserId() {
        return GymUserId;
    }

    public void setGymUserId(int gymUserId) {
        GymUserId = gymUserId;
    }

    public String getGymUserName() {
        return GymUserName;
    }

    public void setGymUserName(String gymUserName) {
        GymUserName = gymUserName;
    }

    public String getGymUserRole() {
        return GymUserRole;
    }

    public void setGymUserRole(String gymUserRole) {
        GymUserRole = gymUserRole;
    }

    public String getGymUserEmail() {
        return GymUserEmail;
    }

    public void setGymUserEmail(String gymUserEmail) {
        GymUserEmail = gymUserEmail;
    }

    public String getGymUserPassword() {
        return GymUserPassword;
    }

    public void setGymUserPassword(String gymUserPassword) {
        GymUserPassword = gymUserPassword;
    }

    public int getPhoneno() {
        return Phoneno;
    }

    public void setPhoneno(int phoneno) {
        Phoneno = phoneno;
    }
}
