package com.project.couponProject3;

import com.project.couponProject3.utils.ArtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CouponProject3Application {

	public static void main(String[] args) {
		SpringApplication.run(CouponProject3Application.class, args);
		System.out.println(ArtUtil.HEADER1);
	}

}
