package com.teamz.order.controller;

import com.teamz.order.DTO.OrderRequest;
import com.teamz.order.DTO.OrderResponse;
import com.teamz.order.DTO.UpdateOrderRequest;
import com.teamz.order.entity.OrderLine;
import com.teamz.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<List<OrderLine>> findById(@PathVariable("order-id") Integer orderId) {
        return ResponseEntity.ok(orderService.findById(orderId));
    }

//    @GetMapping
//    public ResponseEntity<List<OrderResponse>> findAll(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "100") int limit,
//            @RequestParam(defaultValue = "id") String sortBy
//    ) {
//        return ResponseEntity.ok(orderService.findAll(page, limit, sortBy));
//    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/customer/{customer-id}")
    public ResponseEntity<List<OrderResponse>> findOrderByCustomerId(@PathVariable("customer-id") int customerId) {
        return ResponseEntity.ok(orderService.findOrderByCustomerId(customerId));
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateOrder(@RequestBody UpdateOrderRequest request) {
        return ResponseEntity.ok(orderService.updateOrder(request));
    }

    @DeleteMapping("/{order-id}")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable("order-id") Integer orderId) {
        return ResponseEntity.ok(orderService.deleteOrder(orderId));
    }
}
