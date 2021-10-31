package com.project.couponProject3.clr.testProgram;

import com.project.couponProject3.enums.ClientType;
import com.project.couponProject3.servicesImpl.CompanyServiceImpl;
import com.project.couponProject3.servicesImpl.CustomerServiceImpl;
import com.project.couponProject3.servicesImpl.LoginManagerService;
import com.project.couponProject3.utils.ArtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(5)
@RequiredArgsConstructor
public class ClientCouponsTest implements CommandLineRunner  {

    private final LoginManagerService loginManagerService;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("\n" + ArtUtil.CLIENT_COUPONS_TEST_HEADER);
        System.out.println("Show the clients (company/customer) with their coupons (no circular fields)");

        // =========== LOGIN AS COMPANY ===========
        // login with company which has coupons in the system
        CompanyServiceImpl companyService = (CompanyServiceImpl) loginManagerService.login("comp4@d.com", "444", ClientType.COMPANY);

        // =========== GET COMPANY WITH COUPONS ===========
        System.out.println("\n\nGET COMPANY WITH COUPONS TEST: =====================");
        // show the company with its coupons. EXPECTED RESULT ==> SHOULD BRING SUCCESSFULLY :)
        System.out.println(companyService.getCompanyDetails() + "\n\n");

        // =========== LOGIN AS CUSTOMER ===========
        // login with customer which bought coupons in the system
        CustomerServiceImpl customerService = (CustomerServiceImpl) loginManagerService.login("customer1new@mail.com", "new1212", ClientType.CUSTOMER);

        // =========== GET CUSTOMER WITH COUPONS ===========
        System.out.println("\n\nGET CUSTOMER WITH COUPONS TEST: =====================");
        // show the customer with its coupons. EXPECTED RESULT ==> SHOULD BRING SUCCESSFULLY :)
        System.out.println(customerService.getCustomerDetails());
    }
}
