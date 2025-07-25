package com.flipfit.business;

import com.flipfit.bean.*;
import com.flipfit.dao.FlipFitCustomerDAO;
import com.flipfit.dao.FlipFitCustomerDAOInterface;
import java.util.List;

public class CustomerBusiness implements CustomerBusinessInterface {

    private final FlipFitCustomerDAOInterface customerDAO = new FlipFitCustomerDAO();


    // --- Booking Methods ---
    @Override
    public void viewAllGyms() {
        customerDAO.getAllGyms();
    }

}