package com.project.couponProject3.services;

import com.project.couponProject3.exceptions.LoginException;
import com.project.couponProject3.repositories.CompanyRepo;
import com.project.couponProject3.repositories.CouponRepo;
import com.project.couponProject3.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Superclass of all types of service clients
 */
public abstract class ClientService {

    @Autowired
    /**
     * Interface repository extends the JpaRepository for SQL search company commands
     */
    protected CompanyRepo companyRepo;

    @Autowired
    /**
     * Interface repository extends the JpaRepository for SQL search customer commands
     */
    protected CustomerRepo customerRepo;

    @Autowired
    /**
     * Interface repository extends the JpaRepository for SQL search coupon commands
     */
    protected CouponRepo couponRepo;

    /**
     * Looking for client in DB by the details:
     *
     * @param email    client's email
     * @param password client's password
     * @return true if found client by email and password in DB
     * @throws LoginException if the email or password are wrong.
     */
    public abstract boolean login(String email, String password) throws LoginException;

}
