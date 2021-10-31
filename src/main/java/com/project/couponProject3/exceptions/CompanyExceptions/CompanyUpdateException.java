package com.project.couponProject3.exceptions.CompanyExceptions;

/**
 * Class of company update exception
 */
public class CompanyUpdateException extends CompanyException {

    /**
     * C'tor for exception caused by an unsuccessful try of updating a customer
     */
    public CompanyUpdateException() {
    }

    /**
     * C'tor for exception caused by an unsuccessful try of updating a customer
     *
     * @param message get string into the C'tor
     */
    public CompanyUpdateException(String message) {
        super(message);
    }
}
