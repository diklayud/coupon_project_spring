package com.project.couponProject3.repositories;

import com.project.couponProject3.beans.Coupon;
import com.project.couponProject3.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

/**
 * Contains all the smart dialect methods of searching coupon in DB
 */
public interface CouponRepo extends JpaRepository<Coupon, Integer> {

    /**
     * Search for a coupon in DB.
     *
     * @param id by coupon id
     * @return coupon instance from DB or null if no coupon found
     */
    Coupon findById(int id);

    /**
     * Search for a coupon in DB.
     *
     * @param title     by coupon title
     * @param companyID and by company-ID
     * @return coupon instance from DB or null if no coupon found
     */
    Coupon findByTitleAndCompany_id(String title, int companyID);

    /**
     * Search for a company's coupons in DB.
     *
     * @param companyID by company-ID
     * @return List of company's coupons from DB or an empty list if no coupon found
     */
    List<Coupon> findByCompany_id(int companyID);

    /**
     * Search for a company's coupon in DB.
     * @param id bu coupon id
     * @param companyID and by company-ID
     * @return coupon instance from DB or null if no coupon found
     */
    Coupon findByIdAndCompany_id(int id, int companyID);

    /**
     * Search for a company's coupons of certain category in DB.
     *
     * @param companyID by company-ID
     * @param category  and by coupon category
     * @return List of company's coupons of certain category from DB or an empty list if no coupon found
     */
    List<Coupon> findByCompany_idAndCategory(int companyID, Category category);

    /**
     * Search for a company's coupons to max price in DB.
     *
     * @param companyID by company-ID
     * @param maxPrice  and by coupon price
     * @return List of company's coupons less than maxPrice from DB or an empty list if no coupon found
     */
    List<Coupon> findByCompany_idAndPriceLessThan(int companyID, double maxPrice);

    /**
     * Search for coupons until end date in DB.
     *
     * @param endDate by coupon endDate
     * @return List of coupons until due date from DB or an empty list if no coupon found
     */
    List<Coupon> findByEndDateBefore(Date endDate);

}
