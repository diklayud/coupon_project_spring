package com.project.couponProject3.exceptions.CustomerExceptions;

/**
 * Class of add customer exception
 */
public class CustomerAddException extends CustomerException {

    /**
     * C'tor for exception caused by an unsuccessful try of adding a customer
     */
    public CustomerAddException() {
    }

    /**
     * C'tor for exception caused by an unsuccessful try of adding a customer
     *
     * @param message get string into the C'tor
     */
    public CustomerAddException(String message) {
        super(message);
    }
}
