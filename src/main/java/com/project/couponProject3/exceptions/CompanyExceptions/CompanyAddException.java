package com.project.couponProject3.exceptions.CompanyExceptions;

/**
 * Class of add company exception
 */
public class CompanyAddException extends CompanyException {

    /**
     * C'tor for exception caused by an unsuccessful try of adding a company
     */
    public CompanyAddException() {
    }

    /**
     * C'tor for exception caused by an unsuccessful try of adding a company
     *
     * @param message get string into the C'tor
     */
    public CompanyAddException(String message) {
        super(message);
    }
}
