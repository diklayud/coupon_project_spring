package com.project.couponProject3.exceptions.CompanyExceptions;

/**
 * Class of company not found exception
 */
public class CompanyNotFoundException extends CompanyException {

    /**
     * C'tor for exception caused by an unsuccessful try of finding a company
     */
    public CompanyNotFoundException() {
    }

    /**
     * C'tor for exception caused by an unsuccessful try of finding a company
     *
     * @param message get string into the C'tor
     */
    public CompanyNotFoundException(String message) {
        super(message);
    }
}
