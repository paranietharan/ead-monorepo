package com.teamz.order.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.teamz.order.entity.OrderLine;
import com.teamz.order.entity.ShippingDetails;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Integer id;
    private BigDecimal totalAmount;
    private LocalDateTime createdDate;
    private Integer customerId;
    private String paymentId;
    private ShippingDetails shippingDetails;

}