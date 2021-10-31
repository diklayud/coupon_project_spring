package com.project.couponProject3.controllers;

import com.project.couponProject3.beans.UserDetails;
import com.project.couponProject3.enums.ClientType;
import com.project.couponProject3.exceptions.LoginException;
import com.project.couponProject3.utils.JWTutil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * Superclass of administrator/company/customer controller clients
 */
public abstract class ClientController {

    /**
     * JWT instance for creating new token.
     */
    JWTutil jwTutil = new JWTutil();

    /**
     * Abstract login method, implemented by administrator/company/customer controllers.
     * @param userDetails contains user's id, email, password and client type.
     * @return token.
     * @throws LoginException if login details are wrong.
     */
    public abstract ResponseEntity<?> login(UserDetails userDetails) throws LoginException;

    /**
     * Generate new JWT for every request.
     * @param token the old token that was sent in the request.
     * @return new token made of the claims of the old one.
     */
    protected HttpHeaders getHeaders(String token) {
        UserDetails userdetails = new UserDetails();
        userdetails.setEmail(jwTutil.extractEmail(token));
        userdetails.setClientType(ClientType.valueOf((String) jwTutil.extractAllClaims(token).get("clientType")));
        userdetails.setId((int) jwTutil.extractAllClaims(token).get("userId"));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", jwTutil.generateToken(userdetails));
        return httpHeaders;
    }

}
