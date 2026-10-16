package com.teamz.customer.DTO;

import com.teamz.customer.entity.Address;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCustomerDetailsDTO {
    private String name;
    private Integer phoneNo;
}
