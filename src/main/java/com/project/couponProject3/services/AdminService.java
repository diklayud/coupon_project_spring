package com.project.couponProject3.services;


import com.project.couponProject3.beans.Company;
import com.project.couponProject3.beans.Customer;
import com.project.couponProject3.exceptions.CompanyExceptions.CompanyException;
import com.project.couponProject3.exceptions.CustomerExceptions.CustomerException;

import java.util.List;

/**
 * Contains all the method of admin actions
 */
public interface AdminService {

    /**
     * Add the new company instance to DB if company's name or email not already exist in DB.
     *
     * @param company company instance to add to DB
     * @throws CompanyException if company's name or email already exist in DB
     */
    void addCompany(Company company) throws CompanyException;

    /**
     * Update a company that already exist in DB.
     * Company's id and name are not updatable.
     *
     * @param company company instance with the new values
     * @throws CompanyException when company id not found in DB or a try to change company name
     *                          or a try to change company email to an email of other company in the system.
     */
    void updateCompany(Company company) throws CompanyException;

    /**
     * Delete a company from DB including company's coupons and coupons' purchases
     *
     * @param companyID delete by company ID
     * @throws CompanyException if company's ID not found in DB
     */
    void deleteCompany(int companyID) throws CompanyException;

    /**
     * Get all companies instances from DB.
     *
     * @return List of companies or null if no company found
     */
    List<Company> getAllCompanies();

    /**
     * Get a company instance from DB.
     *
     * @param companyID by company ID
     * @return company instance from DB or null if no company found
     */
    Company getOneCompany(int companyID);

    /**
     * Add the new customer instance to DB if customer's email not already exist in DB.
     *
     * @param customer customer instance to add to DB
     * @throws CustomerException if customer's email already exist in DB
     */
    void addCustomer(Customer customer) throws CustomerException;

    /**
     * Update a customer that already exist in DB.
     * Customer's id is not updatable.
     *
     * @param customer customer instance with the new values
     * @throws CustomerException when customer id not found in DB or a try
     *                           to change customer email to an email of other customer in the system.
     */
    void updateCustomer(Customer customer) throws CustomerException;

    /**
     * Delete a customer from DB including the coupons' purchases
     *
     * @param customerID delete by customer ID
     * @throws CustomerException if customer's ID not found in DB
     */
    void deleteCustomer(int customerID) throws CustomerException;

    /**
     * Get all customers instances from DB.
     *
     * @return List of customers or null if no customer found
     */
    List<Customer> getAllCustomers();

    /**
     * Get a customer instance from DB.
     *
     * @param customerID by customer ID
     * @return customer instance from DB or null if no customer found
     */
    Customer getOneCustomer(int customerID);

}
