package com.project.couponProject3.servicesImpl;

import com.project.couponProject3.beans.Coupon;
import com.project.couponProject3.repositories.CouponRepo;
import com.project.couponProject3.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {

    @Autowired
    protected CouponRepo couponRepo;

    @Override
    public List<Coupon> getAllCouponsInSystem() {
        return couponRepo.findAll();
    }


}
