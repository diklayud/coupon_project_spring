package com.project.couponProject3.exceptions.CompanyExceptions;

/**
 * Class of delete company exception
 */
public class CompanyDeleteException extends CompanyException {

    /**
     * C'tor for exception caused by an unsuccessful try of deleting a company
     */
    public CompanyDeleteException() {
    }

    /**
     * C'tor for exception caused by an unsuccessful try of deleting a company
     *
     * @param message get string into the C'tor
     */
    public CompanyDeleteException(String message) {
        super(message);
    }
}
