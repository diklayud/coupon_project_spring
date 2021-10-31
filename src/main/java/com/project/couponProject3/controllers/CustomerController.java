package com.project.couponProject3.controllers;

import com.project.couponProject3.beans.Coupon;
import com.project.couponProject3.beans.UserDetails;
import com.project.couponProject3.enums.Category;
import com.project.couponProject3.exceptions.CouponExceptions.CouponException;
import com.project.couponProject3.exceptions.LoginException;
import com.project.couponProject3.servicesImpl.CompanyServiceImpl;
import com.project.couponProject3.servicesImpl.CustomerServiceImpl;
import com.project.couponProject3.servicesImpl.LoginManagerService;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class of customer controller for REST commands
 */
@RestController
@RequestMapping("customer") // http://localhost:8080/customer
@RequiredArgsConstructor

//enable CrossOrigin, allow getting request from web browser on another port (security issue)
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class CustomerController extends ClientController {

    private final LoginManagerService loginManagerService;
    private CustomerServiceImpl customerService = null;

    private final CompanyServiceImpl companyService;


    @PostMapping("login")
    @Override
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) throws LoginException {
        customerService = (CustomerServiceImpl) loginManagerService.login(userDetails.getEmail(), userDetails.getPassword(), userDetails.getClientType());
        if (customerService != null) {
            int id = customerService.getCustomerID();
            userDetails.setId(id);
            return new ResponseEntity<>(jwTutil.generateToken(userDetails), HttpStatus.CREATED);
        }
        throw new LoginException();
    }

    /**
     * Method of REST command, using post mapping for making a purchase of a coupon by customer.
     * Endpoint is: coupon/purchase
     * Receives and returns the data in JSON data structure.
     *
     * @param token  for authorization
     * @param coupon the coupon instance to purchase
     * @return new token and an indication message of coupon purchased
     * @throws CouponException       if data is incorrect
     * @throws MalformedJwtException if token is invalid
     */
    @PostMapping("coupon/purchase")
    public ResponseEntity<?> purchaseCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws CouponException, MalformedJwtException {
        if (jwTutil.validateToken(token)) {

            if (coupon.getCompany() == null) {
                companyService.setCompanyID(coupon.getCompany_id_ui());
            } else {
                companyService.setCompanyID(coupon.getCompany().getId());
            }
            Coupon couponToPurchase = companyService.getCouponById(coupon.getId());
            customerService.purchaseCoupon(couponToPurchase);
            return new ResponseEntity<>("Coupon #" + coupon.getId() + " purchased!", getHeaders(token), HttpStatus.OK);
        }
        throw new MalformedJwtException("Token is invalid");
    }


    /**
     * Method of REST command, using post mapping for getting all existing customer's coupons from the DB.
     * Endpoint is: get/coupon/all
     * Receives and returns the data in JSON data structure.
     *
     * @param token for authorization
     * @return new token and all the customer's coupons from the DB
     * @throws MalformedJwtException if token is invalid
     */
    @PostMapping("get/coupon/all")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name = "Authorization") String token) throws MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            return new ResponseEntity<>(customerService.getCustomerCoupons(), getHeaders(token), HttpStatus.OK);
        }
        throw new MalformedJwtException("Token is invalid");
    }


    /**
     * Method of REST command, using post mapping for getting an existing customer's coupons by category from the DB.
     * Endpoint is: get/coupon/category/{category}
     * Receives and returns the data in JSON data structure.
     *
     * @param token    for authorization
     * @param category the category of the coupons to get from DB
     * @return new token and the customer's coupons requested from the DB
     * @throws MalformedJwtException if token is invalid
     */
    @PostMapping("get/coupon/category/{category}")
    public ResponseEntity<?> getCustomerCouponsByCategory(@RequestHeader(name = "Authorization") String token, @PathVariable Category category) throws MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            return new ResponseEntity<>(customerService.getCustomerCouponsByCategory(category), getHeaders(token), HttpStatus.OK);
        }
        throw new MalformedJwtException("Token is invalid");
    }

    /**
     * Method of REST command, using post mapping for getting an existing customer's coupons by maximum from the DB.
     * Endpoint is: get/coupon/price/{maxPrice}
     * Receives and returns the data in JSON data structure.
     *
     * @param token    for authorization
     * @param maxPrice the maximum price of the coupons to get from DB
     * @return new token and the customer's coupons requested from the DB
     * @throws MalformedJwtException if token is invalid
     */
    @PostMapping("get/coupon/price/{maxPrice}")
    public ResponseEntity<?> getCustomerCouponsByMaxPrice(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) throws MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            return new ResponseEntity<>(customerService.getCustomerCouponsByMaxPrice(maxPrice), getHeaders(token), HttpStatus.OK);
        }
        throw new MalformedJwtException("Token is invalid");
    }


    /**
     * Method of REST command, using post mapping for getting the details of the logged-in customer from the DB.
     * Endpoint is: get/details
     * Receives and returns the data in JSON data structure.
     *
     * @param token for authorization
     * @return new token and the customer's details from the DB
     * @throws MalformedJwtException if token is invalid
     */
    @PostMapping("get/details")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader(name = "Authorization") String token) throws MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            return new ResponseEntity<>(customerService.getCustomerDetails(), getHeaders(token), HttpStatus.OK);
        }
        throw new MalformedJwtException("Token is invalid");
    }


}
