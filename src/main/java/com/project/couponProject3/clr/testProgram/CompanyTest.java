package com.project.couponProject3.clr.testProgram;

import com.project.couponProject3.beans.Coupon;
import com.project.couponProject3.enums.Category;
import com.project.couponProject3.enums.ClientType;
import com.project.couponProject3.exceptions.CouponExceptions.CouponException;
import com.project.couponProject3.exceptions.LoginException;
import com.project.couponProject3.servicesImpl.AdminServiceImpl;
import com.project.couponProject3.servicesImpl.CompanyServiceImpl;
import com.project.couponProject3.servicesImpl.LoginManagerService;
import com.project.couponProject3.utils.ArtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
@Order(2)
@RequiredArgsConstructor
/**
 * Class of testing the company service methods
 */
public class CompanyTest implements CommandLineRunner {

    private final LoginManagerService loginManagerService;
    private final AdminServiceImpl adminService;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("\n" + ArtUtil.COMPANY_TEST_HEADER);

        // =========== LOGIN ===========
        System.out.println("\n\nLOGIN TEST: =============");
        // try to login with wrong email. EXPECTED RESULT ==> SHOULD SHOW AN EXCEPTION
        try {
            loginManagerService.login("check@check", "newPass123", ClientType.COMPANY);
        } catch (LoginException e) {
            System.out.println(e.getMessage());
        }

        // try to login with wrong password. EXPECTED RESULT ==> SHOULD SHOW AN EXCEPTION
        try {
            loginManagerService.login("new@email.com", "check", ClientType.COMPANY);
        } catch (LoginException e) {
            System.out.println(e.getMessage());
        }

        // try to login with right email and password. EXPECTED RESULT ==> SHOULD LOGIN SUCCESSFULLY :)
        System.out.println("login with right details:");
        CompanyServiceImpl companyService = null;
        try {
            companyService = (CompanyServiceImpl) loginManagerService.login("new@email.com", "newPass123", ClientType.COMPANY);
        } catch (LoginException e) {
            System.out.println(e.getMessage());
        }

        // =========== ADD COUPON ===========
        System.out.println("\n\nADD COUPON TEST: =====================");
        // try to add coupons. EXPECTED RESULT ==> SHOULD ADD SUCCESSFULLY :)
        // add 2 coupons to company #1
        Coupon coupon1 = Coupon.builder()
                .amount(10)
                .title("20% off!")
                .category(Category.FOOD)
                .description("discount in markets")
                .price(100)
                .image("groceries.img")
                .startDate(Date.valueOf("2021-07-01"))
                .endDate(Date.valueOf("2022-08-01"))
                .company(adminService.getOneCompany(companyService.getCompanyID()))
                .company_id_ui(companyService.getCompanyID())
                .build();
        Coupon coupon2 = Coupon.builder()
                .amount(42)
                .title("Restaurant 12% off!")
                .category(Category.RESTAURANT)
                .description("discount in italian restaurants")
                .price(120)
                .image("food.img")
                .startDate(Date.valueOf("2021-07-07"))
                .endDate(Date.valueOf("2022-08-08"))
                .company(adminService.getOneCompany(companyService.getCompanyID()))
                .company_id_ui(companyService.getCompanyID())
                .build();
        try {
            companyService.addCoupon(coupon1);
            companyService.addCoupon(coupon2);
            System.out.println("coupons added successfully");
        } catch (CouponException e) {
            System.out.println(e.getMessage());
        }

        // =========== GET ALL COMPANY'S COUPONS ===========
        // get all company's coupons from DB. EXPECTED RESULT ==> SHOULD GET THEM SUCCESSFULLY
        System.out.println("\n\nGET ALL COMPANY'S COUPONS TEST: =====================");
        companyService.getCompanyCoupons().forEach(System.out::println);
        System.out.println(" ");

        // login with other company to add 2 coupons into it
        // add 2 coupons to company #2
        companyService = (CompanyServiceImpl) loginManagerService.login("comp2@b.com", "222", ClientType.COMPANY);

        Coupon coupon3 = Coupon.builder()
                .amount(3)
                .title("Aldo 40% off!")
                .category(Category.FASHION)
                .description("men shoes on sale")
                .price(230)
                .image("shoes.img")
                .startDate(Date.valueOf("2021-06-25"))
                .endDate(Date.valueOf("2025-10-01"))
                .company(adminService.getOneCompany(companyService.getCompanyID()))
                .company_id_ui(companyService.getCompanyID())
                .build();
        Coupon coupon4 = Coupon.builder()
                .amount(23)
                .title("Zara 10% off!")
                .category(Category.FASHION)
                .description("women dresses")
                .price(150)
                .image("dress.img")
                .startDate(Date.valueOf("2021-07-28"))
                .endDate(Date.valueOf(LocalDate.now()))
                .company(adminService.getOneCompany(companyService.getCompanyID()))
                .company_id_ui(companyService.getCompanyID())
                .build();
        try {
            companyService.addCoupon(coupon3);
            companyService.addCoupon(coupon4);
            System.out.println("coupons added successfully");
        } catch (CouponException e) {
            System.out.println(e.getMessage());
        }

        // =========== GET ALL COMPANY'S COUPONS ===========
        // get all company's coupons from DB. EXPECTED RESULT ==> SHOULD GET THEM SUCCESSFULLY
        System.out.println("\n\nGET ALL COMPANY'S COUPONS TEST: =====================");
        companyService.getCompanyCoupons().forEach(System.out::println);
        System.out.println(" ");

        // login with other company to add 1 coupon into it
        // add 2 coupons to company #4
        companyService = (CompanyServiceImpl) loginManagerService.login("comp4@d.com", "444", ClientType.COMPANY);

        Coupon coupon5 = Coupon.builder()
                .amount(2)
                .title("Mixer on sale")
                .category(Category.ELECTRICITY)
                .description("Strong mixer 15% off")
                .price(430)
                .image("https://images.pexels.com/photos/1450903/pexels-photo-1450903.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940")
//                .image("https://cdn.iconscout.com/icon/premium/png-256-thumb/mixer-2530981-2118646.png")
                .startDate(Date.valueOf("2021-05-07"))
                .endDate(Date.valueOf("2022-12-31"))
                .company(adminService.getOneCompany(companyService.getCompanyID()))
                .company_id_ui(companyService.getCompanyID())
                .build();
        try {
            companyService.addCoupon(coupon5);
            System.out.println("coupon added successfully");
        } catch (CouponException e) {
            System.out.println(e.getMessage());
        }

        // try to add a coupon with existing title of coupon of OTHER company. EXPECTED RESULT ==> SHOULD ADD SUCCESSFULLY :)
        Coupon coupon6 = Coupon.builder()
                .amount(5)
                .title("20% off!")
                .category(Category.ELECTRICITY)
                .description("Great in T.V 20% off")
                .price(2_430)
                .image("https://images.pexels.com/photos/6976103/pexels-photo-6976103.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940")
//                .image("https://static.thenounproject.com/png/3541697-200.png")
                .startDate(Date.valueOf("2021-07-07"))
                .endDate(Date.valueOf("2022-08-08"))
                .company(adminService.getOneCompany(companyService.getCompanyID()))
                .company_id_ui(companyService.getCompanyID())
                .build();
        try {
            companyService.addCoupon(coupon6);
            System.out.println("coupon added successfully");
        } catch (CouponException e) {
            System.out.println(e.getMessage());
        }

        // try to add a coupon with existing title of coupon of THIS company. EXPECTED RESULT ==> SHOULD THROW EXCEPTION
        Coupon coupon7 = Coupon.builder()
                .amount(15)
                .title("20% off!")
                .category(Category.ELECTRICITY)
                .description("computer 20% off")
                .price(2_550)
                .image("computer.img")
                .startDate(Date.valueOf("2021-07-10"))
                .endDate(Date.valueOf("2022-11-12"))
                .company(adminService.getOneCompany(companyService.getCompanyID()))
                .company_id_ui(companyService.getCompanyID())
                .build();
        try {
            companyService.addCoupon(coupon7);
        } catch (CouponException e) {
            System.out.println(e.getMessage());
        }

        // try to add a coupon with expired end date of coupon. EXPECTED RESULT ==> SHOULD THROW EXCEPTION
        Coupon coupon8 = Coupon.builder()
                .amount(5)
                .title("7% off!")
                .category(Category.ELECTRICITY)
                .description("ipad 7% off")
                .price(1_550)
                .image("ipad.img")
                .startDate(Date.valueOf("2021-07-10"))
                .endDate(Date.valueOf("2021-07-25"))
                .company(adminService.getOneCompany(companyService.getCompanyID()))
                .company_id_ui(companyService.getCompanyID())
                .build();
        try {
            companyService.addCoupon(coupon8);
        } catch (CouponException e) {
            System.out.println(e.getMessage());
        }

        // =========== GET ALL COMPANY'S COUPONS ===========
        // get all company's coupons from DB. EXPECTED RESULT ==> SHOULD GET THEM SUCCESSFULLY
        System.out.println("\n\nGET ALL COMPANY'S COUPONS TEST: =====================");
        companyService.getCompanyCoupons().forEach(System.out::println);

        // =========== UPDATE COUPON ===========
        System.out.println("\n\nUPDATE COUPON TEST: =====================");
        // try to update a coupon's details to new unique values.
        // EXPECTED RESULT ==> SHOULD UPDATE SUCCESSFULLY :)
        coupon5.setAmount(1);
        coupon5.setTitle("CRAZY SALE");
        coupon5.setDescription("Strong mixer 55% off");
        coupon5.setPrice(130);
        try {
            companyService.updateCoupon(coupon5);
            System.out.println("coupon updated successfully");
        } catch (CouponException e) {
            System.out.println(e.getMessage());
        }

        // try to update a coupon's title to existing title of other coupon of THIS company.
        // EXPECTED RESULT ==> SHOULD THROW EXCEPTION
        coupon6.setTitle("CRAZY SALE");
        try {
            companyService.updateCoupon(coupon6);
        } catch (CouponException e) {
            System.out.println(e.getMessage());
        }

        // try to update a coupon to expired end date. EXPECTED RESULT ==> SHOULD THROW EXCEPTION
        coupon5.setEndDate(Date.valueOf("2021-07-28"));
        try {
            companyService.updateCoupon(coupon5);
        } catch (CouponException e) {
            System.out.println(e.getMessage());
        }

        // =========== GET ALL COMPANY'S COUPONS TO MAXIMUM PRICE ===========
        System.out.println("\n\nGET ALL COMPANY'S COUPONS BY MAX PRICE TEST: =====================");
        // get list of coupons of price this company has. EXPECTED RESULT ==> SHOULD GET THEM SUCCESSFULLY :)
        List<Coupon> maxPriceCoupons = companyService.getCompanyCouponsByMaxPrice(150);
        maxPriceCoupons.forEach(System.out::println);
        System.out.println("==============================================================");
        maxPriceCoupons = companyService.getCompanyCouponsByMaxPrice(3_000);
        maxPriceCoupons.forEach(System.out::println);
        System.out.println("==============================================================");
        // get list of coupons of price this company doesn't have. EXPECTED RESULT ==> RETURNS AN EMPTY LIST
        System.out.println(companyService.getCompanyCouponsByMaxPrice(100));

        // =========== GET ALL COMPANY'S COUPONS OF CATEGORY ===========
        System.out.println("\n\nGET ALL COMPANY'S COUPONS BY CATEGORY TEST: =====================");
        // login with other company to show coupons by category:
        companyService = (CompanyServiceImpl) loginManagerService.login("new@email.com", "newPass123", ClientType.COMPANY);

        // get list of coupons of category this company has. EXPECTED RESULT ==> SHOULD GET THEM SUCCESSFULLY :)
        companyService.getCompanyCouponsByCategory(Category.FOOD).forEach(System.out::println);
        System.out.println("==============================================================");
        companyService.getCompanyCouponsByCategory(Category.RESTAURANT).forEach(System.out::println);
        System.out.println("==============================================================");
        // get list of coupons of category this company doesn't have. EXPECTED RESULT ==> RETURNS AN EMPTY LIST
        System.out.println(companyService.getCompanyCouponsByCategory(Category.ELECTRICITY));

        // =========== DELETE COUPON ===========
        System.out.println("\n\nDELETE COUPON TEST: =====================");
        // try to delete a coupon by existing id. EXPECTED RESULT ==> SHOULD DELETE SUCCESSFULLY :)
        try {
            companyService.deleteCoupon(2);
            System.out.println("coupon deleted successfully");
        } catch (CouponException e) {
            System.out.println(e.getMessage());
        }
        // try to delete a coupon by not existing id. EXPECTED RESULT ==> SHOULD THROW EXCEPTION
        try {
            companyService.deleteCoupon(25);
        } catch (CouponException e) {
            System.out.println(e.getMessage());
        }

        // =========== GET ALL COMPANY'S COUPONS ===========
        System.out.println("\n\nGET ALL COMPANY'S COUPONS TEST: =====================");
        // get all company's coupons from DB. EXPECTED RESULT ==> SHOULD GET THEM SUCCESSFULLY AND SHOW THE COUPON
        // JUST WAS DELETED IS GONE FROM DB
        companyService.getCompanyCoupons().forEach(System.out::println);

        // =========== GET COMPANY'S DETAILS ===========
        System.out.println("\n\nGET COMPANY'S DETAILS TEST: =====================");
        System.out.println(companyService.getCompanyDetails());

    }
}
