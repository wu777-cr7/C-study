package com.example.snackshop.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private Double price;
    private Integer stock;
    private String description;
    private String imageUrl;
    private String status; // ON / OFF
    private LocalDateTime createTime;
}