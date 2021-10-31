package com.project.couponProject3.exceptions.CouponExceptions;

/**
 * Super class of coupon exceptions
 */
public class CouponException extends Exception{

    /**
     * Superclass C'tor of coupon exceptions
     */
    public CouponException() {
    }

    /**
     * Superclass C'tor of coupon exceptions
     *
     * @param message get string into the C'tor
     */
    public CouponException(String message) {
        super(message);
    }
}
