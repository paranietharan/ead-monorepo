package com.teamz.order.config;

import com.teamz.order.DTO.PaymentIdResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "payment-service")
public interface PaymentClient {
    @GetMapping("/api/v1/payment/{payment-id}")
    PaymentIdResponseDTO validatePaymentId(@PathVariable("payment-id") String paymentId);
}