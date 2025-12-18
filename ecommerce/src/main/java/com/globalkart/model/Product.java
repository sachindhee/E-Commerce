package com.globalkart.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "product")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name ;

    private String description;

    private BigDecimal price;

    private Integer stock;


    private boolean active=true;


    private LocalDateTime createdAt= LocalDateTime.now();

}
