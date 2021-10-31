package com.project.couponProject3.controllers;

import com.project.couponProject3.beans.Company;
import com.project.couponProject3.beans.Customer;
import com.project.couponProject3.beans.UserDetails;
import com.project.couponProject3.exceptions.CompanyExceptions.CompanyException;
import com.project.couponProject3.exceptions.CustomerExceptions.CustomerException;
import com.project.couponProject3.exceptions.LoginException;
import com.project.couponProject3.servicesImpl.AdminServiceImpl;
import com.project.couponProject3.servicesImpl.LoginManagerService;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class of admin controller for REST commands
 */
@RestController
@RequestMapping("admin") // http://localhost:8080/admin
@RequiredArgsConstructor

//enable CrossOrigin, allow getting request from web browser on another port (security issue)
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class AdminController extends ClientController {

    private final LoginManagerService loginManagerService;
    private AdminServiceImpl adminService = null;

    @PostMapping("login")
    @Override
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) throws LoginException {
        adminService = (AdminServiceImpl) loginManagerService.login(userDetails.getEmail(), userDetails.getPassword(), userDetails.getClientType());
        if (adminService != null) {
            return new ResponseEntity<>(jwTutil.generateToken(userDetails), HttpStatus.CREATED);
        }
        throw new LoginException();
    }

    /**
     * Method of REST command, using post mapping for adding new company to the system.
     * Endpoint is: add/company
     * Receives and returns the data in JSON data structure.
     *
     * @param token   for authorization
     * @param company the company instance to add
     * @return new token and an indication message of company created
     * @throws CompanyException      if data is incorrect
     * @throws MalformedJwtException if token is invalid
     */
    @PostMapping("add/company")
    public ResponseEntity<?> addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws CompanyException, MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            adminService.addCompany(company);
            return new ResponseEntity<>("Company " + company.getName() + " created!", getHeaders(token), HttpStatus.OK);
        }
        throw new MalformedJwtException("Token is invalid");
    }

    /**
     * Method of REST command, using post mapping for updating existing company.
     * Endpoint is: update/company
     * Receives and returns the data in JSON data structure.
     *
     * @param token   for authorization
     * @param company the company instance to update by it
     * @return new token and an indication message of company updated
     * @throws CompanyException      if data is incorrect
     * @throws MalformedJwtException if token is invalid
     */
    @PostMapping("update/company")
    public ResponseEntity<?> updateCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws CompanyException, MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            System.out.println("id: ===>>> " + company.getId());
            System.out.println(company);
            adminService.updateCompany(company);
            return new ResponseEntity<>("Company #" + company.getId() + " updated!", getHeaders(token), HttpStatus.ACCEPTED);
        }
        throw new MalformedJwtException("Token is invalid");
    }

    /**
     * Method of REST command, using delete mapping for deleting existing company from the DB.
     * Endpoint is: delete/company/{companyID}
     * Receives and returns the data in JSON data structure.
     *
     * @param token     for authorization
     * @param companyID the id of the company to delete
     * @return new token and an indication message of company deleted
     * @throws CompanyException      if data is incorrect
     * @throws MalformedJwtException if token is invalid
     */
    @DeleteMapping("delete/company/{companyID}")
    public ResponseEntity<?> deleteCompany(@RequestHeader(name = "Authorization") String token, @PathVariable int companyID) throws CompanyException, MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            adminService.deleteCompany(companyID);
            return new ResponseEntity<>("Company #" + companyID + " deleted!", getHeaders(token), HttpStatus.ACCEPTED);
        }
        throw new MalformedJwtException("Token is invalid");
    }

    /**
     * Method of REST command, using post mapping for getting all existing companies from the DB.
     * Endpoint is: get/company/all
     * Receives and returns the data in JSON data structure.
     *
     * @param token for authorization
     * @return new token and all the companies from the DB
     * @throws MalformedJwtException if token is invalid
     */
    @PostMapping("get/company/all")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token) throws MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            return new ResponseEntity<>(adminService.getAllCompanies(), getHeaders(token), HttpStatus.OK);
        }
        throw new MalformedJwtException("Token is invalid");
    }

    /**
     * Method of REST command, using post mapping for getting an existing company by its id from the DB.
     * Endpoint is: get/company/{companyID}
     * Receives and returns the data in JSON data structure.
     *
     * @param token     for authorization
     * @param companyID the id of the company to get from DB
     * @return new token and the company requested from the DB
     * @throws MalformedJwtException if token is invalid
     */
    @PostMapping("get/company/{companyID}")
    public ResponseEntity<?> getCompanyByID(@RequestHeader(name = "Authorization") String token, @PathVariable int companyID) throws MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            return new ResponseEntity<>(adminService.getOneCompany(companyID), getHeaders(token), HttpStatus.OK);
        }
        throw new MalformedJwtException("Token is invalid");
    }

    /**
     * Method of REST command, using post mapping for adding new company to the system.
     * Endpoint is: add/customer
     * Receives and returns the data in JSON data structure.
     *
     * @param token    for authorization
     * @param customer the customer instance to add
     * @return new token and an indication message of customer created
     * @throws CustomerException     if data is incorrect
     * @throws MalformedJwtException if token is invalid
     */
    @PostMapping("add/customer")
    public ResponseEntity<?> addCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) throws CustomerException, MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            adminService.addCustomer(customer);
            return new ResponseEntity<>("Customer " + customer.getFirstName() + " " + customer.getLastName() + " created", getHeaders(token), HttpStatus.CREATED);
        }
        throw new MalformedJwtException("Token is invalid");
    }

    /**
     * Method of REST command, using post mapping for updating existing customer.
     * Endpoint is: update/customer
     * Receives and returns the data in JSON data structure.
     *
     * @param token    for authorization
     * @param customer the company instance to update by it
     * @return new token and an indication message of customer updated
     * @throws CustomerException     if data is incorrect
     * @throws MalformedJwtException if token is invalid
     */
    @PostMapping("update/customer")
    public ResponseEntity<?> updateCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) throws CustomerException, MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            adminService.updateCustomer(customer);
            return new ResponseEntity<>("Customer " + customer.getFirstName() + " updated!", getHeaders(token), HttpStatus.ACCEPTED);
        }
        throw new MalformedJwtException("Token is invalid");
    }


    /**
     * Method of REST command, using delete mapping for deleting existing customer from the DB.
     * Endpoint is: delete/customer/{customerID}
     * Receives and returns the data in JSON data structure.
     *
     * @param token      for authorization
     * @param customerID the id of the customer to delete
     * @return new token and an indication message of company deleted
     * @throws CustomerException     if data is incorrect
     * @throws MalformedJwtException if token is invalid
     */
    @DeleteMapping("delete/customer/{customerID}")
    public ResponseEntity<?> deleteCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable int customerID) throws CustomerException, MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            adminService.deleteCustomer(customerID);
            return new ResponseEntity<>("Customer #" + customerID + " deleted!", getHeaders(token), HttpStatus.ACCEPTED);
        }
        throw new MalformedJwtException("Token is invalid");

    }

    /**
     * Method of REST command, using post mapping for getting all existing customers from the DB.
     * Endpoint is: get/customer/all
     * Receives and returns the data in JSON data structure.
     *
     * @param token for authorization
     * @return new token and all the customers from the DB
     * @throws MalformedJwtException if token is invalid
     */
    @PostMapping("get/customer/all")
    public ResponseEntity<?> getAllCustomers(@RequestHeader(name = "Authorization") String token) throws MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            return new ResponseEntity<>(adminService.getAllCustomers(), getHeaders(token), HttpStatus.OK);
        }
        throw new MalformedJwtException("Token is invalid");
    }

    /**
     * Method of REST command, using post mapping for getting an existing customer by its id from the DB.
     * Endpoint is: get/customer/{customerID}
     * Receives and returns the data in JSON data structure.
     *
     * @param token      for authorization
     * @param customerID the id of the customer to get from DB
     * @return new token and the customer requested from the DB
     * @throws MalformedJwtException if token is invalid
     */
    @PostMapping("get/customer/{customerID}")
    public ResponseEntity<?> getCustomerByID(@RequestHeader(name = "Authorization") String token, @PathVariable int customerID) throws MalformedJwtException {
        if (jwTutil.validateToken(token)) {
            return new ResponseEntity<>(adminService.getOneCustomer(customerID), getHeaders(token), HttpStatus.OK);
        }
        throw new MalformedJwtException("Token is invalid");
    }


}
