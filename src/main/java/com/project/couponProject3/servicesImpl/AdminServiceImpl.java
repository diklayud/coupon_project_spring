package com.project.couponProject3.servicesImpl;


import com.project.couponProject3.beans.Company;
import com.project.couponProject3.beans.Coupon;
import com.project.couponProject3.beans.Customer;
import com.project.couponProject3.exceptions.CompanyExceptions.*;
import com.project.couponProject3.exceptions.CustomerExceptions.*;
import com.project.couponProject3.exceptions.LoginException;
import com.project.couponProject3.services.AdminService;
import com.project.couponProject3.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
/**
 * Implements all the method of administrator actions
 */
public class AdminServiceImpl extends ClientService implements AdminService {

    @Override
    public boolean login(String email, String password) throws LoginException {
        if (email.equals("admin@admin.com")) {
            if (password.equals("admin")) {
                System.out.println("HELLO ADMIN! WELCOME!!");
                return true;
            }
            throw new LoginException("Cannot login, wrong password");
        }
        throw new LoginException("Cannot login, wrong email");
    }

    public void addCompany(Company company) throws CompanyException {
        if (companyRepo.findByName(company.getName()) == null) {
            if (companyRepo.findByEmail(company.getEmail()) == null) {
                companyRepo.save(company);
            } else {
                throw new CompanyAddException("Cannot add company, email is already exist in the system");
            }
        } else {
            throw new CompanyAddException("Cannot add company, name is already exist in the system");
        }
    }

    public void updateCompany(Company company) throws CompanyException {
        Company companyToUpdate = companyRepo.findById(company.getId());
        if (companyToUpdate == null) {
            throw new CompanyNotFoundException("Cannot update company, company ID not found");
        }
        /*
        if (!company.getName().equals(companyToUpdate.getName())) {
            throw new CompanyUpdateException("Cannot update company, company name cannot be changed");
        }
         */
        if (!company.getEmail().equals(companyToUpdate.getEmail())) {
            Company companyFromDB = companyRepo.findByEmail(company.getEmail());
            if (companyFromDB != null) {
                throw new CompanyUpdateException("Cannot update company, the new email already exist in the system");
            }
        }
        if (!company.getEmail().isEmpty()) {
            companyToUpdate.setEmail(company.getEmail());
        }
        if (!company.getPassword().isEmpty()) {
            companyToUpdate.setPassword(company.getPassword());
        }
        companyRepo.saveAndFlush(companyToUpdate);
    }

    public void deleteCompany(int companyID) throws CompanyException {
        try {
            Company companyToDelete = companyRepo.findById(companyID);
            List<Coupon> coupons = companyToDelete.getCoupons();
            for (Coupon item : coupons) {
                List<Customer> customers = item.getCustomers();
                customers.forEach(customer -> customer.getCoupons().removeIf(couponToDelete -> couponToDelete.getId() == item.getId()));
                customerRepo.saveAll(item.getCustomers());
            }
            companyRepo.deleteById(companyID);
        } catch (Exception e) {
            throw new CompanyDeleteException("Cannot delete company, ID not found in the system");
        }
    }

    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    public Company getOneCompany(int companyID) {
        return companyRepo.findById(companyID);
    }

    public void addCustomer(Customer customer) throws CustomerException {
        if (customerRepo.findByEmail(customer.getEmail()) != null) {
            throw new CustomerAddException("Cannot add customer, email is already exist in the system");
        }
        customerRepo.save(customer);
    }

    public void updateCustomer(Customer customer) throws CustomerException {
        Customer customerToUpdate = customerRepo.findById(customer.getId());
        if (customerToUpdate == null) {
            throw new CustomerNotFoundException("Cannot update customer, customer ID not found");
        }
        if (!customerToUpdate.getEmail().equals(customer.getEmail())) {
            Customer customerFromDB = customerRepo.findByEmail(customer.getEmail());
            if (customerFromDB != null) {
                throw new CustomerUpdateException("Cannot update customer, the new email already exist in the system");
            }
        }
        if (!customer.getFirstName().isEmpty()){
            customerToUpdate.setFirstName(customer.getFirstName());
        }
        if (!customer.getLastName().isEmpty()){
            customerToUpdate.setLastName(customer.getLastName());
        }
        if (!customer.getEmail().isEmpty()){
            customerToUpdate.setEmail(customer.getEmail());
        }
        if(!customer.getPassword().isEmpty()){
            customerToUpdate.setPassword(customer.getPassword());
        }
        customerRepo.saveAndFlush(customerToUpdate);
    }

    public void deleteCustomer(int customerID) throws CustomerException {
        try {
            customerRepo.deleteById(customerID);
        } catch (Exception e) {
            throw new CustomerDeleteException("Cannot delete customer, ID not found in the system");
        }
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public Customer getOneCustomer(int customerID) {
        return customerRepo.findById(customerID);
    }

}
