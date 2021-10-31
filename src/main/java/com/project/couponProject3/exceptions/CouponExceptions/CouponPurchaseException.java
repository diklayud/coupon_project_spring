package com.project.couponProject3.exceptions.CouponExceptions;

/**
 * Class of purchase coupon exception
 */
public class CouponPurchaseException extends CouponException{

    /**
     * C'tor for exception caused by a bad try of purchasing a coupon
     */
    public CouponPurchaseException() {
    }

    /**
     * C'tor for exception caused by a bad try of purchasing a coupon
     * @param message get string into the C'tor
     */
    public CouponPurchaseException(String message) {
        super(message);
    }
}
