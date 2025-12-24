package com.globalkart.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="user_id")
   private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<> items;

    private BigDecimal totalAmount;

    private String status;

    private LocalDateTime createdAt = LocalDateTime.now();

}
