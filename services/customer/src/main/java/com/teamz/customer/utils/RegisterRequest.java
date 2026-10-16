package com.teamz.customer.utils;

import com.teamz.customer.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String name;
    private String email;
    private String userName;
    private String password;
    private UserRole userRole;
    private Integer phoneNo;
}
