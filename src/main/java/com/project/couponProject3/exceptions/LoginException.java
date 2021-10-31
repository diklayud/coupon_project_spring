package com.project.couponProject3.exceptions;

/**
 * Class of login exception
 */
public class LoginException extends Exception {

    /**
     * C'tor for exception caused by bad login
     */
    public LoginException() {
    }

    /**
     * C'tor for exception caused by bad login
     *
     * @param message get string into the C'tor
     */
    public LoginException(String message) {
        super(message);
    }
}
