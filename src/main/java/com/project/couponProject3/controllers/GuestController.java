package com.project.couponProject3.controllers;

import com.project.couponProject3.servicesImpl.GuestServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for unregistered clients.
 * This controller receives and returns JSON data structure
 */
@RestController
@RequestMapping("guest") // http://localhost:8080/guest
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
public class GuestController {

    private final GuestServiceImpl guestService;

    /**
     * Method for getting all the coupons from the DB.
     *
     * @return all the coupons that exist in the system
     */
    @PostMapping("get/coupon/all")
    public ResponseEntity<?> getAllCouponsInSystem() {
        return new ResponseEntity<>(guestService.getAllCouponsInSystem(), HttpStatus.OK);
    }

}
