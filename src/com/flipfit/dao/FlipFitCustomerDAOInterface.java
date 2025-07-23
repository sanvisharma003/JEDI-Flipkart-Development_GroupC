package com.flipfit.dao;

import com.flipfit.bean.GymUser;

/**
 * Data Access Object (DAO) interface for handling user-centric operations,
 * primarily for customer registration and login.
 */
public interface FlipFitCustomerDAOInterface {

    /**
     * Registers a new user with a specific role.
     * @param userName The username for the new account.
     * @param password The password for the account.
     * @param email The user's email address.
     * @param phoneNumber The user's phone number.
     * @param role The role of the user (e.g., "Customer").
     */
    void registerUser(String userName, String password, String email, int phoneNumber, String role);

    /**
     * Validates a user's login credentials.
     * @param userName The username to validate.
     * @param password The password to validate.
     * @return True if credentials match, false otherwise.
     */
    boolean isUserValid(String userName, String password);

    /**
     * Retrieves a user's details by their username.
     * @param userName The username of the user to find.
     * @return A GymUser object if found, otherwise null.
     */
    GymUser getUserByName(String userName);
}