package com.flipfit.dao;

import com.flipfit.bean.GymUser;
import java.util.ArrayList;
import java.util.List;
//abcc

public class FlipFitCustomerDAO implements FlipFitCustomerDAOInterface {

    // In-memory list to act as a database for customers.
    private static final List<GymUser> customerList = new ArrayList<>();

    // Static block now pre-populated with three sample customers.
    static {
        // --- Create First Customer ---
        GymUser customer1 = new GymUser();
        customer1.setGymUserId(1);
        customer1.setGymUserName("sanjay_k");
        customer1.setGymUserPassword("pass123");
        customer1.setGymUserEmail("sanjay.k@example.com");
        customer1.setPhoneno(987654321);
        customer1.setGymUserRole("Customer");
        customerList.add(customer1);

        // --- Create Second Customer ---
        GymUser customer2 = new GymUser();
        customer2.setGymUserId(2);
        customer2.setGymUserName("priya_s");
        customer2.setGymUserPassword("pass456");
        customer2.setGymUserEmail("priya.s@example.com");
        customer2.setPhoneno(876543210);
        customer2.setGymUserRole("Customer");
        customerList.add(customer2);

        // --- Create Third Customer ---
        GymUser customer3 = new GymUser();
        customer3.setGymUserId(3);
        customer3.setGymUserName("amit_g");
        customer3.setGymUserPassword("pass789");
        customer3.setGymUserEmail("amit.g@example.com");
        customer3.setPhoneno(765432109);
        customer3.setGymUserRole("Customer");
        customerList.add(customer3);
    }

    @Override
    public void registerUser(String userName, String password, String email, int phoneNumber, String role) {
        // Check if the username is already taken.
        for (GymUser user : customerList) {
            if (user.getGymUserName().equalsIgnoreCase(userName)) {
                System.out.println("Registration Failed: Username '" + userName + "' is already taken.");
                return;
            }
        }

        // Create and populate a new user object.
        GymUser newUser = new GymUser();
        newUser.setGymUserId(customerList.size() + 1); // Simple ID generation
        newUser.setGymUserName(userName);
        newUser.setGymUserPassword(password);
        newUser.setGymUserEmail(email);
        newUser.setPhoneno(phoneNumber);
        newUser.setGymUserRole(role); // Sets the role passed during registration

        customerList.add(newUser);
        System.out.println("✅ User '" + userName + "' registered successfully as a " + role);
    }

    @Override
    public boolean isUserValid(String userName, String password) {
        for (GymUser user : customerList) {
            if (user.getGymUserName().equalsIgnoreCase(userName) && user.getGymUserPassword().equals(password)) {
                return true; // Credentials match.
            }
        }
        return false; // No match found.
    }

    @Override
    public GymUser getUserByName(String userName) {
        for (GymUser user : customerList) {
            if (user.getGymUserName().equalsIgnoreCase(userName)) {
                return user; // Return the found user object.
            }
        }
        return null; // No user found with that username.
    }
}