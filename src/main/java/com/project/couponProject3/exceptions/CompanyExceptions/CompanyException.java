package com.project.couponProject3.exceptions.CompanyExceptions;

/**
 * Super class of company exceptions
 */
public class CompanyException extends Exception {

    /**
     * Superclass C'tor of company exceptions
     */
    public CompanyException() {
    }

    /**
     * Superclass C'tor of company exceptions
     *
     * @param message get string into the C'tor
     */
    public CompanyException(String message) {
        super(message);
    }
}
