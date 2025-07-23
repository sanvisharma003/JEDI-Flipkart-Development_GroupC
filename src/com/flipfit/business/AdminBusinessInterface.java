package com.flipfit.business;

public interface AdminBusinessInterface {
    public boolean approval(int GymId);
    public void add_gym();
    public void add_owner(int GymUser);
    public void view_gym(int GymId);
    public void view_owner(int OwnerId);
    public void view_all_payments();
}
