package com.project.couponProject3.exceptions.CustomerExceptions;

/**
 * Super class of customer exceptions
 */
public class CustomerException extends Exception {

    /**
     * Superclass C'tor of customer exceptions
     */
    public CustomerException() {
    }

    /**
     * Superclass C'tor of customer exceptions
     *
     * @param message get string into the C'tor
     */
    public CustomerException(String message) {
        super(message);
    }
}
