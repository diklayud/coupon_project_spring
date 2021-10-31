package com.project.couponProject3.exceptions.CouponExceptions;

/**
 * Class of update coupon exception
 */
public class CouponUpdateException extends CouponException {

    /**
     * C'tor for exception caused by a bad try of updating a coupon
     */
    public CouponUpdateException() {
    }

    /**
     * C'tor for exception caused by a bad try of updating a coupon
     *
     * @param message get string into the C'tor
     */
    public CouponUpdateException(String message) {
        super(message);
    }
}
