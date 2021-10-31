package com.project.couponProject3.servicesImpl;

import com.project.couponProject3.enums.ClientType;
import com.project.couponProject3.exceptions.LoginException;
import com.project.couponProject3.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
/**
 * Service class which initialize the clients services
 */
public class LoginManagerService {


    private final AdminServiceImpl adminService;
    private final CompanyServiceImpl companyService;
    private final CustomerServiceImpl customerService;

    /**
     * Checks the login details in DB to get the correct client service.
     * Checking the details by:
     *
     * @param email      client's email
     * @param password   client's password
     * @param clientType client's type
     * @return the proper client service or null if the login details are wrong
     * @throws LoginException if data is incorrect
     */
    public ClientService login(String email, String password, ClientType clientType) throws LoginException {
        switch (clientType) {
            case ADMINISTRATOR:
                return adminService.login(email, password) ? adminService : null;

            case COMPANY:
                return companyService.login(email, password) ? companyService : null;

            case CUSTOMER:
                return customerService.login(email, password) ? customerService : null;

            default:
                return null;
        }
    }

}
