package com.project.couponProject3.advices;

import com.project.couponProject3.exceptions.CompanyExceptions.CompanyException;
import com.project.couponProject3.exceptions.CouponExceptions.CouponException;
import com.project.couponProject3.exceptions.CustomerExceptions.CustomerException;
import com.project.couponProject3.exceptions.LoginException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice //aop->exception
public class CouponSystemRestException {

    @ExceptionHandler(value = {LoginException.class})
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ErrorDetail loginError(Exception e){
        return new  ErrorDetail("Bad login", e.getMessage());
    }

    @ExceptionHandler(value = {MalformedJwtException.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorDetail jwtError(Exception e){
        return new ErrorDetail("Token error: ", e.getMessage());
    }

    //which exception class was fired
    @ExceptionHandler(value = {CompanyException.class})
    //what to return in response
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleCompanyException(Exception e){
        return new ErrorDetail("Company error: ", e.getMessage());
    }

    @ExceptionHandler(value = {CustomerException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleCustomerException(Exception e){
        return new ErrorDetail("Customer error: ", e.getMessage());
    }

    @ExceptionHandler(value = {CouponException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetail handleCouponException(Exception e){
        return new ErrorDetail("Coupon error: ", e.getMessage());
    }



}