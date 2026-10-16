package com.teamz.customer.DTO;

import com.teamz.customer.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {
    private String name;
    private String userName;
    private String email;
    private Address address;
    private Integer phoneNo;


}
