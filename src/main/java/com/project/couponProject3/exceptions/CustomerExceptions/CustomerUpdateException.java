package com.project.couponProject3.exceptions.CustomerExceptions;

/**
 * Class of update customer exception
 */
public class CustomerUpdateException extends CustomerException {

    /**
     * C'tor for exception caused by a bad try of updating a customer
     */
    public CustomerUpdateException() {
    }

    /**
     * C'tor for exception caused by a bad try of updating a customer
     *
     * @param message get string into the C'tor
     */
    public CustomerUpdateException(String message) {
        super(message);
    }
}
