package com.project.couponProject3.services;

import com.project.couponProject3.beans.Company;
import com.project.couponProject3.beans.Coupon;
import com.project.couponProject3.enums.Category;
import com.project.couponProject3.exceptions.CouponExceptions.CouponException;

import java.util.List;

/**
 * Contains all the method of company actions
 */
public interface CompanyService {

    /**
     * Add the new coupon instance to DB if coupon's title not already exist under the same company.
     *
     * @param coupon coupon instance to add to DB
     * @throws CouponException if coupon's title already exist under the same company or coupons end date already passed.
     */
    void addCoupon(Coupon coupon) throws CouponException;

    /**
     * Update a coupon that already exist in DB.
     * Coupon's id and company id are not updatable.
     *
     * @param coupon coupon instance with the new values
     * @throws CouponException when coupon id not found in DB or company id is different or
     *                         coupon's title already exist under the company
     */
    void updateCoupon(Coupon coupon) throws CouponException;

    /**
     * Delete a coupon from DB including coupons' purchases
     *
     * @param couponID delete by coupon ID
     * @throws CouponException if coupon's ID not found in DB
     */
    void deleteCoupon(int couponID) throws CouponException;

    /**
     * Get all company's coupons from DB of the logged in company.
     *
     * @return List of company's coupons or null if no coupon found
     */
    List<Coupon> getCompanyCoupons();

    /**
     * Get all company's coupons of certain category from DB of the logged in company.
     *
     * @param category by coupon category
     * @return List of company's coupons of certain category or null if no coupon found
     */
    List<Coupon> getCompanyCouponsByCategory(Category category);

    /**
     * Get all company's coupons to max price from DB of the logged in company.
     *
     * @param maxPrice by coupon price
     * @return List of company's coupons to max price or null if no coupon found
     */
    List<Coupon> getCompanyCouponsByMaxPrice(double maxPrice);

    /**
     * Get a company's coupon by coupon ID from DB.
     * @param id by coupon ID
     * @return coupon instance from DB
     */
    Coupon getCouponById(int id);

    /**
     * Get the details of the logged in company
     *
     * @return instance of the logged in company
     */
    Company getCompanyDetails();

}
