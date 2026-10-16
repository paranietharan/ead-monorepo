package com.teamz.order.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "customer_order")
public class Order {

    @Id
    @GeneratedValue
    private Integer id;
    private BigDecimal totalAmount;
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;
    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }
    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<OrderLine> orderLines;
    private Integer customerId;
    private String paymentId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_details_id", referencedColumnName = "id")
    @JsonManagedReference
    private ShippingDetails shippingDetails;
}
