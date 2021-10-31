package com.project.couponProject3.beans;

import com.project.couponProject3.enums.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserDetails class is for creating JWT
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetails {

    private String email;
    private String password;
    private int id;
    private ClientType clientType;

}
