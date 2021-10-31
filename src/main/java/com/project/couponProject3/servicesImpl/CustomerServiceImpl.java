package com.project.couponProject3.servicesImpl;

import com.project.couponProject3.beans.Coupon;
import com.project.couponProject3.beans.Customer;
import com.project.couponProject3.enums.Category;
import com.project.couponProject3.exceptions.CouponExceptions.CouponException;
import com.project.couponProject3.exceptions.CouponExceptions.CouponPurchaseException;
import com.project.couponProject3.exceptions.LoginException;
import com.project.couponProject3.services.ClientService;
import com.project.couponProject3.services.CustomerService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
/**
 * Implements all the method of customer actions
 */
public class CustomerServiceImpl extends ClientService implements CustomerService {

    private int customerID;

    @Override
    public boolean login(String email, String password) throws LoginException {
        Customer customer = customerRepo.findByEmailAndPassword(email, password);
        if (customer != null) {
            this.customerID = customer.getId();
            System.out.println("HELLO CUSTOMER: " + customer.getFirstName() + "! WELCOME!!");
            return true;
        }
        throw new LoginException("Cannot login, incorrect email or password");
    }

    public void purchaseCoupon(Coupon coupon) throws CouponException {
        Customer customerFromDB = getCustomerDetails();
        List<Customer> customers = coupon.getCustomers();
        for (Customer item : customers) {
            if (item.getId() == customerFromDB.getId()) {
                throw new CouponPurchaseException("Cannot purchase coupon more than once");
            }
        }
        if (coupon.getAmount() <= 0) {
            throw new CouponPurchaseException("Cannot purchase coupon, out of stock");
        }
        if (!coupon.getEndDate().toLocalDate().isAfter(LocalDate.now())) {
            throw new CouponPurchaseException("Cannot purchase coupon, coupon's end date is expired");
        }
        customerFromDB.getCoupons().add(coupon);
        coupon.getCustomers().add(customerFromDB);
        coupon.setAmount(coupon.getAmount() - 1);
        customerRepo.saveAndFlush(customerFromDB);
    }

    public List<Coupon> getCustomerCoupons() {
        return getCustomerDetails().getCoupons();
    }

    public List<Coupon> getCustomerCouponsByCategory(Category category) {
        List<Coupon> coupons = getCustomerDetails().getCoupons();
        List<Coupon> couponsOfCategory = coupons.stream()
                .filter(coupon -> coupon.getCategory().equals(category))
                .collect(Collectors.toList());
        return couponsOfCategory;
    }

    public List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice) {
        List<Coupon> coupons = getCustomerDetails().getCoupons();
        List<Coupon> couponsByMaxPrice = coupons.stream()
                .filter(coupon -> coupon.getPrice() < maxPrice)
                .collect(Collectors.toList());
        return couponsByMaxPrice;
    }

    public Customer getCustomerDetails() {
        return customerRepo.findById(this.customerID);
    }


}
