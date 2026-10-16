package com.teamz.order.service;

import com.teamz.order.DTO.OrderRequest;
import com.teamz.order.DTO.OrderResponse;
import com.teamz.order.DTO.PaymentIdResponseDTO;
import com.teamz.order.DTO.UpdateOrderRequest;
import com.teamz.order.config.PaymentClient;
import com.teamz.order.config.ProductClient;
import com.teamz.order.entity.Order;
import com.teamz.order.entity.OrderLine;
import com.teamz.order.repository.OrderLineRepository;
import com.teamz.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;

    public OrderService(OrderRepository orderRepository, OrderLineRepository orderLineRepository, ProductClient productClient, PaymentClient paymentClient) {
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.productClient = productClient;
        this.paymentClient = paymentClient;
    }

    public Boolean deleteOrder(Integer id) {
        if (!orderRepository.existsById(id)) {
            return false;
        }
        orderRepository.deleteById(id);
        orderLineRepository.deleteByOrderId(id);
        return true;
    }

    public List<OrderLine> findById(Integer id) {
        return orderLineRepository.findByOrder(id);
    }

//    public List<OrderResponse> findAll(int page, int limit, String sortBy) {
//        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(sortBy));
//        return orderRepository.findAll(pageable).map(order -> OrderResponse.builder()
//                .id(order.getId())
//                .totalAmount(order.getTotalAmount())
//                .createdDate(order.getCreatedDate())
//                .customerId(order.getCustomerId())
//                .paymentId(order.getPaymentId())
//                .shippingDetails(order.getShippingDetails())
//                .build()).getContent();
//    }

    public List<OrderResponse> findAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> orderResponse = new ArrayList<>();

        for (Order order : orders) {
            OrderResponse newOrder = OrderResponse.builder()
                    .id(order.getId())
                    .paymentId(order.getPaymentId())
                    .totalAmount(order.getTotalAmount())
                    .createdDate(order.getCreatedDate())
                    .customerId(order.getCustomerId())
                    .shippingDetails(order.getShippingDetails())
                    .build();

            orderResponse.add(newOrder);
        }

        return orderResponse;
    }



    public List<OrderResponse> findOrderByCustomerId(int customerId) {
        return orderRepository.findByCustomerId(customerId).stream().map(order -> OrderResponse.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .createdDate(order.getCreatedDate())
                .customerId(order.getCustomerId())
                .paymentId(order.getPaymentId())
                .shippingDetails(order.getShippingDetails())
                .build()).collect(Collectors.toList());
    }

    private PaymentIdResponseDTO checkPaymentId(String paymentId){
        return paymentClient.validatePaymentId(paymentId);
    }

    public String createOrder(OrderRequest orderRequest){
        if (!checkPaymentId(orderRequest.getPaymentId()).isSuccess()){
            return "Payment ID Not valid";
        }
        List<OrderLine> orderLines = orderRequest.getOrderLines();
        String checkProducts = CheckProductsOnOrderLine(orderLines);
        if (checkProducts.equals("All products are available")){
            Order order = new Order();
            order.setTotalAmount(orderRequest.getTotalAmount());
            order.setCustomerId(orderRequest.getCustomerId());
            order.setOrderLines(orderRequest.getOrderLines());
            order.setPaymentId(orderRequest.getPaymentId());
            order.setShippingDetails(orderRequest.getShippingDetails());
            for (OrderLine orderLine : order.getOrderLines()) {
                orderLine.setOrder(order);
                productClient.updateQuantity(orderLine.getProductId(), orderLine.getQuantity());
            }

            orderRepository.save(order);
            return "Order created successfully";
        }
        return checkProducts;
    }

    public String CheckProductsOnOrderLine(List<OrderLine> orderLine){
        for (OrderLine line : orderLine){
            if (!checkProductAvailability(line.getProductId(), line.getQuantity())){
                return "Product with ID: " + line.getProductId() + " is not available";
            }
        }
        return "All products are available";

    }

    public Boolean checkProductAvailability(Long productId, double quantity)
    {
        return productClient.checkAvailability(productId, quantity);
    }


    public Boolean updateOrder(UpdateOrderRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID:: " + request.getOrderId()));
        if(order == null){
            return false;
        }
        order.setTotalAmount(request.getTotalAmount());
        order.setPaymentId(request.getPaymentId());
        for(OrderLine orderLine : request.getOrderLines()){
            OrderLine line = orderLineRepository.findById(orderLine.getId())
                    .orElseThrow(() -> new EntityNotFoundException("OrderLine not found with ID:: " + orderLine.getId()));
            line.setProductId(orderLine.getProductId());
            line.setQuantity(orderLine.getQuantity());
            line.setOrder(order);
            orderLineRepository.save(line);
        }
        orderRepository.save(order);
        return true;
    }

}
