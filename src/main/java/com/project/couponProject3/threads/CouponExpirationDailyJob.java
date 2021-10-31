package com.project.couponProject3.threads;

import com.project.couponProject3.beans.Coupon;
import com.project.couponProject3.beans.Customer;
import com.project.couponProject3.repositories.CouponRepo;
import com.project.couponProject3.repositories.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
/**
 * Class of thread. Looking for expired coupons once a day
 */
public class CouponExpirationDailyJob {

    private final CouponRepo couponRepo;
    private final CustomerRepo customerRepo;

    /**
     * This method runs once a day, looking for coupons in DB which their end date passed and
     * deletes the expired coupons and their purchases from the DB
     */
    @Async
    @Scheduled(cron = "0 5 0 * * ?", zone = "Asia/Jerusalem")
    public void eraseCoupon() {
        List<Coupon> expiredCoupons = couponRepo.findByEndDateBefore(Date.valueOf(LocalDate.now()));
        for (Coupon item : expiredCoupons) {
            List<Customer> customers = item.getCustomers();
            customers.forEach(customer -> customer.getCoupons().removeIf(couponToDelete -> couponToDelete.getId() == item.getId()));
            customerRepo.saveAll(item.getCustomers());
        }
        couponRepo.deleteAll(expiredCoupons);
    }
}
