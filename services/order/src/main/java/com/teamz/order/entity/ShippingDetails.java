package com.teamz.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class ShippingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "fullName cannot be blank")
    private String fullName;

    @NotBlank(message = "phone Number cannot be blank")
    private Integer phoneNumber;

    @NotBlank(message = "House number cannot be blank")
    private String houseNo;

    @NotBlank(message = "Street cannot be blank")
    private String street;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotBlank(message = "District cannot be blank")
    private String district;

    @NotBlank(message = "Province cannot be blank")
    private String province;

    @NotNull(message = "Postal code cannot be null")
    private Long postalCode;

    @OneToOne(mappedBy = "shippingDetails")
    @JsonIgnore  // Prevent serialization of the circular reference
    private Order order;

}
