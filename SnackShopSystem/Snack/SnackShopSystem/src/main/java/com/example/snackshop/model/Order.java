package com.example.snackshop.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    private Long userId;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private Double totalAmount;
    private String status; // PENDING, PAID, CANCELLED
    private LocalDateTime createTime;
    private LocalDateTime payTime;
}