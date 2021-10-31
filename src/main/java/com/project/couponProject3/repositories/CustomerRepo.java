package com.project.couponProject3.repositories;

import com.project.couponProject3.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Contains all the smart dialect methods of searching customer in DB
 */
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    /**
     * Search for a customer in DB.
     *
     * @param id by customer id
     * @return customer instance from DB or null if no customer found
     */
    Customer findById(int id);

    /**
     * Search for a customer in DB.
     *
     * @param email by customer email
     * @return customer instance from DB or null if no customer found
     */
    Customer findByEmail(String email);

    /**
     * Search for a customer in DB.
     *
     * @param email    by customer email
     * @param password and by customer password
     * @return customer instance from DB or null if no customer found
     */
    Customer findByEmailAndPassword(String email, String password);

}
