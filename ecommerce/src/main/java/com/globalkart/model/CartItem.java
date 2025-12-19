package com.globalkart.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="cart_id")
    private Cart cart;


    @ManyToOne
    @JoinColumn(name="Product_id")
    private Product product;

    private Integer quantity;

    private BigDecimal price;



}
