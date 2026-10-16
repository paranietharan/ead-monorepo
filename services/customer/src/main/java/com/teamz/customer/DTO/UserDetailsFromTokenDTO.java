package com.teamz.customer.DTO;

import com.teamz.customer.entity.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailsFromTokenDTO {
    private Integer userId;
    private UserRole userRole;
}
