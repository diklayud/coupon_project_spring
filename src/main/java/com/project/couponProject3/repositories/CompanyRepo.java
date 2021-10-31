package com.project.couponProject3.repositories;

import com.project.couponProject3.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Contains all the smart dialect methods of searching company in DB
 */
public interface CompanyRepo extends JpaRepository<Company, Integer> {

    /**
     * Search for a company in DB.
     *
     * @param id by company id
     * @return company instance from DB or null if no company found
     */
    Company findById(int id);

    /**
     * Search for a company in DB.
     *
     * @param email    by company email
     * @param password and by company password
     * @return company instance from DB or null if no company found
     */
    Company findByEmailAndPassword(String email, String password);

    /**
     * Search for a company in DB.
     *
     * @param name by company name
     * @return company instance from DB or null if no company found
     */
    Company findByName(String name);

    /**
     * Search for a company in DB.
     *
     * @param email by company email
     * @return company instance from DB or null if no company found
     */
    Company findByEmail(String email);
}
