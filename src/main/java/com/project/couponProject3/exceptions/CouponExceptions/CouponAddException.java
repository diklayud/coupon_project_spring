package com.project.couponProject3.exceptions.CouponExceptions;

/**
 * Class of add coupon exception
 */
public class CouponAddException extends CouponException{

    /**
     * C'tor for exception caused by an unsuccessful try of adding a coupon
     */
    public CouponAddException() {
    }

    /**
     * C'tor for exception caused by an unsuccessful try of adding a coupon
     *
     * @param message get string into the C'tor
     */
    public CouponAddException(String message) {
        super(message);
    }
}
