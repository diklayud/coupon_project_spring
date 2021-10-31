package com.project.couponProject3.exceptions.CustomerExceptions;

/**
 * Class of customer not found exception
 */
public class CustomerNotFoundException extends CustomerException {

    /**
     * C'tor for exception caused by an unsuccessful try of finding a customer
     */
    public CustomerNotFoundException() {
    }

    /**
     * C'tor for exception caused by an unsuccessful try of finding a customer
     *
     * @param message get string into the C'tor
     */
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
