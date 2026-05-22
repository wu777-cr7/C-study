package com.example.snackshop.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
}