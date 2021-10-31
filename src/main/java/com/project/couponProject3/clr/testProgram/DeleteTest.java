package com.project.couponProject3.clr.testProgram;

import com.project.couponProject3.exceptions.CompanyExceptions.CompanyException;
import com.project.couponProject3.exceptions.CouponExceptions.CouponException;
import com.project.couponProject3.exceptions.CustomerExceptions.CustomerException;
import com.project.couponProject3.servicesImpl.AdminServiceImpl;
import com.project.couponProject3.servicesImpl.CompanyServiceImpl;
import com.project.couponProject3.utils.ArtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
@RequiredArgsConstructor
/**
 * Class of testing the delete methods
 */
public class DeleteTest implements CommandLineRunner {

    private final AdminServiceImpl adminService;
    private final CompanyServiceImpl companyService;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("\n" + ArtUtil.DELETE_TEST_HEADER);

        // =========== DELETE COUPON ===========
        System.out.println("\n\nDELETE COUPON TEST: =====================");
        // delete a coupon and it's purchasing history. EXPECTED RESULT ==> SHOULD DELETE SUCCESSFULLY :)
        try {
            companyService.deleteCoupon(1);
            System.out.println("coupon deleted successfully");
        } catch (CouponException e) {
            System.out.println(e.getMessage());
        }

        // =========== DELETE CUSTOMER ===========
        System.out.println("\n\nDELETE CUSTOMER TEST: =====================");
        // delete a customer and it's purchasing history. EXPECTED RESULT ==> SHOULD DELETE SUCCESSFULLY :)
        try {
            adminService.deleteCustomer(2);
            System.out.println("customer deleted successfully");
        } catch (CustomerException e) {
            System.out.println(e.getMessage());
        }

        // =========== DELETE COMPANY ===========
        System.out.println("\n\nDELETE COMPANY TEST: =====================");
        // delete a company and its coupons and its purchasing history. EXPECTED RESULT ==> SHOULD DELETE SUCCESSFULLY :)
        try {
            adminService.deleteCompany(2);
            System.out.println("company deleted successfully");
        } catch (CompanyException e) {
            System.out.println(e.getMessage());
        }


    }
}
