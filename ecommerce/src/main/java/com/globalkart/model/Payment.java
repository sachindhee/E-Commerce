package com.globalkart.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;

    private String status;                           // CREATED, SUCCESS, FAILED
    private BigDecimal amount;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
