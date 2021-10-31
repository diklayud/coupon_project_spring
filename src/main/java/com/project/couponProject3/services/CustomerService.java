package com.project.couponProject3.services;

import com.project.couponProject3.beans.Coupon;
import com.project.couponProject3.beans.Customer;
import com.project.couponProject3.enums.Category;
import com.project.couponProject3.exceptions.CouponExceptions.CouponException;

import java.util.List;

/**
 * Contains all the method of customer actions
 */
public interface CustomerService {

    /**
     * Purchase the coupon instance sent to the method by the logged in customer.
     * After purchasing the coupon, coupon's quantity decrease by one.
     *
     * @param coupon coupon to purchase by the logged in customer
     * @throws CouponException if try to buy the same coupon more than once or coupons amount is zero or
     *                         coupons end date already passed
     */
    void purchaseCoupon(Coupon coupon) throws CouponException;

    /**
     * Get all customer's coupons from DB of the logged in customer.
     *
     * @return List of customer's coupons or null if no coupon found
     */
    List<Coupon> getCustomerCoupons();

    /**
     * Get all customer's coupons of certain category from DB of the logged in customer.
     *
     * @param category by coupon category
     * @return List of customer's coupons of certain category or null if no coupon found
     */
    List<Coupon> getCustomerCouponsByCategory(Category category);

    /**
     * Get all customer's coupons to max price from DB of the logged in customer.
     *
     * @param maxPrice by coupon price
     * @return List of customer's coupons to max price or null if no coupon found
     */
    List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice);

    /**
     * Get the details of the logged in customer
     *
     * @return instance of the logged in customer
     */
    Customer getCustomerDetails();

}
