package com.project.couponProject3.exceptions.CouponExceptions;

/**
 * Class of delete coupon exception
 */
public class CouponDeleteException extends CouponException {

    /**
     * C'tor for exception caused by an unsuccessful try of deleting a coupon
     */
    public CouponDeleteException() {
    }

    /**
     * C'tor for exception caused by an unsuccessful try of deleting a coupon
     *
     * @param message get string into the C'tor
     */
    public CouponDeleteException(String message) {
        super(message);
    }
}
