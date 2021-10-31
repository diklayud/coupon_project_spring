package com.project.couponProject3.controllers;

import com.project.couponProject3.beans.Coupon;
import com.project.couponProject3.beans.UserDetails;
import com.project.couponProject3.enums.Category;
import com.project.couponProject3.exceptions.CompanyExceptions.CompanyException;
import com.project.couponProject3.exceptions.CouponExceptions.CouponException;
import com.project.couponProject3.exceptions.LoginException;
import com.project.couponProject3.servicesImpl.CompanyServiceImpl;
import com.project.couponProject3.servicesImpl.LoginManagerService;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class of company controller for REST commands
 */
@RestController
@RequestMapping("company") // http://localhost:8080/company
@RequiredArgsConstructor

//enable CrossOrigin, allow to get request from web browser on another port (security issue)
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class CompanyController extends ClientController {

    private final LoginManagerService loginManagerService;
    private CompanyServiceImpl companyService = null;

    @PostMapping("login")
    @Override
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) throws LoginException {
        companyService = (CompanyServiceImpl) loginManagerService.login(userDetails.getEmail(), userDetails.getPassword(), userDetails.getClientType());
        if (companyService != null) {
            int id = companyService.getCompanyID();
            userDetails.setId(id);
            return new ResponseEntity<>(jwTutil.generateToken(userDetails), HttpStatus.CREATED);
        }
        throw new LoginException();
    }

    /**
     * Method of REST command, using post mapping for adding new coupon to the system.
     * Endpoint is: add/coupon
     * Receives and returns the data in JSON data structure.
     *
     * @param token  for authorization
     * @param coupon the coupon instance to add
     * @return new token and an indication message of coupon created
     * @throws CouponException       if data is incorrect
     * @throws MalformedJwtException if token is invalid
     */
    @PostMapping("add/coupon")
    public ResponseEntity<?> addCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws CouponException, MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            companyService.addCoupon(coupon);
            return new ResponseEntity<>("Coupon " + coupon.getTitle() + " added!", getHeaders(token), HttpStatus.CREATED);
        }
        throw new MalformedJwtException("Token is invalid");
    }

    /**
     * Method of REST command, using post mapping for updating existing coupon.
     * Endpoint is: update/coupon
     * Receives and returns the data in JSON data structure.
     *
     * @param token  for authorization
     * @param coupon the coupon instance to update by it
     * @return new token and an indication message of company updated
     * @throws CouponException       if data is incorrect
     * @throws MalformedJwtException if token is invalid
     */
    @PostMapping("update/coupon")
    public ResponseEntity<?> updateCoupon(@RequestHeader(name = "Authorization") String token,
                                          @RequestBody Coupon coupon) throws CouponException, MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            companyService.updateCoupon(coupon);
            return new ResponseEntity<>("Coupon " + coupon.getTitle() + " updated!", getHeaders(token), HttpStatus.ACCEPTED);
        }
        throw new MalformedJwtException("Token is invalid");
    }

    /**
     * Method of REST command, using delete mapping for deleting existing coupon from the DB.
     * Endpoint is: delete/coupon/{couponID}
     * Receives and returns the data in JSON data structure.
     *
     * @param token    for authorization
     * @param couponID the id of the coupon to delete
     * @return new token and an indication message of coupon deleted
     * @throws CouponException       if data is incorrect
     * @throws MalformedJwtException if token is invalid
     */
    @DeleteMapping("delete/coupon/{couponID}")
    public ResponseEntity<?> deleteCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable int couponID) throws CouponException, MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            companyService.deleteCoupon(couponID);
            return new ResponseEntity<>("Coupon #" + couponID + " deleted!", getHeaders(token), HttpStatus.ACCEPTED);
        }
        throw new MalformedJwtException("Token is invalid");
    }

    /**
     * Method of REST command, using post mapping for getting all existing company's coupons from the DB.
     * Endpoint is: get/coupon/all
     * Receives and returns the data in JSON data structure.
     *
     * @param token for authorization
     * @return new token and all the company's coupons from the DB
     * @throws MalformedJwtException if token is invalid
     */
    @PostMapping("get/coupon/all")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name = "Authorization") String token) throws MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            return new ResponseEntity<>(companyService.getCompanyCoupons(), getHeaders(token), HttpStatus.OK);
        }
        throw new MalformedJwtException("Token is invalid");
    }

    /**
     * Method of REST command, using post mapping for getting an existing company's coupons by category from the DB.
     * Endpoint is: get/coupon/category/{category}
     * Receives and returns the data in JSON data structure.
     *
     * @param token    for authorization
     * @param category the category of the coupons to get from DB
     * @return new token and the company's coupons requested from the DB
     * @throws MalformedJwtException if token is invalid
     */
    @PostMapping("get/coupon/category/{category}")
    public ResponseEntity<?> getCompanyCouponsByCategory(@RequestHeader(name = "Authorization") String token, @PathVariable Category category) throws MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            return new ResponseEntity<>(companyService.getCompanyCouponsByCategory(category), getHeaders(token), HttpStatus.OK);
        }
        throw new MalformedJwtException("Token is invalid");
    }

    /**
     * Method of REST command, using post mapping for getting an existing company's coupons by maximum from the DB.
     * Endpoint is: get/coupon/price/{maxPrice}
     * Receives and returns the data in JSON data structure.
     *
     * @param token    for authorization
     * @param maxPrice the maximum price of the coupons to get from DB
     * @return new token and the company's coupons requested from the DB
     * @throws MalformedJwtException if token is invalid
     */
    @PostMapping("get/coupon/price/{maxPrice}")
    public ResponseEntity<?> getCompanyCouponsByMaxPrice(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) throws MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            return new ResponseEntity<>(companyService.getCompanyCouponsByMaxPrice(maxPrice), getHeaders(token), HttpStatus.OK);
        }
        throw new MalformedJwtException("Token is invalid");
    }

    /**
     * Method of REST command, using post mapping for getting the details of the logged-in company from the DB.
     * Endpoint is: get/details
     * Receives and returns the data in JSON data structure.
     *
     * @param token for authorization
     * @return new token and the company's details from the DB
     * @throws MalformedJwtException if token is invalid
     */
    @PostMapping("get/details")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader(name = "Authorization") String token) throws MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            return new ResponseEntity<>(companyService.getCompanyDetails(), getHeaders(token), HttpStatus.OK);
        }
        throw new MalformedJwtException("Token is invalid");
    }


}
