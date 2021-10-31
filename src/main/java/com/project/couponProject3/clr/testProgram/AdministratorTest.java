package com.project.couponProject3.clr.testProgram;

import com.project.couponProject3.beans.Company;
import com.project.couponProject3.beans.Customer;
import com.project.couponProject3.enums.ClientType;
import com.project.couponProject3.exceptions.CompanyExceptions.CompanyException;
import com.project.couponProject3.exceptions.CustomerExceptions.CustomerException;
import com.project.couponProject3.exceptions.LoginException;
import com.project.couponProject3.servicesImpl.AdminServiceImpl;
import com.project.couponProject3.servicesImpl.LoginManagerService;
import com.project.couponProject3.utils.ArtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Order(1)
@RequiredArgsConstructor
/**
 * Class of testing the administrator service methods
 */
public class AdministratorTest implements CommandLineRunner {

    private final LoginManagerService loginManagerService;

    @Override
    public void run(String... args) throws Exception {

        try {

            System.out.println("\n" + ArtUtil.ADMIN_TEST_HEADER);

            // =========== LOGIN ===========
            System.out.println("\n\nLOGIN ADMINISTRATOR TEST: =============");
            // try to login with wrong email. EXPECTED RESULT ==> SHOULD SHOW EXCEPTION
            try {
                loginManagerService.login("check", "admin", ClientType.ADMINISTRATOR);
            } catch (LoginException error) {
                System.out.println(error.getMessage());
            }

            // try to login with wrong password. EXPECTED RESULT ==> SHOULD SHOW EXCEPTION
            try {
                loginManagerService.login("admin@admin.com", "check", ClientType.ADMINISTRATOR);
            } catch (LoginException error) {
                System.out.println(error.getMessage());
            }

            // try to login with right email and password. EXPECTED RESULT ==> SHOULD LOGIN SUCCESSFULLY :)
            System.out.println("login with right details:");
            AdminServiceImpl adminService = (AdminServiceImpl) loginManagerService.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);

            // =========== ADD COMPANY ===========
            System.out.println("\n\nADD COMPANY TEST: =====================");
            // try to add 5 companies. EXPECTED RESULT ==> SHOULD ADD SUCCESSFULLY :)
            Company company1 = Company.builder()
                    .name("company1")
                    .email("comp1@a.com")
                    .password("111")
                    .build();
            Company company2 = Company.builder()
                    .name("company2")
                    .email("comp2@b.com")
                    .password("222")
                    .build();
            Company company3 = Company.builder()
                    .name("company3")
                    .email("comp3@c.com")
                    .password("333")
                    .build();
            Company company4 = Company.builder()
                    .name("company4")
                    .email("comp4@d.com")
                    .password("444")
                    .build();
            Company company5 = Company.builder()
                    .name("company5")
                    .email("comp5@e.com")
                    .password("555")
                    .build();
            try {
                adminService.addCompany(company1);
                adminService.addCompany(company2);
                adminService.addCompany(company3);
                adminService.addCompany(company4);
                adminService.addCompany(company5);
                System.out.println("5 companies added successfully !!");
            } catch (CompanyException e) {
                System.out.println(e.getMessage());
            }

            // try to add company with existing name. EXPECTED RESULT ==> SHOULD THROW EXCEPTION
            Company company6 = Company.builder()
                    .name("company4")
                    .email("comp6@f.com")
                    .password("666")
                    .build();
            try {
                adminService.addCompany(company6);
            } catch (CompanyException e) {
                System.out.println(e.getMessage());
            }
            // try to add company with existing email. EXPECTED RESULT ==> SHOULD THROW EXCEPTION
            try {
                adminService.addCompany(Company.builder()
                        .name("company7")
                        .email("comp5@e.com")
                        .password("777")
                        .build());
            } catch (CompanyException e) {
                System.out.println(e.getMessage());
            }

            // =========== GET ALL COMPANIES ===========
            System.out.println("\n\nGET ALL COMPANIES TEST: =====================");
            // get all companies from DB. EXPECTED RESULT ==> SHOULD GET THEM SUCCESSFULLY AND SHOW THAT THE COMPANIES
            // WITH THE EXISTING VALUES REALLY NOT ADDED TO DB :)
            System.out.println("Our LIST of companies:");
            List<Company> companies = adminService.getAllCompanies();
            companies.forEach(System.out::println);

            // =========== UPDATE COMPANY ===========
            System.out.println("\n\nUPDATE COMPANY TEST: =====================");
            // try to update existing company details to new unique values.
            // EXPECTED RESULT ==> SHOULD UPDATE SUCCESSFULLY :)
            company1 = companies.get(0);
            company1.setPassword("newPass123");
            company1.setEmail("new@email.com");
            try {
                adminService.updateCompany(company1);
                System.out.println("company updated successfully !!");
            } catch (CompanyException e) {
                System.out.println(e.getMessage());
            }

            // try to update company's email to existing email of other company.
            // EXPECTED RESULT ==> SHOULD THROW EXCEPTION
            company2 = companies.get(1);
            company2.setEmail("new@email.com");
            try {
                adminService.updateCompany(company2);
            } catch (CompanyException e) {
                System.out.println(e.getMessage());
            }

            /*
            // try to update company's name.
            // EXPECTED RESULT ==> SHOULD THROW EXCEPTION
            company2.setName("newCompanyName");
            try {
                adminService.updateCompany(company2);
            } catch (CompanyException e) {
                System.out.println(e.getMessage());
            }
             */

            // try to update company's id.
            // EXPECTED RESULT ==> SHOULD THROW EXCEPTION
            company2.setId(500);
            try {
                adminService.updateCompany(company2);
            } catch (CompanyException e) {
                System.out.println(e.getMessage());
            }

            // =========== GET A SINGLE COMPANY ===========
            System.out.println("\n\nGET SINGLE COMPANY TEST: =====================");
            // get a company by id from DB. EXPECTED RESULT ==> SHOULD GET IT SUCCESSFULLY AND SHOW THAT THE COMPANY'S
            // DATA UPDATED IN DB ACCORDING TO THE UPDATE TEST ABOVE :)
            System.out.println(adminService.getOneCompany(1));

            // =========== DELETE A SINGLE COMPANY ===========
            System.out.println("\n\nDELETE COMPANY TEST: =====================");
            // try to delete a company by existing id. EXPECTED RESULT ==> SHOULD DELETE SUCCESSFULLY :)
            try {
                adminService.deleteCompany(3);
                System.out.println("company deleted successfully !!");
            } catch (CompanyException e) {
                System.out.println(e.getMessage());
            }

            // try to delete a company by not existing id. EXPECTED RESULT ==> SHOULD THROW EXCEPTION
            try {
                adminService.deleteCompany(300);
            } catch (CompanyException e) {
                System.out.println(e.getMessage());
            }

            // =========== GET ALL COMPANIES ===========
            System.out.println("\n\nGET ALL COMPANIES TEST: =====================");
            // get all companies from DB. EXPECTED RESULT ==> SHOULD GET THEM SUCCESSFULLY AND SHOW THAT
            // THE COMPANY THAT JUST WAS DELETED IS GONE FROM DB
            System.out.println("Our NEW LIST of companies:");
            adminService.getAllCompanies().forEach(System.out::println);


            // =========== ADD CUSTOMER ===========
            System.out.println("\n\nADD CUSTOMER TEST: =====================");
            // try to add 5 customers. EXPECTED RESULT ==> SHOULD ADD SUCCESSFULLY :)
            Customer customer1 = Customer.builder()
                    .firstName("customer1")
                    .lastName("Lcustomer1")
                    .email("cust1@mail.com")
                    .password("111")
                    .build();
            Customer customer2 = Customer.builder()
                    .firstName("customer2")
                    .lastName("Lcustomer2")
                    .email("cust2@mail.com")
                    .password("222")
                    .build();
            Customer customer3 = Customer.builder()
                    .firstName("customer3")
                    .lastName("Lcustomer3")
                    .email("cust3@mail.com")
                    .password("333")
                    .build();
            Customer customer4 = Customer.builder()
                    .firstName("customer4")
                    .lastName("Lcustomer4")
                    .email("cust4@mail.com")
                    .password("444")
                    .build();
            Customer customer5 = Customer.builder()
                    .firstName("customer5")
                    .lastName("Lcustomer5")
                    .email("cust5@mail.com")
                    .password("555")
                    .build();
            try {
                adminService.addCustomer(customer1);
                adminService.addCustomer(customer2);
                adminService.addCustomer(customer3);
                adminService.addCustomer(customer4);
                adminService.addCustomer(customer5);
                System.out.println("5 customers added successfully !!");
            } catch (CustomerException e) {
                System.out.println(e.getMessage());
            }

            // try to add customer with existing email. EXPECTED RESULT ==> SHOULD THROW EXCEPTION
            Customer customer6 = Customer.builder()
                    .firstName("customer6")
                    .lastName("Lcustomer6")
                    .email("cust5@mail.com")
                    .password("616")
                    .build();
            try {
                adminService.addCustomer(customer6);
            } catch (CustomerException e) {
                System.out.println(e.getMessage());
            }

            // =========== GET ALL CUSTOMERS ===========
            System.out.println("\n\nGET ALL CUSTOMERS TEST: =====================");
            // get all customer from DB. EXPECTED RESULT ==> SHOULD GET THEM SUCCESSFULLY AND SHOW THAT THE CUSTOMER
            // WITH THE EXISTING EMAIL REALLY NOT ADDED TO DB :)
            System.out.println("Our LIST of customers:");
            List<Customer> customers = adminService.getAllCustomers();
            customers.forEach(System.out::println);

            // =========== UPDATE CUSTOMER ===========
            System.out.println("\n\nUPDATE CUSTOMER TEST: =====================");
            // try to update existing customer details to new unique values.
            // EXPECTED RESULT ==> SHOULD UPDATE SUCCESSFULLY :)
            try {
                customer1 = customers.get(0);
                customer1.setFirstName("newFname");
                customer1.setLastName("newLname");
                customer1.setPassword("new1212");
                customer1.setEmail("customer1new@mail.com");
                adminService.updateCustomer(customer1);
                System.out.println("customer updated successfully !!");
            } catch (CustomerException e) {
                System.out.println(e.getMessage());
            }

            // try to update customer's email to existing email of other customer.
            // EXPECTED RESULT ==> SHOULD THROW EXCEPTION
            customer2 = customers.get(1);
            customer2.setEmail("customer1new@mail.com");
            try {
                adminService.updateCustomer(customer2);
            } catch (CustomerException e) {
                System.out.println(e.getMessage());
            }

            // try to update customer's id.
            // EXPECTED RESULT ==> SHOULD THROW EXCEPTION
            customer2.setId(450);
            try {
                adminService.updateCustomer(customer2);
            } catch (CustomerException e) {
                System.out.println(e.getMessage());
            }

            // =========== GET A SINGLE CUSTOMER ===========
            System.out.println("\n\nGET SINGLE CUSTOMER TEST: =====================");
            // get a customer by id from DB. EXPECTED RESULT ==> SHOULD GET IT SUCCESSFULLY AND SHOW THAT THE CUSTOMER'S
            // DATA UPDATED IN DB ACCORDING TO THE UPDATE TEST ABOVE :)
            System.out.println(adminService.getOneCustomer(1));

            // =========== DELETE A SINGLE CUSTOMER ===========
            System.out.println("\n\nDELETE CUSTOMER TEST: =====================");
            // try to delete a customer by existing id. EXPECTED RESULT ==> SHOULD DELETE SUCCESSFULLY :)
            try {
                adminService.deleteCustomer(3);
                System.out.println("customer deleted successfully !!");
            } catch (CustomerException e) {
                System.out.println(e.getMessage());
            }
            // try to delete a customer by not existing id. EXPECTED RESULT ==> SHOULD THROW EXCEPTION
            try {
                adminService.deleteCustomer(35);
            } catch (CustomerException e) {
                System.out.println(e.getMessage());
            }

            // =========== GET ALL CUSTOMERS ===========
            System.out.println("\n\nGET ALL CUSTOMERS TEST: =====================");
            // get all customers from DB. EXPECTED RESULT ==> SHOULD GET THEM SUCCESSFULLY AND SHOW THAT
            // THE CUSTOMER THAT JUST WAS DELETED IS GONE FROM DB
            System.out.println("Our NEW LIST of customers:");
            adminService.getAllCustomers().forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("SOMETHING TERRIBLY WENT WRONG: " + e.getMessage());
        }
    }
}
