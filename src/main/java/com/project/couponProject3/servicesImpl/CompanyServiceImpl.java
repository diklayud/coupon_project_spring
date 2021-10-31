package com.project.couponProject3.servicesImpl;

import com.project.couponProject3.beans.Company;
import com.project.couponProject3.beans.Coupon;
import com.project.couponProject3.enums.Category;
import com.project.couponProject3.exceptions.CouponExceptions.CouponAddException;
import com.project.couponProject3.exceptions.CouponExceptions.CouponDeleteException;
import com.project.couponProject3.exceptions.CouponExceptions.CouponException;
import com.project.couponProject3.exceptions.CouponExceptions.CouponUpdateException;
import com.project.couponProject3.exceptions.LoginException;
import com.project.couponProject3.services.ClientService;
import com.project.couponProject3.services.CompanyService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
@Setter
/**
 * Implements all the method of company actions
 */
public class CompanyServiceImpl extends ClientService implements CompanyService {

    private int companyID;

    @Override
    public boolean login(String email, String password) throws LoginException {
        Company company = companyRepo.findByEmailAndPassword(email, password);
        if (company != null) {
            this.companyID = company.getId();
            System.out.println("HELLO COMPANY " + company.getName() + "! WELCOME!!");
            return true;
        }
        throw new LoginException("Cannot login, incorrect email or password");
    }

    public void addCoupon(Coupon coupon) throws CouponException {
        Coupon couponFromDB = couponRepo.findByTitleAndCompany_id(coupon.getTitle(), coupon.getCompany_id_ui());
        if (couponFromDB != null) {
            throw new CouponAddException("Cannot add the coupon, coupon's title already exist under this company!");
        }
        if (coupon.getEndDate().toLocalDate().isBefore(LocalDate.now())) {
            throw new CouponAddException("Cannot add coupon, coupon's end date already passed!");
        }
        if (coupon.getCompany_id_ui() != this.companyID) {
            throw new CouponAddException("Cannot add coupon, coupon's company ID is different from the logged-in company ID!");
        }
        if (coupon.getCompany() == null) {
            coupon.setCompany(this.getCompanyDetails());
        }
        couponRepo.save(coupon);
    }

    public void updateCoupon(Coupon coupon) throws CouponException {
        if (this.companyID != coupon.getCompany_id_ui()) {
            throw new CouponUpdateException("Cannot update coupon, coupon's company ID is different of the logged-in company ID");
        }
        Coupon couponToUpdate = couponRepo.findById(coupon.getId());
        if (couponToUpdate == null) {
            throw new CouponUpdateException("Cannot update coupon, coupon ID not found");
        }
        if (!couponToUpdate.getTitle().equals(coupon.getTitle())) {
            Coupon couponFromDB = couponRepo.findByTitleAndCompany_id(coupon.getTitle(), coupon.getCompany_id_ui());
            if (couponFromDB != null) {
                throw new CouponUpdateException("Cannot update the coupon, coupon's title already exist under this company!");
            }
        }
        if (coupon.getEndDate() != null){
            if (!couponToUpdate.getEndDate().toLocalDate().equals(coupon.getEndDate().toLocalDate())) {
                if (coupon.getEndDate().toLocalDate().isBefore(LocalDate.now())) {
                    throw new CouponUpdateException("Cannot update the coupon, coupon's end date already passed!");
                }
                couponToUpdate.setEndDate(coupon.getEndDate());
            }
        }
        if (!coupon.getTitle().isEmpty()) {
            couponToUpdate.setTitle(coupon.getTitle());
        }
        if (coupon.getCategory() != null) {
            couponToUpdate.setCategory(coupon.getCategory());
        }
        if (!coupon.getImage().isEmpty()) {
            couponToUpdate.setImage(coupon.getImage());
        }
        if (coupon.getPrice() > 0) {
            couponToUpdate.setPrice(coupon.getPrice());
        }
        if (!coupon.getDescription().isEmpty()) {
            couponToUpdate.setDescription(coupon.getDescription());
        }
        if (coupon.getAmount() != 0) {
            couponToUpdate.setAmount(coupon.getAmount());
        }
        couponRepo.saveAndFlush(couponToUpdate);
    }

    public void deleteCoupon(int couponID) throws CouponException {
        try {
            Coupon couponToDelete = couponRepo.findByIdAndCompany_id(couponID, this.companyID);
                couponToDelete.getCustomers().forEach(customer -> customer.getCoupons().removeIf(coupon -> coupon.getId() == couponID));
                customerRepo.saveAll(couponToDelete.getCustomers());
                couponRepo.deleteById(couponID);
        } catch (Exception e) {
            throw new CouponDeleteException("Cannot delete coupon, ID not found in the system");
        }
    }

    public List<Coupon> getCompanyCoupons() {
        return couponRepo.findByCompany_id(this.companyID);
    }

    public List<Coupon> getCompanyCouponsByCategory(Category category) {
        return couponRepo.findByCompany_idAndCategory(this.companyID, category);
    }

    public List<Coupon> getCompanyCouponsByMaxPrice(double maxPrice) {
        return couponRepo.findByCompany_idAndPriceLessThan(this.companyID, maxPrice);
    }

    public Coupon getCouponById(int id) {
        return couponRepo.findById(id);
    }

    public Company getCompanyDetails() {
        return companyRepo.findById(this.companyID);
    }

}
