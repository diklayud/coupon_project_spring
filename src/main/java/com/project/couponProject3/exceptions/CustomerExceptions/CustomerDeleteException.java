package com.project.couponProject3.exceptions.CustomerExceptions;

/**
 * Class of delete customer exception
 */
public class CustomerDeleteException extends CustomerException {

    /**
     * C'tor for exception caused by an unsuccessful try of deleting a customer
     */
    public CustomerDeleteException() {
    }

    /**
     * C'tor for exception caused by an unsuccessful try of deleting a customer
     *
     * @param message get string into the C'tor
     */
    public CustomerDeleteException(String message) {
        super(message);
    }
}
