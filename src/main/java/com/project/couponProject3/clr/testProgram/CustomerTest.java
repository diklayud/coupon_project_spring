package com.project.couponProject3.clr.testProgram;

import com.project.couponProject3.enums.Category;
import com.project.couponProject3.enums.ClientType;
import com.project.couponProject3.exceptions.CouponExceptions.CouponException;
import com.project.couponProject3.exceptions.LoginException;
import com.project.couponProject3.servicesImpl.AdminServiceImpl;
import com.project.couponProject3.servicesImpl.CustomerServiceImpl;
import com.project.couponProject3.servicesImpl.LoginManagerService;
import com.project.couponProject3.utils.ArtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@RequiredArgsConstructor
/**
 * Class of testing the company service methods
 */
public class CustomerTest implements CommandLineRunner {

    private final LoginManagerService loginManagerService;
    private final AdminServiceImpl adminService;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("\n" + ArtUtil.CUSTOMER_TEST_HEADER);

        // =========== LOGIN ===========
        System.out.println("\n\nLOGIN TEST: =============");
        // try to login with wrong email. EXPECTED RESULT ==> SHOULD SHOW AN EXCEPTION
        try {
            loginManagerService.login("check@check", "new1212", ClientType.CUSTOMER);
        } catch (LoginException e) {
            System.out.println(e.getMessage());
        }

        // try to login with wrong password. EXPECTED RESULT ==> SHOULD SHOW AN EXCEPTION
        try {
            loginManagerService.login("customer1new@mail.com", "check", ClientType.CUSTOMER);
        } catch (LoginException e) {
            System.out.println(e.getMessage());
        }

        // try to login with right email and password. EXPECTED RESULT ==> SHOULD LOGIN SUCCESSFULLY :)
        System.out.println("login with right details:");
        CustomerServiceImpl customerService = (CustomerServiceImpl) loginManagerService.login("customer1new@mail.com", "new1212", ClientType.CUSTOMER);

        // =========== ADD PURCHASE COUPON ===========
        System.out.println("\n\nPURCHASE COUPON TEST: =====================");
        // try to purchase coupons. EXPECTED RESULT ==> SHOULD ADD SUCCESSFULLY :)
        // add 3 coupons to customer #1
        try {
            customerService.purchaseCoupon(adminService.getOneCompany(1).getCoupons().get(0));
            customerService.purchaseCoupon(adminService.getOneCompany(2).getCoupons().get(0));
            customerService.purchaseCoupon(adminService.getOneCompany(4).getCoupons().get(0));
            System.out.println("coupons purchased successfully");
        } catch (CouponException e) {
            System.out.println(e.getMessage());
        }

        // try to purchase a coupon already purchased by this customer. EXPECTED RESULT ==> SHOULD THROW EXCEPTION
        try {
            customerService.purchaseCoupon(adminService.getOneCompany(1).getCoupons().get(0));
        } catch (CouponException e) {
            System.out.println(e.getMessage());
        }

        // =========== GET CUSTOMER'S PURCHASED COUPON ===========
        System.out.println("\n\nGET ALL CUSTOMER'S COUPONS TEST: =====================");
        customerService.getCustomerCoupons().forEach(System.out::println);
        System.out.println(" ");

        // login with other customer to add 3 coupons into it
        customerService = (CustomerServiceImpl) loginManagerService.login("cust2@mail.com", "222", ClientType.CUSTOMER);
        // add 3 coupons to customer #2
        try {
            customerService.purchaseCoupon(adminService.getOneCompany(1).getCoupons().get(0));
            customerService.purchaseCoupon(adminService.getOneCompany(2).getCoupons().get(0));
            customerService.purchaseCoupon(adminService.getOneCompany(4).getCoupons().get(1));
            System.out.println("coupons purchased successfully");
        } catch (CouponException e) {
            System.out.println(e.getMessage());
        }

        // try to purchase a coupon with amount ZERO. EXPECTED RESULT ==> SHOULD THROW EXCEPTION
        try {
            customerService.purchaseCoupon(adminService.getOneCompany(4).getCoupons().get(0));
        } catch (CouponException e) {
            System.out.println(e.getMessage());
        }

        // try to purchase a coupon with expired end date. EXPECTED RESULT ==> SHOULD THROW EXCEPTION
        // ========== *** can't create the test: don't have expired coupons in DB and
        // can't add expired coupons to DB by using companyService.addCoupon or  companyService.updateCoupon.*** ==========

        // =========== GET CUSTOMER'S PURCHASED COUPON ===========
        System.out.println("\n\nGET ALL CUSTOMER'S COUPONS TEST: =====================");
        customerService.getCustomerCoupons().forEach(System.out::println);

        // =========== GET CUSTOMER'S PURCHASED COUPON OF CATEGORY ===========
        System.out.println("\n\nGET ALL CUSTOMER'S COUPONS BY CATEGORY TEST: =====================");
        // get list of coupons of category this customer bought. EXPECTED RESULT ==> SHOULD GET THEM SUCCESSFULLY :)
        customerService.getCustomerCouponsByCategory(Category.FOOD).forEach(System.out::println);
        System.out.println("=====================================================================");
        // get list of coupons of category this customer didn't buy. EXPECTED RESULT ==> RETURNS AN EMPTY LIST
        System.out.println(customerService.getCustomerCouponsByCategory(Category.VACATION));

        // =========== GET CUSTOMER'S PURCHASED COUPON TO MAXIMUM PRICE ===========
        System.out.println("\n\nGET ALL CUSTOMER'S COUPONS BY MAX PRICE TEST: =====================");
        // get list of coupons of price this customer bought. EXPECTED RESULT ==> SHOULD GET THEM SUCCESSFULLY :)
        customerService.getCustomerCouponsByMaxPrice(300).forEach(System.out::println);
        System.out.println("=====================================================================");
        // get list of coupons of price this customer didn't buy. EXPECTED RESULT ==> RETURNS AN EMPTY LIST
        System.out.println(customerService.getCustomerCouponsByMaxPrice(50));

        // =========== GET CUSTOMER'S DETAILS ===========
        System.out.println("\n\nGET CUSTOMER'S DETAILS TEST: =====================");
        System.out.println(customerService.getCustomerDetails());
    }
}
